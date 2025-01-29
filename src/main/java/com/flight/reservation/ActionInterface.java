package com.flight.reservation;

/**
 * Defines an interface for actions that can be performed on flight seats.
 * Implementing enums provide an implementation for the {@code execute} method,
 * which handles seat reservations and cancellations.
 */
public interface ActionInterface {

    /**
     * Executes an action (such as booking or canceling seats) on the given reservation manager.
     *
     * @param manager The {@link AbstractReservationManager} responsible for seat management.
     * @param command The {@link Command} containing details such as row number, column number,
     *                and number of consecutive seats to modify.
     * @return true if the action succeeds, false otherwise.
     */
    boolean execute(AbstractReservationManager manager, Command command);
}
