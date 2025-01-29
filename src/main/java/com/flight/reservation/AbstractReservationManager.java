package com.flight.reservation;

import java.util.stream.IntStream;

/**
 * Abstract base class for managing seat reservations on a flight.
 * Provides common functionalities for reserving and canceling seats,
 * while allowing subclasses to define the execution logic.
 */
public abstract class AbstractReservationManager {
    private final FlightSeats flightSeats;

    /**
     * Constructor to initialize the reservation manager with a flight seat layout.
     *
     * @param flightSeats The seating arrangement of the flight.
     */
    public AbstractReservationManager(FlightSeats flightSeats) {
        this.flightSeats = flightSeats;
    }

    /**
     * Executes a given reservation command.
     * The actual implementation is provided by subclasses.
     *
     * @param command The command containing seat reservation details.
     * @return true if the operation succeeds, false otherwise.
     */
    public abstract boolean execute(Command command);

    boolean reserveSeats(int row, int startCol, int endCol) {
        if (!areSeatsAvailable(row, startCol, endCol)) return false;

        for (int i = startCol; i <= endCol; i++) {
            flightSeats.getSeat(row, i).reserve();
        }
        return true;
    }

    boolean cancelSeats(int row, int startCol, int endCol) {
        if (!areSeatsReserved(row, startCol, endCol)) return false;

        for (int i = startCol; i <= endCol; i++) {
            flightSeats.getSeat(row, i).cancel();
        }
        return true;
    }

    private boolean areSeatsAvailable(int row, int startCol, int endCol) {
        return IntStream.rangeClosed(startCol, endCol)
                .mapToObj(col -> flightSeats.getSeat(row, col))
                .noneMatch(Seat::isReserved);
    }

    private boolean areSeatsReserved(int row, int startCol, int endCol) {
        return IntStream.rangeClosed(startCol, endCol)
                .mapToObj(col -> flightSeats.getSeat(row, col))
                .allMatch(Seat::isReserved);
    }
}
