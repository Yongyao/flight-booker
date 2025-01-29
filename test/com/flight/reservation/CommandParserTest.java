package com.flight.reservation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    @Test
    void parseValidBookCommand() {
        String[] args = {"BOOK", "A3", "2"};

        Command command = CommandParser.parse(args);

        assertEquals(Action.BOOK, command.getAction());
        assertEquals(0, command.getRowNumber());
        assertEquals(3, command.getColumnNumber());
        assertEquals(2, command.getConsecutiveSeats());
    }

    @Test
    void parseValidCancelCommand() {
        String[] args = {"CANCEL", "B5", "3"};

        Command command = CommandParser.parse(args);

        assertEquals(Action.CANCEL, command.getAction());
        assertEquals(1, command.getRowNumber());
        assertEquals(5, command.getColumnNumber());
        assertEquals(3, command.getConsecutiveSeats());
    }

    @Test
    void parseInvalidAction_throwsException() {
        String[] args = {"RESERVE", "C2", "2"};

        assertThrows(IllegalArgumentException.class, () -> CommandParser.parse(args));
    }

    @Test
    void parseInvalidPositionFormat_throwsException() {
        String[] args = {"BOOK", "C22", "2"};

        assertThrows(IllegalArgumentException.class, () -> CommandParser.parse(args));
    }
}

