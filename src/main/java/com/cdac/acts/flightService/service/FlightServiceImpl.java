package com.cdac.acts.flightService.service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.acts.flightService.entity.Flight;
import com.cdac.acts.flightService.exceptions.CreateFlightException;
import com.cdac.acts.flightService.exceptions.DeleteFlightException;
import com.cdac.acts.flightService.exceptions.FlightNotFoundException;
import com.cdac.acts.flightService.repository.FlightRepository;


@Service
public class FlightServiceImpl implements FlightService{

	@Autowired
	FlightRepository flightRepository;
	
	@Override
	public List<Flight> getAllFlights() {
		// TODO Auto-generated method stub
		
		List<Flight> flights = flightRepository.findAll();
		
		return flights;
	}

	@Override
	public Flight getFlightsById(Long id){
		// TODO Auto-generated method stub
		
		 return flightRepository.findById(id)
			        .orElseThrow(() -> new FlightNotFoundException("Flight with ID " + id + " not found"));

	}
	
	@Override
	public List<Flight> getFlightByOneWayFilter(Long departureAirportId, Long arrivalAirportId, LocalDateTime date, int passengers) {
		
		return flightRepository.findFlightsForOneWay(departureAirportId, arrivalAirportId, date, passengers)
		        .orElseThrow(() -> new FlightNotFoundException("Flight with not found"));
	}
	
	@Override
	public List<Flight> getFlightByRoundTripFilter(int departureAirportId, int arrivalAirportId,
			LocalDateTime departureDate, LocalDateTime arrivalDate, int passengers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flight createFlight(Flight flight) {
		// TODO Auto-generated method stub
		if(flightRepository.existsByFlightNumber(flight.getFlightNumber())){
			throw new CreateFlightException("Flight with flight number - " +flight.getFlightNumber() +" already exists");
		}
		try {
			flightRepository.saveAndFlush(flight);
			return flight;
		}
		catch(CreateFlightException e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public void deleteFlight(String flightNumber) {
		// TODO Auto-generated method stub
		if(!flightRepository.existsByFlightNumber(flightNumber)) {
			throw new FlightNotFoundException("Flight with number " + flightNumber + " not found");
		}
		else if(flightRepository.bookingExistsByFlightNumber(flightNumber) == 1) {
			throw new DeleteFlightException("Cannot delete flight " + flightNumber + " as bookings exists");
		}
		try {
			flightRepository.deleteByFlightNumber(flightNumber);
		}
		catch(DeleteFlightException e) {
			throw e;
		}
		
	}

	@Override
	@Transactional
	public Flight updateFlight(Flight flight) {
		Flight existingFlight = flightRepository.findById(flight.getId())
	            .orElseThrow(() -> new FlightNotFoundException("Flight with ID " + flight.getId() + " not found"));

	    existingFlight.setFlightNumber(flight.getFlightNumber());
	    existingFlight.setAirplaneId(flight.getAirplaneId());
	    existingFlight.setDepartureAirportId(flight.getDepartureAirportId());
	    existingFlight.setArrivalAirportId(flight.getArrivalAirportId());
	    existingFlight.setDepartureTime(flight.getDepartureTime());
	    existingFlight.setArrivalTime(flight.getArrivalTime());
	    existingFlight.setPrice(flight.getPrice());
	    existingFlight.setBoardingGate(flight.getBoardingGate());
	    existingFlight.setTotalSeats(flight.getTotalSeats());
	    existingFlight.setAvailableSeats(flight.getAvailableSeats());
	    existingFlight.setUpdatedAt(LocalDateTime.now());

	    return flightRepository.save(existingFlight);		
	}


}
