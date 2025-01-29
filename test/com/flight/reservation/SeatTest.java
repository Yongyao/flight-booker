package com.flight.reservation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SeatTest {

    @Test
    void testSeatIsInitiallyUnreserved() {
        Seat seat = new Seat();
        assertFalse(seat.isReserved(), "New seat should not be reserved by default.");
    }

    @Test
    void reserveSeat() {
        Seat seat = new Seat();
        seat.reserve();
        assertTrue(seat.isReserved(), "Seat should be reserved after calling reserve().");
    }

    @Test
    void cancelReservation() {
        Seat seat = new Seat();
        seat.reserve();  // First reserve the seat
        seat.cancel();   // Then cancel the reservation
        assertFalse(seat.isReserved(), "Seat should be unreserved after calling cancel().");
    }
}
