package com.flight.reservation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservationManagerTest {

    @Test
    void reserveSeats_success() {
        ReservationManager reservationManager = new ReservationManager(new FlightSeats());

        boolean result = reservationManager.reserveSeats(1, 2, 4);

        assertTrue(result, "Seats should be reserved successfully.");
    }

    @Test
    void reserveSeats_alreadyReserved_fail() {
        ReservationManager reservationManager = new ReservationManager(new FlightSeats());
        reservationManager.reserveSeats(1, 2, 4); // First reservation should work

        boolean result = reservationManager.reserveSeats(1, 2, 4); // Second attempt should fail

        assertFalse(result, "Seats should not be reserved if already booked.");
    }

    @Test
    void cancelSeats_success() {
        ReservationManager reservationManager = new ReservationManager(new FlightSeats());
        reservationManager.reserveSeats(2, 3, 5);

        boolean result = reservationManager.cancelSeats(2, 3, 5);

        assertTrue(result, "Seats should be canceled successfully.");
    }

    @Test
    void cancelSeats_notReserved_fail() {
        ReservationManager reservationManager = new ReservationManager(new FlightSeats());

        boolean result = reservationManager.cancelSeats(3, 1, 3);

        assertFalse(result, "Seats should not be canceled if they were never reserved.");
    }

    @Test
    void executeBookCommand_success() {
        ReservationManager reservationManager = new ReservationManager(new FlightSeats());
        Command bookCommand = new Command.Builder()
                .setAction(Action.BOOK)
                .setRowNumber(4)
                .setColumnNumber(2)
                .setConsecutiveSeats(3)
                .build();

        boolean result = reservationManager.execute(bookCommand);

        assertTrue(result, "Booking should succeed through execute method.");
    }

    @Test
    void executeCancelCommand_seatsReserved_success() {
        ReservationManager reservationManager = new ReservationManager(new FlightSeats());
        Command bookCommand = new Command.Builder()
                .setAction(Action.BOOK)
                .setRowNumber(5)
                .setColumnNumber(1)
                .setConsecutiveSeats(2)
                .build();
        reservationManager.execute(bookCommand); // First, book the seats
        Command cancelCommand = new Command.Builder()
                .setAction(Action.CANCEL)
                .setRowNumber(5)
                .setColumnNumber(1)
                .setConsecutiveSeats(2)
                .build();

        boolean result = reservationManager.execute(cancelCommand);

        assertTrue(result, "Cancel should succeed for reserved seats through execute method");
    }

    @Test
    void executeBookCommand_alreadyReservedSeats_fail() {
        ReservationManager reservationManager = new ReservationManager(new FlightSeats());
        // First booking should succeed
        Command firstBookCommand = new Command.Builder()
                .setAction(Action.BOOK)
                .setRowNumber(6)
                .setColumnNumber(2)
                .setConsecutiveSeats(3)
                .build();
        reservationManager.execute(firstBookCommand);
        // Second booking for the same seats should fail
        Command secondBookCommand = new Command.Builder()
                .setAction(Action.BOOK)
                .setRowNumber(6)
                .setColumnNumber(2)
                .setConsecutiveSeats(3)
                .build();

        boolean result = reservationManager.execute(secondBookCommand);

        assertFalse(result, "Booking should fail when trying to reserve already booked seats.");
    }

    @Test
    void executeCancelCommand_unreservedSeats_fail() {
        ReservationManager reservationManager = new ReservationManager(new FlightSeats());
        // Attempting to cancel seats that were never reserved
        Command cancelCommand = new Command.Builder()
                .setAction(Action.CANCEL)
                .setRowNumber(7)
                .setColumnNumber(3)
                .setConsecutiveSeats(2)
                .build();

        boolean result = reservationManager.execute(cancelCommand);

        assertFalse(result, "Cancellation should fail when trying to cancel seats that were never reserved.");
    }
}