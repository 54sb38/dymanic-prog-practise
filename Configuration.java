package assignment2;

import java.util.*;

/**
 * A class representing a configuration that the venue can be in.
 * 
 * Each configuration has a set-up time and a tear-down time (in whole days), both of
 * which must be greater than or equal to one.
 * 
 * The cost of having the venue in a configuration c for day d is given by c.cost(d),
 * where c.cost(d) is greater than or equal to 0.
 * 
 * DO NOT MODIFY THIS FILE IN ANY WAY.
 */
public final class Configuration {

    /* The identifier of the configuration. */
    private final int id;
    /* The time (in whole days) to set up the configuration. */
    private final int setupTime;
    /* The time (in whole days) to tear down the configuration */
    private final int teardownTime;
    /*
     * The cost of the configuration per day. The cost for a day d is cost.get(d) if 0 <=
     * d < cost.size(), and is 0 otherwise.
     */
    private final ArrayList<Integer> cost;

    /**
     * Creates a new configuration with the given identifier, setup and tear-down times
     * (in whole days), and costs per day. The cost for a day d is cost.get(d) if 0 <= d <
     * cost.size(), and is 0 otherwise.
     */
    public Configuration(int id, int setupTime, int teardownTime, ArrayList<Integer> cost) {
        if (setupTime < 1 || teardownTime < 1) {
            throw new IllegalArgumentException(
                    "The setup and teardown times must be greater than or equal to one.");
        }
        if (cost == null || cost.contains(null)) {
            throw new IllegalArgumentException("The costs cannot be null");
        }
        for (int c : cost) {
            if (c < 0) {
                throw new IllegalArgumentException(
                        "The cost per day must be greater than or equal to zero.");
            }
        }
        this.id = id;
        this.setupTime = setupTime;
        this.teardownTime = teardownTime;
        this.cost = cost;
    }

    /** Returns the identifier of the configuration */
    public int id() {
        return id;
    }

    /** Returns the time (in whole days) to set up the configuration. */
    public int setupTime() {
        return setupTime;
    }

    /** Returns the time (in whole days) to tear down the configuration. */
    public int teardownTime() {
        return teardownTime;
    }

    /** Returns the cost of the configuration for the given day. */
    public int cost(int day) {
        if (0 <= day && day < cost.size()) {
            return cost.get(day);
        } else {
            return 0;
        }
    }

    /** Returns the cost of the configuration from firstDay to lastDay (inclusive). */
    public int cost(int firstDay, int lastDay) {
        int totalCost = 0;
        for (int d = firstDay; d <= lastDay; d++) {
            totalCost += cost(d);
        }
        return totalCost;
    }

    @Override
    public String toString() {
        return "c" + id + ": (setup=" + setupTime + ", teardown=" + teardownTime + ", cost="
                + cost.toString() + ")";
    }

}
