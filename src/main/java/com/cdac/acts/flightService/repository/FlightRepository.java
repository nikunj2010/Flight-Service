package com.cdac.acts.flightService.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdac.acts.flightService.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>{

	@Query("SELECT f FROM Flight f " +
			"WHERE f.departureAirportId = :departureId " +
			"AND f.arrivalAirportId = :arrivalId " +
			"AND f.departureTime > :departureDate " +
			"AND f.availableSeats >= :passengers")
	Optional<List<Flight>> findFlightsForOneWay(
			@Param("departureId") Long departureId,
			@Param("arrivalId") Long arrivalId,
			@Param("departureDate") LocalDateTime departureDate,
			@Param("passengers") int passengers
			);

	boolean existsByFlightNumber(String flightNumber);

	@Query(
			value = "SELECT EXISTS (SELECT 1 FROM bookings WHERE flightId = (SELECT id FROM flights WHERE flightNumber = :flightNumber))",
			nativeQuery = true
			)
	Long bookingExistsByFlightNumber(@Param("flightNumber") String flightNumber);

	
	void deleteByFlightNumber(String flightNumber);
}
