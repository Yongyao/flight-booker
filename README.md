# Design Summary

This system manages the reservation and cancellation of seats for a flight.

## How to Use Flight Booker
- Build the project and create a JAR file
```
mvn clean install
```
- Execute the application
```
# Example: ./run.sh BOOK A1 2
./run.sh <ACTION> <SEAT_POSITION> <CONSECUTIVE_SEATS>
```

## Algorithm
- Seating data is modeled as a boolean 2D array
  - `true` means reserved, while `false` means empty.
  - Saved in a CSV-like file.
- Given an input, we first validate and parse the user input into a group of
parameters (e.g. action, row index, etc.). 
- If the input is valid, we load the seating data from the CSV file.
- Execute the action:
  - *BOOK*: Validates if all requested seats are available, then reserves them.
  - *CANCEL*: Ensures that all seats are reserved before canceling them.

## Class Design

### Seat
- Contains the state of a seat (i.e. whether it's reserved).
- Easy to extend to add other states or attributes

### FlightSeats
- Represents a 2D array of `Seat` objects.
- Hides and protects the underlying data model
  - If we change to a different model (e.g. hashmap), client code be won't 
  get affected.
  - Provide immutability

### Action (Enum)
- Defines available actions: `BOOK` and `CANCEL`.

### Command
- Encapsulates user inputs (action, row, column, consecutive seats).
- Uses the builder pattern for construction.
- A command is always valid and immutable after construction.

### CommandParser
- Parses command-line arguments into a `Command` object.
- Validates user input.

### ReservationManager (extends AbstractReservationManager)
- Handles both reservations and cancellations.
  - Actual logic lies in `Action` via polymorphism.
- Provides thread safety through a lock at the FlightSeats level.

### ReservationManagerWithRowLevelLock
- Same execution logic as ReservationManager
- A finer-grained lock only works at row level, which reduces
  unnecessary contention.

### FileManager
- Manages saving and loading of seating arrangements to/from files.

## Testing

Unit tests cover,
- Seat reservation and cancellation
- Command parsing and building
- Flight seats construction

## Design Considerations

### - Modularity
The design provides a separation of concerns between business logic, user
input handling, and data storage.

### - Extensibility
The design allows easy extensions, e.g., 
- Add a seat type attribute in `SEAT`
- Add a new `Action` without changing `ReservationManager`
- Add a new `ReservationManager` strategy.

### - Performance
The current synchronization strategy ensures thread safety but could be optimized
for larger systems using finer-grained locking.
- `ReservationManagerWithRowLevelLock`

### - Command Pattern
Command class and the Action enum encapsulates all the parameters. It allows easy
extensions, e.g.
- Add new parameters.
- Support undo a command.

### - Immutability
- Once a `FlightSeats` instance is created, we cannot modify the underlying array.
- Once a command is built, it won't get changed anymore.

## Future Considerations

### Optimized File Operations
Use binary storage and `RandomAccessFile` for efficient row-level read/write,
reducing I/O overhead.

### Concurrency Enhancements
Consider optimistic locking using CAS to reduce blocking, benefiting low-contention
scenarios despite potential retry overhead.

### Code Enhancements
- Dependency Injection: Using a framework like Spring would improve modularity, and
configuration management.
- Use tools like Lombok to reduce boilerplate code in `Command` builder pattern.- Better error Handling & Logging 
  - Integrate structured logging (e.g., Log4j) to track issues effectively.
  - Implement exception hierarchy.

### Undo/redo Support
Implement command history to allow users to revert or redo reservations.

### System Integration
- Database Integration for persistent seating management.
- Payment System Integration for seamless booking experience.

### Additional Features
- Seat Classes (first-class, economy, etc.).
- Cost calculation and dynamic pricing based on demand and seat location.  
