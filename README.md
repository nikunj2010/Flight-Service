# Flight Service

A Spring Boot-based Flight Management System for managing flights, airports, airplanes, and seats.

## 1. Project Setup

### Prerequisites

- Java 17+
- Maven 3.9+
- MySQL Server

### Steps

1. **Clone the repository**
   ```sh
   git clone <your-repo-url>
   cd Flight-Service-nikunj
   ```

2. **Configure Database**
   - Create a MySQL database named `flight_booking_system`.
   - Update credentials in [`src/main/resources/application.properties`](src/main/resources/application.properties) if needed:
     ```
     spring.datasource.url=jdbc:mysql://localhost:3306/flight_booking_system
     spring.datasource.username=root
     spring.datasource.password=root
     ```

3. **Build the project**
   ```sh
   ./mvnw clean install
   ```

4. **Run the application**
   ```sh
   ./mvnw spring-boot:run
   ```

## 2. Database Schema

The main tables are:

- **flights**
  - `id` (PK)
  - `flightNumber` (unique)
  - `airplaneId` (FK)
  - `departureAirportId` (FK)
  - `arrivalAirportId` (FK)
  - `departureTime`, `arrivalTime`
  - `price`, `boardingGate`
  - `totalSeats`, `availableSeats`
  - `isCancelled`
  - `created_at`, `updated_at`

- **airports**
  - `id` (PK)
  - `name`, `code` (unique)
  - `address`, `city_name`
  - `created_at`, `updated_at`

- **airplanes**
  - `id` (PK)
  - `modelNumber`
  - `capacity`
  - `created_at`, `updated_at`

- **seats**
  - `id` (PK)
  - `row`, `col`
  - `airplaneId` (FK)
  - `type`
  - `isBooked`
  - `created_at`, `updated_at`

## 3. Database Integration

- Uses Spring Data JPA for ORM.
- MySQL connection is configured in [`src/main/resources/application.properties`](src/main/resources/application.properties).
- Entities are defined in [`com.cdac.acts.flightService.entity`](src/main/java/com/cdac/acts/flightService/entity).
- Repositories are in [`com.cdac.acts.flightService.repository`](src/main/java/com/cdac/acts/flightService/repository).

## 4. Essential Information

- **API Endpoints:** Defined in [`FlightController`](src/main/java/com/cdac/acts/flightService/controller/FlightController.java).
- **Exception Handling:** See [`FlightExceptionHandler`](src/main/java/com/cdac/acts/flightService/exceptions/FlightExceptionHandler.java).
- **Response Wrapper:** All responses use [`ResponsePayload`](src/main/java/com/cdac/acts/flightService/responseWrapper/ResponsePayload.java).
- **Service Layer:** Business logic is in [`FlightServiceImpl`](src/main/java/com/cdac/acts/flightService/service/FlightServiceImpl.java).
- **Unit Tests:** Example test in [`FlightServiceApplicationTests`](src/test/java/com/cdac/acts/flightService/FlightServiceApplicationTests.java).

## 5. Useful Commands

- **Build:** `./mvnw clean install`
- **Run:** `./mvnw spring-boot:run`
- **Test:** `./mvnw test`

## 6. Troubleshooting

- Ensure MySQL is running and accessible.
- Check database credentials in `application.properties`.
- For port conflicts, change the server port in `application.properties`:
  ```
  server.port=8080
  ```

## 7. Contact

For issues, please open a GitHub issue or contact the