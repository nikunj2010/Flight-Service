package com.cdac.acts.flightService.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdac.acts.flightService.DTO.FlightDetailsDTO;
import com.cdac.acts.flightService.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>{

	// Query to filter flights based on one-way search criteria
	@Query(
		    value = """
		        SELECT 
		            f.id,
		            f.flightnumber,
		            f.airplaneid,
		            da.name AS departureAirportName,
		            aa.name AS arrivalAirportName,
		            f.departuretime,
		            f.arrivaltime,
		            f.price,
		            f.boardinggate,
		            f.totalseats,
		            f.availableseats,
		            f.created_at,
		            f.updated_at
		        FROM flights f
		        JOIN airports da ON f.departureairportid = da.id
		        JOIN airports aa ON f.arrivalairportid = aa.id
		        WHERE f.departureairportid = :departureId
		        AND f.arrivalairportid = :arrivalId
		        AND f.departuretime > :departureDate
		        AND f.availableseats >= :passengers
		        """,
		    nativeQuery = true
		)
		Optional<List<Object[]>> findFlightsForOneWay(
		    @Param("departureId") Long departureId,
		    @Param("arrivalId") Long arrivalId,
		    @Param("departureDate") LocalDateTime departureDate,
		    @Param("passengers") int passengers
		);



	// Check if a flight with the given flight number exists
	boolean existsByFlightNumber(String flightNumber);

	// Check if bookings exist for a given flightNumber
	@Query(
			value = "SELECT EXISTS (SELECT 1 FROM bookings WHERE flightId = (SELECT id FROM flights WHERE flightNumber = :flightNumber))",
			nativeQuery = true
			)
	Long bookingExistsByFlightNumber(@Param("flightNumber") String flightNumber);

	// Delete flight by flight number
	void deleteByFlightNumber(String flightNumber);

	// Get all flights with joined airport names
	@Query(
			value = """
					    SELECT 
					        f.id,
					        f.flightnumber,
					        f.airplaneid,
					        da.name AS departureAirportName,
					        aa.name AS arrivalAirportName,
					        f.departuretime,
					        f.arrivaltime,
					        f.price,
					        f.boardinggate,
					        f.totalseats,
					        f.availableseats,
					        f.created_at,
					        f.updated_at
					    FROM flights f
					    JOIN airports da ON f.arrivalairportid = da.id
					    JOIN airports aa ON f.arrivalairportid = aa.id
					""",
					nativeQuery = true
			)
	List<Object[]> getRawFlightDetails();
	
	@Query(
		    value = """
		        SELECT 
		            f.id,
		            f.flightnumber,
		            f.airplaneid,
		            da.name AS departureAirportName,
		            aa.name AS arrivalAirportName,
		            f.departuretime,
		            f.arrivaltime,
		            f.price,
		            f.boardinggate,
		            f.totalseats,
		            f.availableseats,
		            f.created_at,
		            f.updated_at
		        FROM flights f
		        JOIN airports da ON f.departureairportid = da.id
		        JOIN airports aa ON f.arrivalairportid = aa.id
		        WHERE f.flightnumber like :flightNumber
		        """,
		    nativeQuery = true
		)
		List<Object[]> getRawFlightDetailsByFlightNumber(@Param("flightNumber") String flightNumber);




}
