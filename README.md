# Resilience Engine

## Overview

A Java-based execution framework designed to manage task resilience in unstable environments. It implements advanced Design Patterns to handle execution failures using modular strategies, protecting system resources from cascading failures through intelligent state management.

## Key Patterns Applied

- **Strategy Pattern**: Decouples retry and circuit breaker logic from the execution engine
- **State Pattern**: Implements a three-state Circuit Breaker (Closed, Open, Half-Open) to manage system health
- **Observer Pattern**: Provides a decoupled monitoring layer to log failures without affecting business logic
- **Command Pattern**: Encapsulates operations into generic Task objects

## Technical Features

- **Generics**: Full support for typed task returns using Java Generics
- **Custom Exception Hierarchy**: Specific resilience error handling via `ResilienceException`
- **Resource Protection**: Immediate request blocking when the system detects persistent external failures

## Project Structure

```
resilience-engine/
├── src/
│   ├── core/
│   │   ├── Task interfaces
│   │   ├── Generic Executor
│   │   └── Custom exceptions
│   ├── strategies/
│   │   ├── FixedRetry
│   │   ├── ExponentialBackoff
│   │   └── CircuitBreaker
│   ├── monitoring/
│   │   ├── Observer interfaces
│   │   └── Logging implementations
│   └── tasks/
│       └── Concrete implementations (e.g., NetworkTask)
└── README.md
```

## Requirements

- JDK 17 or higher

## Execution

### Compilation

```bash
javac -d bin src/**/*.java src/*.java
```

### Running

```bash
java -cp bin Main
```

## Academic Context

**Course**: Advanced Object-Oriented Programming (Programação Orientada a Objetos Avançada)  
**Department**: Department of Computing (DC)  
**Institution**: Federal University of São Carlos (UFSCar)

## Design Patterns Reference

### Strategy Pattern
Allows the selection of retry algorithms at runtime, enabling flexible failure handling strategies without modifying the core execution logic.

### State Pattern
The Circuit Breaker transitions between three states:
- **Closed**: Normal operation, requests pass through
- **Open**: Failure threshold exceeded, requests are blocked
- **Half-Open**: Testing phase to check if the system has recovered

### Observer Pattern
Monitoring components observe execution events without tight coupling to the execution engine, facilitating extensible logging and metrics collection.

### Command Pattern
Tasks are encapsulated as first-class objects, enabling queuing, scheduling, and execution of operations in a uniform manner.

## License

This project is developed for academic purposes at UFSCar.

---

**Developed with ☕ and resilience patterns**
