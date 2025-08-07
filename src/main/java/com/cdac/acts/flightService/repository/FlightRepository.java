package com.cdac.acts.flightService.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdac.acts.flightService.entity.Airplane;
import com.cdac.acts.flightService.entity.Airport;
import com.cdac.acts.flightService.entity.Flight;

import jakarta.transaction.Transactional;

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


	//Delete flight by id
	@Modifying
	@Query(value = "update flights set isCancelled = true where id = :flightId ", nativeQuery = true)
	void deleteById(Long flightId);

	//Search flights by flight number
	@Query(value = "select * from flights where flightNumber like :flightNumber ", nativeQuery = true)
	List<Flight> getFlightByFlightNumber(@Param("flightNumber") String flightNumber);


	//Delete flight by airport id
	@Modifying
	@Transactional
	@Query("UPDATE Flight f SET f.isCancelled = true WHERE f.departureAirport.id = :airportId OR f.arrivalAirport.id = :airportId")
	int cancelFlightsByAirportId(@Param("airportId") Long airportId);

	@Query("SELECT f.id FROM Flight f WHERE f.departureAirport.id = :airportId OR f.arrivalAirport.id = :airportId")
	List<Long> findFlightIdsByAirportId(@Param("airportId") Long airportId);

	//Get all airports
	@Query(value = "select * from airports", nativeQuery = true)
	List<Airport> getAllAirports();

	//Get all airplanes
	@Query(value = "select * from airplanes", nativeQuery = true)
	List<Airplane> getAllAirplanes();
}
