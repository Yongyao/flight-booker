package com.flight.reservation;

/**
 * ReservationManagerWithRowLevelLock row-level locking for more fine-grained
 * concurrency control.
 */
public class ReservationManagerWithRowLevelLock extends AbstractReservationManager {
    // An array of locks, one per row.
    private final Object[] rowLocks;

    /**
     * Constructor for ReservationManagerWithRowLevelLock.
     * Initializes rowLocks array, where each element represents a lock for a specific row.
     *
     * @param flightSeats the FlightSeats object representing the seating arrangement
     */
    public ReservationManagerWithRowLevelLock(FlightSeats flightSeats) {
        super(flightSeats);

        // Create a new lock object for each row in the seating arrangement
        this.rowLocks = new Object[flightSeats.getRowLength()];
        for (int i = 0; i < rowLocks.length; i++) {
            rowLocks[i] = new Object();
        }
    }

    /**
     * Executes the given command on the flight seating arrangement.
     * This method ensures that only one thread can access a specific row for a reservation
     * or cancellation operation at a time, using row-level locking.
     *
     * @param command the Command containing the action, row, column, and seat details.
     * @return true if the action succeeds, false otherwise.
     */
    public boolean execute(Command command) {
        // Locking the row-specific lock to ensure only one thread can modify the row's seats at a time
        synchronized (rowLocks[command.getRowNumber()]) {
            boolean succeed = command.getAction().execute(this, command);
            System.out.println(succeed ? "SUCCESS" : "FAIL");
            return succeed;
        }
    }
}

