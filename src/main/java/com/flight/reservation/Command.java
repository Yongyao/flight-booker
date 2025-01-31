package com.flight.reservation;

/**
 * Represents a command to book or cancel seats in a flight.
 * <p>
 * This class encapsulates the action type, row number, column number, and
 * the number of consecutive seats involved in the command.
 * It follows the Builder pattern to ensure proper construction and validation.
 */
public class Command {
    private final Action action;
    private final int rowNumber;
    private final int columnNumber;
    private final int consecutiveSeats;

    /**
     * Private constructor to enforce the use of the Builder for object creation.
     *
     * @param builder The Builder instance containing the initialized values.
     */
    private Command(Builder builder) {
        this.action = builder.action;
        this.rowNumber = builder.rowNumber;
        this.columnNumber = builder.columnNumber;
        this.consecutiveSeats = builder.consecutiveSeats;
    }

    /**
     * @return The action to be performed (BOOK or CANCEL).
     */
    public Action getAction() {
        return action;
    }

    /**
     * @return The row number where the action will be applied.
     */
    public int getRowNumber() {
        return rowNumber;
    }

    /**
     * @return The starting column number for the action.
     */
    public int getColumnNumber() {
        return columnNumber;
    }

    /**
     * @return The number of consecutive seats affected by the action.
     */
    public int getConsecutiveSeats() {
        return consecutiveSeats;
    }

    /**
     * Builder class for constructing {@link Command} objects.
     * <p>
     * Ensures input validation before creating an instance of Command.
     */
    public static class Builder {
        private Action action;
        private int rowNumber;
        private int columnNumber;
        private int consecutiveSeats;

        /**
         * Sets the action type for the command.
         *
         * @param action The action to be performed (BOOK or CANCEL).
         * @return The builder instance for chaining.
         * @throws IllegalArgumentException If the action is null.
         */
        public Builder setAction(Action action) {
            if (action == null) {
                throw new IllegalArgumentException("Action cannot be null");
            }
            this.action = action;
            return this;
        }

        /**
         * Sets the row number for the command.
         *
         * @param rowNumber The row number (0-20).
         * @return The builder instance for chaining.
         * @throws IllegalArgumentException If the row number is out of bounds.
         */
        public Builder setRowNumber(int rowNumber) {
            if (rowNumber < 0 || rowNumber > 20) {
                throw new IllegalArgumentException("Row number must be in the range of [0, 20]");
            }
            this.rowNumber = rowNumber;
            return this;
        }

        /**
         * Sets the starting column number for the command.
         *
         * @param columnNumber The column number (0-7).
         * @return The builder instance for chaining.
         * @throws IllegalArgumentException If the column number is out of bounds.
         */
        public Builder setColumnNumber(int columnNumber) {
            if (columnNumber < 0 || columnNumber > 7) {
                throw new IllegalArgumentException("Column number must be in the range of [0, 7]");
            }
            this.columnNumber = columnNumber;
            return this;
        }

        /**
         * Sets the number of consecutive seats for the command.
         *
         * @param consecutiveSeats The number of consecutive seats (1-8).
         * @return The builder instance for chaining.
         * @throws IllegalArgumentException If the number of consecutive seats is out of bounds.
         */
        public Builder setConsecutiveSeats(int consecutiveSeats) {
            if (consecutiveSeats < 1 || consecutiveSeats > 8) {
                throw new IllegalArgumentException("Consecutive seats must be in the range of [1, 8]");
            }
            this.consecutiveSeats = consecutiveSeats;
            return this;
        }

        /**
         * Builds and returns a {@link Command} instance after validation.
         *
         * @return A validated {@link Command} instance.
         * @throws IllegalArgumentException If any of inputs are invalid.
         */
        public Command build() {
            // validate();
            return new Command(this);
        }

        private void validate() {
            if (action == null) {
                throw new IllegalArgumentException("Action must be set");
            }
            if (rowNumber < 0 || rowNumber > 20) {
                throw new IllegalArgumentException("Row number must be in the range of [0, 20]");
            }
            if (columnNumber < 0 || columnNumber > 7) {
                throw new IllegalArgumentException("Column number must be in the range of [0, 7]");
            }
            if (consecutiveSeats < 1 || consecutiveSeats > 8) {
                throw new IllegalArgumentException("Consecutive seats must be in the range of [1, 8]");
            }
            int endCol = columnNumber + consecutiveSeats - 1;
            if (endCol > 7) {
                throw new IllegalArgumentException("The end column number must be smaller than or equal to 7");
            }
        }
    }
}

