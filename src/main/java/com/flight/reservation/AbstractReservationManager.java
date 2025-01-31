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

    boolean newReserveSeats(int row, int targetIndex, int totalSeats) {
        System.out.println("newReserveSeats...");

        if (getAvailableSeats(row, targetIndex) < totalSeats) {
            System.out.println("Not enough available seats");
            return false;
        }


        for (int i = targetIndex; i >= 0 && totalSeats > 0; i--) {
            Seat seat = flightSeats.getSeat(row, i);
            System.out.println("totalSeats is " + totalSeats + " at index " + i);

            if (!seat.isReserved()) {
                seat.reserve();
                totalSeats--;
            }
        }

        if (totalSeats > 0) {
            for (int i = targetIndex + 1; i < flightSeats.getColLength() && totalSeats > 0; i++) {
                Seat seat = flightSeats.getSeat(row, i);
                if (!seat.isReserved()) {
                    seat.reserve();
                    totalSeats--;
                }
            }
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

    private int getAvailableSeats(int row, int targetIndex) {
        // start from `targetIndex` and expand in both ways (left first)
        int leftCount = 0;
        for (int i = targetIndex; i >= 0; i--) {
            Seat seat = flightSeats.getSeat(row, i);
            if (!seat.isReserved()) {
                leftCount++;
            }
        }

        int rightCount = 0;
        for (int i = targetIndex + 1; i < flightSeats.getColLength(); i++) {
            Seat seat = flightSeats.getSeat(row, i);
            if (!seat.isReserved()) {
                rightCount++;
            }
        }
        return leftCount + rightCount;
    }

    private boolean areSeatsReserved(int row, int startCol, int endCol) {
        return IntStream.rangeClosed(startCol, endCol)
                .mapToObj(col -> flightSeats.getSeat(row, col))
                .allMatch(Seat::isReserved);
    }
}
