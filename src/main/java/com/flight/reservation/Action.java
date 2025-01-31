package com.flight.reservation;

/**
 * Enum representing actions that can be performed on flight seats.
 * Implements the {@link ActionInterface} and provides concrete implementations
 * for booking and canceling seats via the {@code execute} method.
 */
public enum Action implements ActionInterface {
    BOOK {
        @Override
        public boolean execute(AbstractReservationManager manager, Command command) {
            System.out.print("executing...");
            return manager.newReserveSeats(
                    command.getRowNumber(),
                    command.getColumnNumber(),
                    command.getConsecutiveSeats()
            );
        }
//        public boolean execute(AbstractReservationManager manager, Command command) {
//            return manager.reserveSeats(
//                    command.getRowNumber(),
//                    command.getColumnNumber(),
//                    command.getColumnNumber() + command.getConsecutiveSeats() - 1
//            );
//        }
    },

    CANCEL {
        @Override
        public boolean execute(AbstractReservationManager manager, Command command) {
            return manager.cancelSeats(
                    command.getRowNumber(),
                    command.getColumnNumber(),
                    command.getColumnNumber() + command.getConsecutiveSeats() - 1
            );
        }
    }
}

