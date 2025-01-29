package com.flight.reservation;

/**
 * A utility class that parses input arguments into a {@link Command}.
 * This class processes user input, validates it, and converts it into a structured {@code Command} object.
 * It ensures that actions, seat positions, and seat counts are correctly formatted before creating a command.
 */
public class CommandParser {

    /**
     * Parses a string array of arguments into a {@link Command} object.
     *
     * @param args A string array containing the action, seat position, and number of consecutive seats.
     * @return A {@link Command} object representing the parsed input.
     * @throws IllegalArgumentException If the number of arguments is incorrect.
     */
    public static Command parse(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("The number of input arguments isn't equal to 3.");
        }

        Action action = parseAction(args[0]);
        int[] position = parsePosition(args[1]);
        int consecutiveNumber = parseConsecutiveNumber(args[2]);

        return new Command.Builder()
                .setAction(action)
                .setRowNumber(position[0])
                .setColumnNumber(position[1])
                .setConsecutiveSeats(consecutiveNumber)
                .build();
    }

    private static Action parseAction(String actionName) {
        if (actionName.equals("BOOK")) {
            return Action.BOOK;
        } else if (actionName.equals("CANCEL")) {
            return Action.CANCEL;
        } else {
            throw new IllegalArgumentException("Invalid action name: " + actionName);
        }
    }

    private static int[] parsePosition(String position) {
        if (position.length() != 2) {
            throw new IllegalArgumentException("Invalid position format: " + position);
        }

        int row = position.charAt(0) - 'A'; // Converts row letter to index (A -> 0, B -> 1, etc.)
        int col = position.charAt(1) - '0'; // Converts column character to integer

        return new int[]{row, col};
    }

    private static int parseConsecutiveNumber(String consecutiveNumber) {
        try {
            return Integer.parseInt(consecutiveNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid consecutive number: " + consecutiveNumber);
        }
    }
}
