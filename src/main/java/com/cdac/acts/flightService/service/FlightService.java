package com.cdac.acts.flightService.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.cdac.acts.flightService.entity.Flight;

public interface FlightService {

	List<Flight> getAllFlights();
	
	Flight getFlightsById(Long id);
	
	Flight createFlight(Flight flight);
	
	void deleteFlight(String flightNumber);
	
	Flight updateFlight(Flight flight);
	
	List<Flight> getFlightByOneWayFilter(Long departureAirportId, Long arrivalAirportId, LocalDateTime date, int passengers);
	
	List<Flight> getFlightByRoundTripFilter(int departureAirportId, int arrivalAirportId, LocalDateTime departureDate, LocalDateTime arrivalDate, int passengers);
}
