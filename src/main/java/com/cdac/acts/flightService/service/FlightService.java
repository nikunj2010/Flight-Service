package com.cdac.acts.flightService.service;

import java.time.LocalDateTime;

import java.util.List;

import com.cdac.acts.flightService.entity.Airport;
import com.cdac.acts.flightService.entity.Flight;

public interface FlightService {

	List<Flight> getAllFlights();
	
	List<Flight> getFlightsByFlightNumber(String FlightNumber);
	
	Flight createFlight(Flight flight);
	
	void deleteFlight(String flightNumber);
	
	Flight updateFlight(Flight flight);
	
	List<Flight> getFlightByOneWayFilter(Long departureAirportId, Long arrivalAirportId, LocalDateTime date, int passengers);
	
	List<Airport> getAllAirports();
}
