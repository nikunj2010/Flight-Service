package com.cdac.acts.flightService.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.cdac.acts.flightService.DTO.FlightDetailsDTO;
import com.cdac.acts.flightService.entity.Flight;

public interface FlightService {

	List<FlightDetailsDTO> getAllFlights();
	
	List<FlightDetailsDTO> getFlightsByFlightNumber(String FlightNumber);
	
	Flight createFlight(Flight flight);
	
	void deleteFlight(String flightNumber);
	
	Flight updateFlight(Flight flight);
	
	List<FlightDetailsDTO> getFlightByOneWayFilter(Long departureAirportId, Long arrivalAirportId, LocalDateTime date, int passengers);
	
}
