package com.cdac.acts.flightService.repository;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdac.acts.flightService.entity.Airport;
import com.cdac.acts.flightService.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>{

	// Query to filter flights based on one-way search criteria
	@Query(
			value = """
					SELECT 
					    *
					    from flights f
					WHERE f.departureairportid = :departureId
					AND f.arrivalairportid = :arrivalId
					AND DATE(f.departuretime) = DATE(:departureDate)
					AND f.availableseats >= :passengers
					""",
					nativeQuery = true
			)
	List<Flight> findFlightsForOneWay(
			@Param("departureId") Long departureId,
			@Param("arrivalId") Long arrivalId,
			@Param("departureDate") LocalDateTime departureDate,
			@Param("passengers") int passengers
			);

	// Check if a flight with the given flight number exists
	boolean existsByFlightNumberAndIsCancelledFalse(String flightNumber);

	// Check if bookings exist for a given flightNumber
	@Query(
			value = "SELECT EXISTS (SELECT 1 FROM bookings "
					+ "WHERE "
					+ "flightId = (SELECT id FROM flights WHERE flightNumber = :flightNumber)"
					+ "AND status = 'CONFIRMED')",
			nativeQuery = true
			)
	Long bookingExistsByFlightNumber(@Param("flightNumber") String flightNumber);

	// Delete flight by flight number
	@Modifying
	@Query(value = "update flights set isCancelled = true where flightnumber = :flightNumber ", nativeQuery = true)
	void deleteByFlightNumber(String flightNumber);

	@Query(value = "select * from flights where flightNumber like :flightNumber ", nativeQuery = true)
	List<Flight> getFlightByFlightNumber(@Param("flightNumber") String flightNumber);

	//Get all airports
	@Query(value = "select * from airports", nativeQuery = true)
	List<Airport> getAllAirports();

}
