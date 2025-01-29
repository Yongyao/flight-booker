package com.flight.reservation;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Manages the loading and saving of flight seating arrangements from/to a file.
 * <p>
 * This class provides methods to:
 * - Save the current seat arrangement of a flight to a file.
 * - Load a seating arrangement from a file and reconstruct the seat states.
 * <p>
 * The file format uses:
 * - "R" to represent a reserved seat.
 * - "E" to represent an empty seat.
 * - A comma (",") as the delimiter between seat states.
 */
public class FileManager {
    private final static String DELIMITER = ","; // Delimiter used in the file format
    private final static String RESERVED_SYMBOL = "R"; // Symbol representing a reserved seat
    private final static String EMPTY_SYMBOL = "E"; // Symbol representing an empty seat

    /**
     * Saves the current seating arrangement of a flight to a file.
     * <p>
     * Each row of the seating arrangement is written as a comma-separated line.
     *
     * @param flightSeats The FlightSeats object containing the seat layout.
     * @param fileName    The name of the file to save the seating arrangement.
     * @throws RuntimeException If an I/O error occurs while writing to the file.
     */
    public void saveToFile(FlightSeats flightSeats, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (int i = 0; i < flightSeats.getRowLength(); i++) {
                String rowRepresentation = createRowRepresentation(flightSeats, i);
                writer.println(rowRepresentation);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving seating arrangement to file: " + fileName, e);
        }
    }

    private String createRowRepresentation(FlightSeats flightSeats, int row) {
        return IntStream.range(0, flightSeats.getColLength())
                .mapToObj(col -> flightSeats.getSeat(row, col).isReserved() ? RESERVED_SYMBOL : EMPTY_SYMBOL)
                .collect(Collectors.joining(DELIMITER));
    }

    /**
     * Loads a seating arrangement from a file and reconstructs a 2D array of Seat objects.
     * <p>
     * Each row in the file is parsed into an array of Seat objects based on the symbols "R" and "E".
     *
     * @param fileName The name of the file to load the seating arrangement from.
     * @return A 2D array of Seat objects representing the flight's seating arrangement.
     * @throws RuntimeException If an I/O error occurs while reading the file.
     */
    public Seat[][] loadFromFile(String fileName) {
        List<Seat[]> output = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse the line into an array of Seat objects
                Seat[] seats = Arrays.stream(line.split(DELIMITER))
                        .map(this::createSeatFromSymbol)
                        .toArray(Seat[]::new);

                output.add(seats);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading seating arrangement from file: " + fileName, e);
        }

        // Convert the list of seat arrays into a 2D array
        return output.toArray(new Seat[0][0]);
    }

    private Seat createSeatFromSymbol(String symbol) {
        Seat seat = new Seat();
        if (RESERVED_SYMBOL.equals(symbol)) {
            seat.reserve();
        }
        return seat;
    }
}

