package com.flight.reservation;

/**
 * Manages seat reservations and cancellations for a flight.
 * This class extends {@link AbstractReservationManager} and provides a synchronized implementation
 * of the execute method to ensure thread safety while handling seat reservations.
 */
public class ReservationManager extends AbstractReservationManager {

    /**
     * Constructs a ReservationManager for managing flight seat reservations.
     *
     * @param flightSeats The {@link FlightSeats} object representing the seating arrangement.
     */
    public ReservationManager(FlightSeats flightSeats) {
        super(flightSeats);
    }

    /**
     * Executes a reservation or cancellation command on the flight seating arrangement.
     * This method ensures that only one thread can execute a command at a time by synchronizing
     * on the {@code ReservationManager} instance, preventing race conditions.
     *
     * @param command The {@link Command} containing details of the action (BOOK or CANCEL), row,
     *               column, and number of seats to modify.
     * @return true if the action succeeds, false otherwise.
     * Note: This method modifies the state of the {@link FlightSeats} object passed during initialization.
     */
    public boolean execute(Command command) {
        synchronized (this) { // Ensures thread safety when processing commands
            return command.getAction().execute(this, command);
        }
    }
}

