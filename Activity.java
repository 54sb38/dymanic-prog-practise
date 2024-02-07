package assignment2;

/**
 * An abstract class representing an activity.
 *
 * An activity has a start day and an end day, and a start configuration and an end
 * configuration.
 * 
 * DO NOT MODIFY THIS FILE IN ANY WAY.
 */
public abstract class Activity {

    /** Returns the start day of the activity. */
    public abstract int start();

    /** Returns the end day of the activity. */
    public abstract int end();

    /** Returns the duration of the activity. */
    public abstract int duration();

    /** Returns the configuration on the first day of the activity */
    public abstract Configuration startConfiguration();

    /** Returns the configuration on the last day of the activity */
    public abstract Configuration endConfiguration();

    /** The profit (or loss) of the entire activity. */
    public abstract int profit();

    /** Idle activity (idle in a configuration for one day). */
    public final static class Idle extends Activity {

        /* The configuration for the duration of the activity. */
        private final Configuration configuration;
        /* The start and end day. */
        private final int day;
        /* The duration of an Idle activity is always one day. */
        private final int duration;

        /** Creates a new Idle activity for the given configuration on the given day. */
        public Idle(Configuration configuration, int day) {
            if (configuration == null) {
                throw new IllegalArgumentException("Configuration cannot be null.");
            }
            if (day < 0) {
                throw new IllegalArgumentException("Days must be greater than or equal to zero.");
            }
            this.configuration = configuration;
            this.day = day;
            this.duration = 1;
        }

        /** Returns the configuration for the duration of the activity. */
        public Configuration configuration() {
            return configuration;
        }

        @Override
        public int duration() {
            return duration;
        }

        @Override
        public int start() {
            return day;
        }

        @Override
        public int end() {
            return day;
        }

        @Override
        public Configuration startConfiguration() {
            return configuration;
        }

        @Override
        public Configuration endConfiguration() {
            return configuration;
        }

        @Override
        public int profit() {
            return -configuration.cost(day);
        }

        @Override
        public String toString() {
            return "IDLE in configuration c" + configuration.id();
        }

    }

    /** An activity for hosting an event. */
    public final static class HostEvent extends Activity {

        /* The configuration for the duration of the activity. */
        private final Configuration configuration;
        /* The booking that is being hosted for the duration of the activity. */
        private final BookingRequest booking;
        /* The duration of the activity will be the duration of the booking. */
        private final int duration;

        /**
         * Creates a new activity for hosting the given booking request in the given
         * configuration.
         */
        public HostEvent(Configuration configuration, BookingRequest booking) {
            if (configuration == null || booking == null) {
                throw new IllegalArgumentException("Inputs cannot be null.");
            }
            this.configuration = configuration;
            this.booking = booking;
            this.duration = (booking.end() - booking.start()) + 1;
        }

        /** Returns the configuration for the duration of the activity. */
        public Configuration configuration() {
            return configuration;
        }

        /** Returns the booking that is being hosted for the duration of the activity. */
        public BookingRequest booking() {
            return booking;
        }

        @Override
        public int duration() {
            return duration;
        }

        @Override
        public int start() {
            return booking.start();
        }

        @Override
        public int end() {
            return booking.end();
        }

        @Override
        public Configuration startConfiguration() {
            return configuration;
        }

        @Override
        public Configuration endConfiguration() {
            return configuration;
        }

        @Override
        public int profit() {
            return booking.payment(configuration)
                    - configuration.cost(booking.start(), booking.end());
        }

        @Override
        public String toString() {
            return "HOSTING " + "b" + booking.id() + " in configuration " + "c"
                    + configuration.id();
        }
    }

    /**
     * An activity for changing the configuration of the venue from one configuration to
     * another.
     */
    public final static class Reconfigure extends Activity {

        /* The configuration at the start of the reconfiguration. */
        private final Configuration oldConfiguration;
        /* The configuration at the end of the reconfiguration. */
        private final Configuration newConfiguration;
        /* The first day of the activity. */
        private final int start;
        /* The last day of the activity. */
        private final int end;
        /* The time to tear down the old configuration and set up the new one. */
        private final int duration;

        /**
         * Creates a new activity for reconfiguring the venue from the given
         * oldConfiguration to the given newConfiguration, starting on the given start
         * day.
         */
        public Reconfigure(Configuration oldConfiguration, Configuration newConfiguration,
                int start) {
            if (oldConfiguration == null || newConfiguration == null) {
                throw new IllegalArgumentException("The configurations cannot be null.");
            }
            if (oldConfiguration == newConfiguration) {
                throw new IllegalArgumentException(
                        "Must reconfigure to a different configuration.");
            }
            this.oldConfiguration = oldConfiguration;
            this.newConfiguration = newConfiguration;
            this.duration = oldConfiguration.teardownTime() + newConfiguration.setupTime();
            this.start = start;
            this.end = (start + duration) - 1;
        }

        @Override
        public int duration() {
            return duration;
        }

        @Override
        public int start() {
            return start;
        }

        @Override
        public int end() {
            return end;
        }

        @Override
        public Configuration startConfiguration() {
            return oldConfiguration;
        }

        @Override
        public Configuration endConfiguration() {
            return newConfiguration;
        }

        @Override
        public int profit() {
            return -(oldConfiguration.cost(start, start + oldConfiguration.teardownTime() - 1)
                    + newConfiguration.cost(start + oldConfiguration.teardownTime(), end));
        }

        @Override
        public String toString() {
            return "RECONFIGURING " + "c" + oldConfiguration.id() + " to " + "c"
                    + newConfiguration.id();
        }

    }

}
