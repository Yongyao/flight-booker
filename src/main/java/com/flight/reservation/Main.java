package com.flight.reservation;

import java.io.File;

/**
 * Entry point of the flight reservation system.
 */
public class Main {
    private final static String SEATING_FILE = "seating_chart.txt";

    public static void main(String[] args) {
        try {
            // Parse and validate inputs
            Command command = CommandParser.parse(args);

            // Load all the seat states from a local file
            FlightSeats flightSeats = new FlightSeats();
            FileManager fileManager = new FileManager();
            if (new File(SEATING_FILE).exists()) {
                flightSeats = new FlightSeats(fileManager.loadFromFile(SEATING_FILE));
            }

            // Initialize a reservation manager with the seat states
            AbstractReservationManager reservationManager = new ReservationManager(flightSeats);
            // AbstractReservationManager reservationManager = new ReservationManagerWithRowLevelLock(flightSeats);

            // Execute the command
            boolean result = reservationManager.execute(command);
            System.out.println(result ? "SUCCESS" : "FAIL");

            // Save the updated seating arrangement
            fileManager.saveToFile(flightSeats, SEATING_FILE);
        } catch (RuntimeException e) {
            // The question specifically asks to only print out "FAIL".
            // In production code, we should treat each exception type differently and
            // create custom exception if necessary.
            System.out.println("FAIL");
        }
    }

}
