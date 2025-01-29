package com.flight.reservation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void testCommandBuilder_createsValidCommand() {
        Command command = new Command.Builder()
                .setAction(Action.BOOK)
                .setRowNumber(5)
                .setColumnNumber(2)
                .setConsecutiveSeats(3)
                .build();

        assertEquals(Action.BOOK, command.getAction());
        assertEquals(5, command.getRowNumber());
        assertEquals(2, command.getColumnNumber());
        assertEquals(3, command.getConsecutiveSeats());
    }

    @Test
    void testCommandBuilder_invalidRow_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Command.Builder()
                    .setAction(Action.BOOK)
                    .setRowNumber(-1)  // Invalid row
                    .setColumnNumber(2)
                    .setConsecutiveSeats(3)
                    .build();
        });

        assertTrue(exception.getMessage().contains("Row number must be in the range"));
    }

    @Test
    void testCommandBuilder_invalidColumn_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Command.Builder()
                    .setAction(Action.BOOK)
                    .setRowNumber(5)
                    .setColumnNumber(10)  // Invalid column
                    .setConsecutiveSeats(3)
                    .build();
        });

        assertTrue(exception.getMessage().contains("Column number must be in the range"));
    }

    @Test
    void testCommandBuilder_invalidConsecutiveSeats_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Command.Builder()
                    .setAction(Action.BOOK)
                    .setRowNumber(5)
                    .setColumnNumber(2)
                    .setConsecutiveSeats(10)  // Too many seats
                    .build();
        });

        assertTrue(exception.getMessage().contains("Consecutive seats must be in the range"));
    }

    @Test
    void testCommandBuilder_nullAction_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Command.Builder()
                    .setAction(null)  // Null action
                    .setRowNumber(5)
                    .setColumnNumber(2)
                    .setConsecutiveSeats(3)
                    .build();
        });

        assertTrue(exception.getMessage().contains("Action cannot be null"));
    }

    @Test
    void testCommandBuilder_endColumnExceedingLimit_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Command.Builder()
                    .setAction(Action.BOOK)
                    .setRowNumber(5)
                    .setColumnNumber(6)  // Column 6 + 3 seats → Exceeds limit of 7
                    .setConsecutiveSeats(3)
                    .build();
        });

        assertTrue(exception.getMessage().contains("The end column number must be smaller than or equal to 7"));
    }
}
