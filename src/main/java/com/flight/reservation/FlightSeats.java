package com.flight.reservation;

/**
 * Represents the entire seating chart of a flight.
 * <p>
 * This class manages the seating arrangement using a 2D array of {@link Seat} objects.
 * It provides methods to:
 * - Retrieve individual seats.
 * - Get the number of rows and columns.
 * - Validate seat positions.
 * <p>
 * The default seating configuration has 20 rows and 8 columns.
 */
public class FlightSeats {
    private final static int DEFAULT_ROW_LENGTH = 20; // Default number of rows
    private final static int DEFAULT_COL_LENGTH = 8;  // Default number of columns
    private final Seat[][] seats; // 2D array to hold the seat objects

    /**
     * Default constructor that initializes the flight seating arrangement with default values.
     */
    public FlightSeats() {
        seats = new Seat[DEFAULT_ROW_LENGTH][DEFAULT_COL_LENGTH];
        for (int i = 0; i < DEFAULT_ROW_LENGTH; i++) {
            for (int j = 0; j < DEFAULT_COL_LENGTH; j++) {
                seats[i][j] = new Seat(); // Initialize each seat in the array
            }
        }
    }

    /**
     * Constructor that initializes the flight seating arrangement from an existing 2D array of {@link Seat} objects.
     * <p>
     * This constructor allows creating a seating arrangement from a provided 2D array of seats.
     *
     * @param inputSeats A 2D array of {@link Seat} objects representing the seating arrangement.
     * @throws IllegalArgumentException If the input array is invalid.
     */
    public FlightSeats(Seat[][] inputSeats) {
        if (inputSeats == null || inputSeats.length == 0 || inputSeats[0] == null) {
            throw new IllegalArgumentException("Input seats array cannot be null or empty.");
        }

        int rows = inputSeats.length;
        int cols = inputSeats[0].length;

        // Validate uniform column sizes for all rows
        for (Seat[] row : inputSeats) {
            if (row == null || row.length != cols) {
                throw new IllegalArgumentException("All rows in the input must have the same number of columns.");
            }
        }

        // Deep copy the input seats array
        seats = new Seat[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                seats[i][j] = new Seat(inputSeats[i][j]); // Create a new Seat instance
            }
        }
    }

    /**
     * Retrieves the seat at the specified row and column.
     *
     * @param row The row number of the seat.
     * @param col The column number of the seat.
     * @return The {@link Seat} object at the specified position.
     * @throws IllegalArgumentException If the specified seat coordinates are invalid.
     */
    public Seat getSeat(int row, int col) {
        if (isValidSeat(row, col)) {
            return seats[row][col];
        } else {
            throw new IllegalArgumentException("Invalid seat selection.");
        }
    }

    /**
     * Gets the total number of rows in the seating arrangement.
     *
     * @return The number of rows.
     */
    public int getRowLength() {
        return seats.length;
    }

    /**
     * Gets the total number of columns in the seating arrangement.
     *
     * @return The number of columns.
     */
    public int getColLength() {
        return seats[0].length;
    }

    private boolean isValidSeat(int row, int col) {
        return row >= 0 && row < seats.length && col >= 0 && col < seats[row].length;
    }
}
