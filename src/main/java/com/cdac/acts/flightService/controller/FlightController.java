package com.cdac.acts.flightService.controller;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.acts.flightService.entity.Airplane;
import com.cdac.acts.flightService.entity.Airport;
import com.cdac.acts.flightService.entity.Flight;
import com.cdac.acts.flightService.responseWrapper.ResponsePayload;
import com.cdac.acts.flightService.service.FlightServiceImpl;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/flights")
@CrossOrigin(origins = "*")

public class FlightController {

	@Autowired
	private FlightServiceImpl flightService;

	@GetMapping
	public ResponseEntity<ResponsePayload<List<Flight>>> getAllFlights() {
		List<Flight> flights = flightService.getAllFlights();

		ResponsePayload<List<Flight>> res = new ResponsePayload<>(
				"SUCCESS",
				"Flights fetched successfully",
				flights
				);

		return ResponseEntity.ok(res);
	}

	@GetMapping("/{flightNumber}")
	public ResponseEntity<ResponsePayload<List<Flight>>> getFlightById(@PathVariable String flightNumber) {
		List<Flight> flights = flightService.getFlightsByFlightNumber(flightNumber);
		ResponsePayload<List<Flight>> res = new ResponsePayload<>(
				"SUCCESS",
				"Flight fetched successfully",
				flights
				);

		return ResponseEntity.ok(res);
	}

	@GetMapping("/search")
	public ResponseEntity<ResponsePayload<List<Flight>>> getFlightByFilter(
			@RequestParam Long departureId,
			@RequestParam Long arrivalId,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime departureDate,
			@RequestParam int passengers
			) {
		List<Flight> flights = flightService.getFlightByOneWayFilter(departureId, arrivalId, departureDate, passengers);

		ResponsePayload<List<Flight>> res = new ResponsePayload<>(
				"SUCCESS",
				"Matching flights retrieved successfully",
				flights
				);

		return ResponseEntity.ok(res);
	}

	@PostMapping
	public ResponseEntity<ResponsePayload<Flight>> createFlight(@RequestBody Flight flight) {
		Flight createdFlight = flightService.createFlight(flight);

		ResponsePayload<Flight> res = new ResponsePayload<>(
				"SUCCESS",
				"Flight created successfully",
				createdFlight
				);

		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<ResponsePayload<Flight>> updateFlight(@RequestBody Flight flight) {
		Flight updatedFlight = flightService.updateFlight(flight);
		System.out.println("update called!!!");
		ResponsePayload<Flight> res = new ResponsePayload<>(
				"SUCCESS",
				"Flight updated successfully",
				updatedFlight
				);

		return ResponseEntity.ok(res);
	}

	@Transactional
	@DeleteMapping
	public ResponseEntity<ResponsePayload<Long>> deleteFlight(@RequestParam Long flightId) {
		System.out.println("id in controller = " + flightId);
		flightService.deleteFlight(flightId);

		ResponsePayload<Long> res = new ResponsePayload<>(
				"SUCCESS",
				"Flight deleted successfully",
				flightId
				);

		return ResponseEntity.ok(res);
	}
	

	@PutMapping("/{id}")
	public ResponseEntity<ResponsePayload<?>> cancelFlightAsAirportDeleted(@PathVariable Long id) {
		boolean result = flightService.cancelFlightByAirportId(id);
		System.out.println("delete called!!!");
		if(result) {
			ResponsePayload<Boolean> res = new ResponsePayload<>(
					"SUCCESS",
					"Flight cancelled successfully",
					true
					);
			return ResponseEntity.ok(res);
		}
		else  {
			ResponsePayload<Boolean> res = new ResponsePayload<>(
					"FAILED",
					"Flight deleteion fail",
					false
					);
			return ResponseEntity.ok(res);
		}
	}
	
	/**Extra controllers
	 * get all airports
	 * get all airplanes**/
	
	@GetMapping("/airports")
	public ResponseEntity<ResponsePayload<List<Airport>>> getAllAirports(){
		List<Airport> airports = flightService.getAllAirports();

		ResponsePayload<List<Airport>> res = new ResponsePayload<>(
				"SUCCESS",
				"Airports fetched successfully",
				airports
				);

		return ResponseEntity.ok(res);
	}
	
	@GetMapping("/airplanes")
	public ResponseEntity<ResponsePayload<List<Airplane>>> getAllAirplanes(){
		List<Airplane> airplanes = flightService.getAllAirplanes();

		ResponsePayload<List<Airplane>> res = new ResponsePayload<>(
				"SUCCESS",
				"Airplanes fetched successfully",
				airplanes
				);

		return ResponseEntity.ok(res);
	}
}
