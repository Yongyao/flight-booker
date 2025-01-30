package com.flight.reservation;

import java.util.concurrent.atomic.AtomicReference;

public class ReservationManagerWithCAS extends AbstractReservationManager {
    private final AtomicReference<FlightSeats> flightSeatsRef;

    public ReservationManagerWithCAS(FlightSeats flightSeats) {
        super(flightSeats);
        this.flightSeatsRef = new AtomicReference<>(flightSeats);
    }

    public boolean execute(Command command) {
        while (true) {
            FlightSeats currentSeats = flightSeatsRef.get();
            FlightSeats updatedSeats;

            try {
                // Try to reserve seats, creating a new immutable instance
                // TODO: modify `reserveSeats()` in abstract manager to return a defensive copy
                // updatedSeats = command.getAction().execute(this, command);
                updatedSeats = null;
            } catch (IllegalStateException e) {
                // If seats are already reserved, fail the reservation
                return false;
            }

            // Attempt to atomically update the reference to the new state
            if (flightSeatsRef.compareAndSet(currentSeats, updatedSeats)) {
                return true; // Update successful
            }

            // If compareAndSet fails, another thread modified the state
            // Retry the operation with the new state
        }
    }
}
