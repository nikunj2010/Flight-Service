package com.cdac.acts.flightService.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.acts.flightService.entity.Airport;
import com.cdac.acts.flightService.entity.Flight;
import com.cdac.acts.flightService.exceptions.CreateFlightException;
import com.cdac.acts.flightService.exceptions.DeleteFlightException;
import com.cdac.acts.flightService.exceptions.FlightNotFoundException;
import com.cdac.acts.flightService.repository.FlightRepository;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    FlightRepository flightRepository;


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
        if(flights.size() == 0) {
        	throw new FlightNotFoundException("No flights found");
        }        

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
    public void deleteFlight(String flightNumber) {
        if (!flightRepository.existsByFlightNumberAndIsCancelledFalse(flightNumber)) {
            throw new FlightNotFoundException("Flight with number " + flightNumber + " not found");
        } else if (flightRepository.bookingExistsByFlightNumber(flightNumber) == 1) {
            throw new DeleteFlightException("Cannot delete flight " + flightNumber + " as bookings exist");
        }

        try {
            flightRepository.deleteByFlightNumber(flightNumber);
        } catch (Exception e) {
            throw new DeleteFlightException("Failed to delete flight: " + e.getMessage());
        }
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
	public List<Airport> getAllAirports() {
		return flightRepository.getAllAirports();
	}

	

    
}
