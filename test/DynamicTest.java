package assignment2.test;

import assignment2.*;
import java.util.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Basic tests for the {@link Dynamic} implementation class.
 * 
 * We will use a much more comprehensive test suite to test your code, so you should add
 * your own tests to this test suite to help you to debug your implementation.
 */
public class DynamicTest {

    /** Basic test from the handout. */
    @Test
    public void basicTestOptimalProfitDynamic1() throws Exception {
        /* Initialise parameters to the test */
        int k = 11;

        ArrayList<Configuration> configurations = new ArrayList<>();
        int id;
        int setupTime;
        int teardownTime;
        ArrayList<Integer> cost;

        id = 0;
        setupTime = 1;
        teardownTime = 1;
        cost = new ArrayList<>(Arrays.asList(1, 0, 2, 1, 0, 0, 1, 1, 1, 5, 0));
        configurations.add(new Configuration(id, setupTime, teardownTime, cost));

        id = 1;
        setupTime = 2;
        teardownTime = 1;
        cost = new ArrayList<>(Arrays.asList(0, 6, 3, 1, 1, 1, 1, 1, 2, 0, 8));
        configurations.add(new Configuration(id, setupTime, teardownTime, cost));

        ArrayList<BookingRequest> bookingRequests = new ArrayList<>();
        HashMap<Configuration, Integer> payment;

        id = 0;
        payment = new HashMap<>();
        payment.put(configurations.get(0), 4);
        payment.put(configurations.get(1), 3);
        bookingRequests.add(new BookingRequest(id, 0, 1, payment));

        id++;
        payment = new HashMap<>();
        payment.put(configurations.get(0), 2);
        payment.put(configurations.get(1), 7);
        bookingRequests.add(new BookingRequest(id, 0, 0, payment));

        id++;
        payment = new HashMap<>();
        payment.put(configurations.get(0), 2);
        payment.put(configurations.get(1), 5);
        bookingRequests.add(new BookingRequest(id, 3, 3, payment));

        id++;
        payment = new HashMap<>();
        payment.put(configurations.get(0), 3);
        payment.put(configurations.get(1), 12);
        bookingRequests.add(new BookingRequest(id, 4, 6, payment));

        /* Expected output */
        int expectedProfit = 3;

        /* Execute the test and compare expected and actual results */
        int actualProfit = Dynamic.optimalProfitDynamic(k, configurations, bookingRequests);
        Assert.assertEquals(expectedProfit, actualProfit);
    }

    /** Basic test for boundary case: no booking requests */
    @Test
    public void basicTestOptimalProfitDynamic2() throws Exception {
        /* Initialise parameters to the test */
        int k = 11;

        ArrayList<Configuration> configurations = new ArrayList<>();
        int id;
        int setupTime;
        int teardownTime;
        ArrayList<Integer> cost;

        id = 0;
        setupTime = 1;
        teardownTime = 1;
        cost = new ArrayList<>(Arrays.asList(1, 0, 2, 1, 0, 0, 1, 1, 1, 5, 0));
        configurations.add(new Configuration(id, setupTime, teardownTime, cost));

        id = 1;
        setupTime = 2;
        teardownTime = 1;
        cost = new ArrayList<>(Arrays.asList(0, 6, 3, 1, 1, 1, 1, 1, 2, 0, 8));
        configurations.add(new Configuration(id, setupTime, teardownTime, cost));

        ArrayList<BookingRequest> bookingRequests = new ArrayList<>();

        /* Expected output */
        int expectedProfit = -8;

        /* Execute the test and compare expected and actual results */
        int actualProfit = Dynamic.optimalProfitDynamic(k, configurations, bookingRequests);
        Assert.assertEquals(expectedProfit, actualProfit);
    }

