package assignment2;

import java.util.*;

/**
 * A class representing a booking request for an event.
 * 
 * Each booking request has a start day and an end day, where the start day must be
 * greater than or equal to zero and the end day must be greater than or equal to the
 * start day.
 * 
 * Each booking request has a payment that would be received for hosting the event,
 * dependent on the configuration of the venue. Each possible payment is greater than or
 * equal to zero.
 * 
 * DO NOT MODIFY THIS FILE IN ANY WAY.
 */
public class BookingRequest {

    /* The identifier of the booking request. */
    private final int id;
    /* The first day of the event. */
    private final int start;
    /* The last day of the event. */
    private final int end;
    /*
     * The payment that would be received for hosting the event, dependent on the
     * configuration of the venue. The payment for hosting the event in a configuration c
     * is payment.get(c) if payment.containsKey(c), and is 0 otherwise.
     */
    private final HashMap<Configuration, Integer> payment;

    /**
     * Creates a new booking request with the given identifier, start and end days, and
     * payments (relative to configurations). The payment for hosting the event in a
     * configuration c is payment.get(c) if payment.containsKey(c), and is 0 otherwise.
     */
    public BookingRequest(int id, int start, int end, HashMap<Configuration, Integer> payment) {
        if (!(0 <= start && start <= end)) {
            throw new IllegalArgumentException("Illegal start and finish times.");
        }
        if (payment == null || payment.keySet().contains(null) || payment.values().contains(null)) {
            throw new IllegalArgumentException("payment cannot be null or contain null values");
        }
        for (int p : payment.values()) {
            if (p < 0) {
                throw new IllegalArgumentException(
                        "payments must be greater than or equal to zero");
            }
        }
        this.id = id;
        this.start = start;
        this.end = end;
        this.payment = payment;
    }

    /**
     * Returns the identifier of the booking.
     */
    public int id() {
        return id;
    }

    /** Returns the first day of the event. */
    public int start() {
        return start;
    }

    /** Returns the last day of the event. */
    public int end() {
        return end;
    }

    /**
     * Returns the payment that would be received for hosting the event in the given
     * configuration.
     */
    public int payment(Configuration configuration) {
        if (payment.containsKey(configuration)) {
            return payment.get(configuration);
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        String paymentSummary = "{";
        for (Configuration configuration : payment.keySet()) {
            paymentSummary += " (c" + configuration.id() + " -> $" + payment.get(configuration)
                    + ") ";
        }
        paymentSummary += "}";
        return "b" + id + ": (start=" + start + ", end=" + end + ", payment=" + paymentSummary
                + ")";
    }

}
