package com.flight.reservation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FlightSeatsTest {

    @Test
    void testDefaultFlightSeats() {
        FlightSeats flightSeats = new FlightSeats();

        // Ensure the default seat configuration is correct.
        assertEquals(20, flightSeats.getRowLength());
        assertEquals(8, flightSeats.getColLength());
        // Check that all seats are available (not reserved).
        for (int i = 0; i < flightSeats.getRowLength(); i++) {
            for (int j = 0; j < flightSeats.getColLength(); j++) {
                assertFalse(flightSeats.getSeat(i, j).isReserved());
            }
        }
    }

    @Test
    void testCustomFlightSeatsConstructor() {
        Seat[][] customSeats = new Seat[2][3];
        customSeats[0][0] = new Seat();
        customSeats[0][1] = new Seat();
        customSeats[0][2] = new Seat();
        customSeats[1][0] = new Seat();
        customSeats[1][1] = new Seat();
        customSeats[1][2] = new Seat();

        FlightSeats flightSeats = new FlightSeats(customSeats);

        // Validate the custom seat configuration.
        assertEquals(2, flightSeats.getRowLength());
        assertEquals(3, flightSeats.getColLength());
        // Check all seats are available (not reserved).
        for (int i = 0; i < flightSeats.getRowLength(); i++) {
            for (int j = 0; j < flightSeats.getColLength(); j++) {
                assertFalse(flightSeats.getSeat(i, j).isReserved());
            }
        }
    }

    @Test
    void testInvalidSeatSelection() {
        FlightSeats flightSeats = new FlightSeats();

        // Attempt to get a seat outside the bounds
        assertThrows(IllegalArgumentException.class, () -> flightSeats.getSeat(21, 0)); // Invalid row
        assertThrows(IllegalArgumentException.class, () -> flightSeats.getSeat(0, 8));  // Invalid column
    }
}
