package com.cdac.acts.flightService.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.cdac.acts.flightService.entity.Airplane;
import com.cdac.acts.flightService.entity.Airport;
import com.cdac.acts.flightService.entity.Flight;
import com.cdac.acts.flightService.exceptions.CreateFlightException;
import com.cdac.acts.flightService.exceptions.FlightNotFoundException;
import com.cdac.acts.flightService.repository.FlightRepository;

@Service
public class FlightServiceImpl implements FlightService {

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<Flight> getAllFlights() {

		return flightRepository.findAll();
	}

	@Override
	public List<Flight> getFlightsByFlightNumber(String flightNumber) {

		List<Flight> flights = flightRepository.getFlightByFlightNumber(flightNumber + "%");
		if(flights.size() == 0) {
			throw new FlightNotFoundException("flight with flight number " + flightNumber + " not found");
		}
		return flights;

	}


	@Override
	public List<Flight> getFlightByOneWayFilter(Long departureAirportId, Long arrivalAirportId, LocalDateTime date, int passengers) {
		System.out.println(departureAirportId + ", " + arrivalAirportId + ", " + date + ", " + passengers);
		List<Flight> flights =  flightRepository.findFlightsForOneWay(departureAirportId, arrivalAirportId, date, passengers);
		System.out.println("flight are");
		flights.forEach(System.out::println);
//		if(flights.size() == 0) {
//			throw new FlightNotFoundException("No flights found");
//		}        

		return flights;
	}

	@Override
	public Flight createFlight(Flight flight) {
		if (flightRepository.existsByFlightNumberAndIsCancelledFalse(flight.getFlightNumber())) {
			throw new CreateFlightException("Flight with flight number - " + flight.getFlightNumber() + " already exists");
		}

		try {
			return flightRepository.saveAndFlush(flight);
		} catch (Exception e) {
			throw new CreateFlightException("Failed to create flight: " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public boolean deleteFlight(Long flightId) {
		String bookingServiceUrl = "http://localhost:8081/bookings";
		System.out.println("id in controller = " + flightId);
		flightRepository.deleteById(flightId);
		ResponseEntity<Boolean> response = restTemplate.exchange(
				bookingServiceUrl + "/" + flightId,
				HttpMethod.PUT,
				null,  // or new HttpEntity<>(requestBody)
				Boolean.class
				);

		Boolean bookingsPresent = response.getBody();

		return bookingsPresent;
	}

	@Override
	@Transactional
	public Flight updateFlight(Flight flight) {

		Flight existingFlight = flightRepository.findById(flight.getId())
				.orElseThrow(() -> new FlightNotFoundException("Flight with ID " + flight.getId() + " not found"));

		Flight updatedFlight = flightRepository.save(flight);
		return flight;
	}


	@Override
	public boolean cancelFlightByAirportId(Long id) {

		String bookingServiceUrl = "http://localhost:8081/bookings";
		List<Long> flightIds = flightRepository.findFlightIdsByAirportId(id);
		if(flightIds.size() != 0) {
			ResponseEntity<Boolean> response = null;
			for(Long flightId : flightIds){
				response = restTemplate.exchange(
						bookingServiceUrl + "/" + flightId,
						HttpMethod.PUT,
						null,
						Boolean.class
						);
				System.out.println("outside if");
				if (response.getStatusCode() == HttpStatus.OK && Boolean.TRUE.equals(response.getBody())) {
					System.out.println("inside if");
					flightRepository.cancelFlightsByAirportId(id);
				}
				else {
					return false;
				}
			}
		}
		return true;
	}
	
	
	@Override
	public List<Airport> getAllAirports() {
		return flightRepository.getAllAirports();
	}
	
	@Override
	public List<Airplane> getAllAirplanes() {
		return flightRepository.getAllAirplanes();
	}
}
