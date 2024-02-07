package assignment2;

import java.util.*;

public class Recursive {

    /**
     * @require The number of days that you are managing the venue for, k, is greater than
     *          or equal to 1.
     * 
     *          The arrays of configurations and booking requests cannot be null and
     *          cannot contain null values.
     * 
     *          The number of configurations, configurations.size(), is greater than or
     *          equal to one. For convenience you may assume that each of the
     *          configurations has a unique identifier that corresponds to its index in
     *          the input array of configurations.
     * 
     *          The number of booking requests, bookingRequests.size(), is greater than or
     *          equal to zero. For each booking request b in bookingRequests, b.end() <=
     *          k-1. For convenience you may assume that each of the booking requests has
     *          a unique identifier that corresponds to its index in the input array of
     *          booking requests. The booking requests are not guaranteed to be sorted in
     *          any particular order.
     * 
     * @ensure Returns the maximum profit of any schedule for the venue (given the input
     *         parameters k, configurations and bookingRequests).
     * 
     *         (See handout for details.)
     * 
     *         This method must be implemented using a recursive programming solution to
     *         the problem. It is expected that your recursive algorithm will not be
     *         polynomial-time in the worst case. (You must NOT provide a dynamic
     *         programming solution to this question.)
     */
    public static int optimalProfitRecursive(int k, ArrayList<Configuration> configurations,
            ArrayList<BookingRequest> bookingRequests) {
        Configuration initialConfiguration = configurations.get(0);
        ArrayList<Activity> initialActivities =
                getPossibleActivities(initialConfiguration, 0, k,
                        configurations, bookingRequests);

        int maxProfit = Integer.MIN_VALUE;
        // iterate through every staring points
        for (Activity nextActivity : initialActivities) {
            Configuration nextConfiguration = initialConfiguration;
            if (nextActivity.getClass() == Activity.Reconfigure.class)
                nextConfiguration = nextActivity.endConfiguration();
            int nextProfit = nextActivity.profit() + Recursive.optimalProfit(k,
                    configurations, bookingRequests, nextActivity.end() + 1,
                    nextActivity, nextConfiguration);
            if (nextProfit > maxProfit)
                maxProfit = nextProfit;
        }
        return maxProfit;
    }

    private static int optimalProfit(int k,
                                  ArrayList<Configuration> configurations,
                              ArrayList<BookingRequest> bookingRequests,
                              int day, Activity activity,
                              Configuration configuration) {
        if (day == k)
            return 0;

        int maxProfit = Integer.MIN_VALUE;
        // List every possible activities to carry out at the day with the
        // current configuration
        ArrayList<Activity> nextActivities = getPossibleActivities(configuration,
                day, k, configurations, bookingRequests);
        // Now try out every possible activities listed above and calc profit
        for (Activity nextActivity : nextActivities) {
            Configuration nextConfiguration = configuration;
            if (nextActivity.getClass() == Activity.Reconfigure.class)
                nextConfiguration = nextActivity.endConfiguration();
            int nextProfit = nextActivity.profit() + Recursive.optimalProfit(k,
                    configurations,
                    bookingRequests, nextActivity.end() + 1, nextActivity,
                    nextConfiguration);
            if (nextProfit > maxProfit)
                maxProfit = nextProfit;
        }

        return maxProfit;
    }

    private static ArrayList<Activity> getPossibleActivities(Configuration configuration,
                                                             int day, int k,
                                                             ArrayList<Configuration> configurations,
                                                             ArrayList<BookingRequest> bookingRequests) {
        ArrayList<Activity> nextActivities = new ArrayList<>();
        nextActivities.add(new Activity.Idle(configuration, day));
        for (BookingRequest booking : bookingRequests) {
            if ((booking.start() == day) && (booking.end() < k)) {
                // event will not exceed final day
                nextActivities.add(new Activity.HostEvent(configuration,
                        booking));
            }
        }
        for (Configuration nextConfiguration : configurations) {
            if (nextConfiguration == configuration)
                continue;
            if ((configuration.teardownTime() + nextConfiguration.setupTime() + day - 1) < k) {
                // reconfiguration will not exceed final day
                nextActivities.add(new Activity.Reconfigure(configuration,
                        nextConfiguration, day));
            }
        }

        return nextActivities;
    }
}
