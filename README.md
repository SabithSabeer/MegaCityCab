# Mega City Cab: Online Reservation System

## Project Overview
This project is an online reservation system for Mega City Cab, a taxi booking service. The system allows customers to book rides, manage their accounts, and provides administrative features for managing customers, drivers, vehicles, and bookings.

## System Architecture
The application follows several design patterns and SOLID principles:

### Design Patterns
1. **DAO Pattern (Data Access Object)**
   - Separates database logic from business logic
   - Package: `dao`
   - Implements Single Responsibility Principle

2. **Service Layer Pattern**
   - Manages business logic separate from request handling
   - Package: `services` 
   - Implements Open/Closed Principle

3. **MVC Pattern**
   - Model: `models` package (represents data)
   - View: HTML, CSS, JavaScript front-end
   - Controller: `servlets` package
   - Implements Dependency Inversion Principle

4. **Singleton Pattern**
   - Ensures single database connection instance
   - Package: `config`
   - Implements Single Responsibility Principle

### SOLID Principles Implementation
- **Single Responsibility Principle (SRP)**: Each component has a single responsibility
- **Open/Closed Principle (OCP)**: System is designed for extension without modification
- **Liskov Substitution Principle (LSP)**: Allows different implementations to be swapped seamlessly
- **Interface Segregation Principle (ISP)**: Separate interfaces for different components
- **Dependency Inversion Principle (DIP)**: High-level modules depend on abstractions

## Features
- Customer registration and login
- Driver registration and login
- Admin dashboard and management
- Booking system with vehicle selection
- Customer management system
- Vehicle management system
- Driver management system
- Admin management system

## Version History
| Version | Date | Description |
|---------|------|-------------|
| V1.0 | 01.03.2025 | Initial commit of the project |
| V1.1 | 01.03.2025 | Added database connection |
| V1.2 | 02.03.2025 | Added login & signup page |
| V1.3 | 02.03.2025 | Added Customer Dashboard |
| V1.4 | 04.03.2025 | Added Booking Dashboard |
| V1.5 | 05.03.2025 | Added Admin Login |
| V1.6 | 05.03.2025 | Admin Login Backend |
| V1.7 | 06.03.2025 | Customer Management System |
| V1.8 | 08.03.2025 | Completed the Customer Management System |
| V1.9 | 08.03.2025 | Completed vehicle management system |
| V1.10 | 09.03.2025 | Completed Driver Management System |
| V1.11 | 10.03.2025 | Completed Admin management System |

## Testing
The system includes comprehensive testing:
- Unit tests for service classes using JUnit
- Manual test cases for UI components
- Test cases cover form validation, login functionality, booking process, and general UI components

## Project Information
- **Course**: CIS6003 - Advanced Programming 
- **Student**: Mohamed Sabith Sabeer (CL/BSCSD/30/24)
- **Date**: March 2025
