package com.flight.reservation;

/**
 * Represents a seat in the flight seating arrangement.
 */
public class Seat {
    // Boolean flag to indicate if the seat is reserved or not
    private boolean reserved;

    public Seat() {
        this.reserved = false; // The seat is available by default
    }

    /**
     * Checks if the seat is reserved.
     * @return True if the seat is reserved, false otherwise.
     */
    public boolean isReserved() {
        return reserved; // Returns the reservation status of the seat
    }

    /**
     * Reserves the seat, marking it as unavailable.
     */
    public void reserve() {
        this.reserved = true; // Marks the seat as reserved
    }

    /**
     * Cancels the reservation of the seat.
     */
    public void cancel() {
        this.reserved = false; // Marks the seat as available again
    }
}
