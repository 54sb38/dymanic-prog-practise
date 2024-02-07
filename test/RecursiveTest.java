package assignment2.test;

import assignment2.*;
import java.util.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * Basic tests for the {@link Recursive} implementation class.
 * 
 * We will use a much more comprehensive test suite to test your code, so you should add
 * your own tests to this test suite to help you to debug your implementation.
 */
public class RecursiveTest {

    /** Basic test from the handout. */
    @Test
    public void basicTestRecursive1() throws Exception {
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
        int actualProfit = Recursive.optimalProfitRecursive(k, configurations, bookingRequests);
        Assert.assertEquals(expectedProfit, actualProfit);
    }

    /** Basic test for boundary case: no booking requests */
    @Test
    public void basicTestRecursive2() throws Exception {
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
        int actualProfit = Recursive.optimalProfitRecursive(k, configurations, bookingRequests);
        Assert.assertEquals(expectedProfit, actualProfit);
    }

}
