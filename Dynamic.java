package assignment2;

import java.util.*;

public class Dynamic {

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
     *         This method must be implemented using an efficient bottom-up dynamic
     *         programming solution to the problem (not memoised).
     */
    public static int optimalProfitDynamic(int k, ArrayList<Configuration> configurations,
            ArrayList<BookingRequest> bookingRequests) {

        // table for subproblem solns
        // stores the max possible profit at each day towards to final day
        // under a config
        //     0  1  2  ...  k  k+1   (day)
        // c0  p                0
        // c1                   0
        // ...                  0
        // cn                   0
        // (config)
        HashMap<Configuration, int[]> optProfitsPerConfig =
                new HashMap<>();
        for (Configuration configuration : configurations) {
            int optProfits[] = new int[k + 1];
            for (int day = 0; day < k; ++day) {
                optProfits[day] = Integer.MIN_VALUE;
            }
            optProfits[k] = 0; // base cases
            optProfitsPerConfig.put(configuration, optProfits);
        }
        // calculate each day's optimal profit
        for (int day = k - 1; day >= 0; --day) {
            for (Configuration configuration : configurations) {
                ArrayList<Activity> nextActivities =
                        getPossibleActivities(configuration, day, k,
                                configurations, bookingRequests);
                // find the activity that will give the optimal profit
                int maxProfit = Integer.MIN_VALUE;
                for (Activity activity : nextActivities) {
                    Configuration nextConfiguration = configuration;
                    if (activity.getClass() == Activity.Reconfigure.class)
                        nextConfiguration = activity.endConfiguration();
                    int profit = activity.profit() +
                                    optProfitsPerConfig.get(nextConfiguration)[activity.end() + 1];
                    if (profit > maxProfit) {
                        maxProfit = profit;
                    }
                }
                optProfitsPerConfig.get(configuration)[day] = maxProfit;
            }
        }

        return optProfitsPerConfig.get(configurations.get(0))[0];
    }

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
     * @ensure Returns a schedule for the venue with the maximum profit (given the input
     *         parameters k, configurations and bookingRequests).
     * 
     *         (See handout for details.)
     * 
     *         This method must be implemented using an efficient bottom-up dynamic
     *         programming solution to the problem (not memoised).
     */
    public static Activity[] optimalScheduleDynamic(int k, ArrayList<Configuration> configurations,
            ArrayList<BookingRequest> bookingRequests) {

        ArrayList<Activity> optimalSchedule = new ArrayList<>();
        HashMap<Configuration, int[]> optProfitsPerConfig =
                new HashMap<>();
        HashMap<Configuration, Activity[]> optActivitiesIfStartFromTodayPerConfig =
                new HashMap<>();
        for (Configuration configuration : configurations) {
            int[] optProfits = new int[k + 1];
            for (int day = 0; day < k; ++day) {
                optProfits[day] = Integer.MIN_VALUE;
            }
            optProfits[k] = 0; // base cases
            optProfitsPerConfig.put(configuration, optProfits);
        }
        for (Configuration configuration : configurations) {
            Activity[] optPrevActivitiesIfStartFromToday = new Activity[k];
            for (int day = 0; day < k; ++day) {
                optPrevActivitiesIfStartFromToday[day] = null;
            }
            optActivitiesIfStartFromTodayPerConfig.put(configuration, optPrevActivitiesIfStartFromToday);
        }
        // calculate each day's optimal profit
        for (int day = k - 1; day >= 0; --day) {
            for (Configuration configuration : configurations) {
                ArrayList<Activity> nextActivities =
                        getPossibleActivities(configuration, day, k,
                                configurations, bookingRequests);
                // find the activity that will give the optimal profit
                int maxProfit = Integer.MIN_VALUE;
                Activity optimalActivity = null;
                for (Activity activity : nextActivities) {
                    Configuration nextConfiguration = configuration;
                    if (activity.getClass() == Activity.Reconfigure.class)
                        nextConfiguration = activity.endConfiguration();
                    int profit = activity.profit() +
                            optProfitsPerConfig.get(nextConfiguration)[activity.end() + 1];
                    if (profit > maxProfit) {
                        maxProfit = profit;
                        optimalActivity = activity;
                    }
                }
                optProfitsPerConfig.get(configuration)[day] = maxProfit;
                if (day < k)
                    optActivitiesIfStartFromTodayPerConfig.get(configuration)[day] = optimalActivity;
            }
        }

        // Retrieve optimal schedule
        int day = 0;
        Configuration c = configurations.get(0);
        while (day < k) {
            Activity a = optActivitiesIfStartFromTodayPerConfig.get(c)[day];
            int i = day;
            do {
                optimalSchedule.add(a);
                ++i;
            } while (i <= a.end());
            day = a.end() + 1;
            if (a.getClass() == Activity.Reconfigure.class) {
                c = a.endConfiguration();
            }
        }

        Activity[] out = new Activity[optimalSchedule.size()];
        out = optimalSchedule.toArray(out);

        return out;
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