    /** Basic test from the handout. */
    @Test
    public void basicTestOptimalScheduleDynamic1() throws Exception {
        /* Initialise parameters to the test */
        int k = 11;

        ArrayList<Configuration> configurations = new ArrayList<>();
        int id;
        int setupTime;
        int teardownTime;
        ArrayList<Integer> cost;

        id = 0;
        setupTime = 1;
        teardownTime = 1;
        cost = new ArrayList<>(Arrays.asList(1, 0, 2, 1, 0, 0, 1, 1, 1, 5, 0));
        configurations.add(new Configuration(id, setupTime, teardownTime, cost));

        id = 1;
        setupTime = 2;
        teardownTime = 1;
        cost = new ArrayList<>(Arrays.asList(0, 6, 3, 1, 1, 1, 1, 1, 2, 0, 8));
        configurations.add(new Configuration(id, setupTime, teardownTime, cost));

        ArrayList<BookingRequest> bookingRequests = new ArrayList<>();
        HashMap<Configuration, Integer> payment;

        id = 0;
        payment = new HashMap<>();
        payment.put(configurations.get(0), 4);
        payment.put(configurations.get(1), 3);
        bookingRequests.add(new BookingRequest(id, 0, 1, payment));

        id++;
        payment = new HashMap<>();
        payment.put(configurations.get(0), 2);
        payment.put(configurations.get(1), 7);
        bookingRequests.add(new BookingRequest(id, 0, 0, payment));

        id++;
        payment = new HashMap<>();
        payment.put(configurations.get(0), 2);
        payment.put(configurations.get(1), 5);
        bookingRequests.add(new BookingRequest(id, 3, 3, payment));

        id++;
        payment = new HashMap<>();
        payment.put(configurations.get(0), 3);
        payment.put(configurations.get(1), 12);
        bookingRequests.add(new BookingRequest(id, 4, 6, payment));

        /* Expected output */
        int expectedProfit = 3;

        /* Execute the test and compare expected and actual results */
        Activity[] actualSchedule = Dynamic.optimalScheduleDynamic(k,
                new ArrayList<>(configurations), new ArrayList<>(bookingRequests));
        int actualProfit = checkSchedule(actualSchedule, k, configurations, bookingRequests);
        Assert.assertEquals(expectedProfit, actualProfit);
    }

    /** Basic test for boundary case: no booking requests */
    @Test
    public void basicTestOptimalScheduleDynamic2() throws Exception {
        /* Initialise parameters to the test */
        int k = 11;

        ArrayList<Configuration> configurations = new ArrayList<>();
        int id;
        int setupTime;
        int teardownTime;
        ArrayList<Integer> cost;

        id = 0;
        setupTime = 1;
        teardownTime = 1;
        cost = new ArrayList<>(Arrays.asList(1, 0, 2, 1, 0, 0, 1, 1, 1, 5, 0));
        configurations.add(new Configuration(id, setupTime, teardownTime, cost));

        id = 1;
        setupTime = 2;
        teardownTime = 1;
        cost = new ArrayList<>(Arrays.asList(0, 6, 3, 1, 1, 1, 1, 1, 2, 0, 8));
        configurations.add(new Configuration(id, setupTime, teardownTime, cost));

        ArrayList<BookingRequest> bookingRequests = new ArrayList<>();

        /* Expected output */
        int expectedProfit = -8;

        /* Execute the test and compare expected and actual results */
        Activity[] actualSchedule = Dynamic.optimalScheduleDynamic(k,
                new ArrayList<>(configurations), new ArrayList<>(bookingRequests));
        int actualProfit = checkSchedule(actualSchedule, k, configurations, bookingRequests);
        Assert.assertEquals(expectedProfit, actualProfit);
    }

    /*--------Helper methods------------*/

    /**
     * Checks that the schedule is valid with respect to inputs k, configurations and
     * bookingRequests, and returns the profit of the schedule.
     */
    private static int checkSchedule(Activity[] schedule, int k,
            ArrayList<Configuration> configurations, ArrayList<BookingRequest> bookingRequests) {
        int profit = 0; // the profit to be calculated

        // Check that schedule is not null and is for the correct number of days
        Assert.assertTrue(schedule != null && schedule.length == k);

        // Check the activity for each day of the schedule, and calculate the profit
        int day = 0;
        Configuration currentConfiguration = configurations.get(0);
        Activity nextActivity = null;
        while (day < k) {
            nextActivity = schedule[day];
            // the activity should be one of the predefined types of activities
            Assert.assertTrue(nextActivity instanceof Activity.Idle
                    || nextActivity instanceof Activity.HostEvent
                    || nextActivity instanceof Activity.Reconfigure);

            // the next activity should not be null
            Assert.assertTrue(nextActivity != null);
            // the next activity should start on the current day
            Assert.assertEquals(day, nextActivity.start());
            // there should be time to complete the activity
            Assert.assertTrue(nextActivity.end() < k);
            // its start configuration should equal the current configuration
            Assert.assertEquals(currentConfiguration, nextActivity.startConfiguration());
            // the end configuration should be one of the given configurations
            Assert.assertTrue(configurations.contains(nextActivity.endConfiguration()));
            // the booking (if there is one) should be one of the given booking requests
            if (nextActivity instanceof Activity.HostEvent) {
                Assert.assertTrue(
                        bookingRequests.contains(((Activity.HostEvent) nextActivity).booking()));
            }
            // nextActivity should be scheduled for all days of the activity
            while (day <= nextActivity.end()) {
                Assert.assertEquals(nextActivity, schedule[day]);
                day = day + 1;
            }
            currentConfiguration = nextActivity.endConfiguration();
            profit = profit + nextActivity.profit();
        }
        return profit;
    }

}
