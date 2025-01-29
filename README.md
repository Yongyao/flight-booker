### How to Use Flight Booker
- Build the Project

```
mvn clean install
```
- Use run.sh to execute the application
```
# Example: ./run.sh BOOK A1 2
./run.sh <ACTION> <SEAT_POSITION> <CONSECUTIVE_SEATS>
```

# Design Summary

This system manages the reservation and cancellation of seats for a flight.

## Algorithm Overview
1. Seating data is modeled as a 2D array and saved in a CSV-like file.
2. Given an input, we first validate and parse the user input into a group of
   parameters (e.g. action, row index).
3. If the command is valid, we load the seating data from the CSV file.
4. Execute the command:
- **BOOK**: Validates if all requested seats are available, then reserves them.
- **CANCEL**: Ensures that all seats are reserved before canceling them.

## Class Design

### 1. Seat
- Tracks whether a seat is reserved.
- Allows reservation and cancellation.

### 2. FlightSeats
- Manages the seating layout and validates seat availability.
- Represents a 2D array of `Seat` objects.
- Hides the underlying data model

### 3. ReservationManager (extends AbstractReservationManager)
- Handles seat reservations and cancellations.
- Validates seat availability before performing actions
- Provides thread safety by lock at the entire FlightSeats level .

### 4. ReservationManagerWithRowLevelLock (extends AbstractReservationManager)
- Same execution logic as ReservationManager
- A finer-grained lock ath only works at row level, which reduces
unnecessary contention.

### 5. Action (Enum)
- Defines available actions: `BOOK` and `CANCEL`.
- Executes actions via polymorphism.

### 6. Command
- Encapsulates user inputs (action, row, column, consecutive seats).
- Uses the builder pattern for flexible and safe construction of commands.

### 7. CommandParser
- Parses command-line arguments into a `Command` object.
- Validates user input.

### 8. FileManager
- Manages saving and loading of seating arrangements to/from files.

## Testing

Unit tests that verify the correctness of seat reservations, command parsing, etc.

## Design Considerations:

### - Modularity
The design provides a clean separation of concerns between business logic, user
input handling, and data storage.

### - Extensibility
The design allows easy extensions, e.g., 
- Adding a new `Action`
- Adding a new `ReservationManager` strategies.

### - Performance
The current synchronization strategy ensures thread safety but could be optimized
for larger systems using finer-grained locking.

### - Command Pattern
Command class and the Action enum encapsulates all the parameters. It allows easy
extensions, e.g.
- Add new parameters.
- Support undo a command.

### - Immutability
`FlightSeats` class creates a copy of the input 2D array to ensure consistent state.

## Future Considerations

### Concurrency Enhancements
Consider optimistic locking using CAS to reduce blocking, benefiting low-contention
scenarios despite potential retry overhead.

### Code Enhancements
- Dependency Injection: Using a framework like Spring would improve modularity, and
configuration management.
- Use Lombok to reduce boilerplate code in `Command` builder pattern.

### Undo/Redo Support
Implement command history to allow users to revert or redo reservations.

### Optimized File Operations
Use binary storage and `RandomAccessFile` for efficient row-level read/write,
reducing I/O overhead.

### System Integration
- Database Integration for persistent seating management.
- Payment System Integration for seamless booking transactions.

### Additional Features
- Seat Classes (first-class, economy, etc.).
- Cost calculation and dynamic pricing (e.g. discount) based on demand and seat
location.  
