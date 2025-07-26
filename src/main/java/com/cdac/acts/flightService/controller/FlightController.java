package com.cdac.acts.flightService.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.acts.flightService.DTO.FlightDetailsDTO;
import com.cdac.acts.flightService.entity.Flight;
import com.cdac.acts.flightService.responseWrapper.ResponsePayload;
import com.cdac.acts.flightService.service.FlightServiceImpl;

@RestController
@RequestMapping("/flights")
@CrossOrigin(origins = "http://localhost:5173")
public class FlightController {

    @Autowired
    private FlightServiceImpl flightService;

    @GetMapping
    public ResponseEntity<ResponsePayload<List<FlightDetailsDTO>>> getAllFlights() {
        List<FlightDetailsDTO> flights = flightService.getAllFlights();

        ResponsePayload<List<FlightDetailsDTO>> res = new ResponsePayload<>(
            "SUCCESS",
            "Flights fetched successfully",
            flights
        );

        return ResponseEntity.ok(res);
    }

    @GetMapping("/{flightNumber}")
    public ResponseEntity<ResponsePayload<List<FlightDetailsDTO>>> getFlightById(@PathVariable String flightNumber) {
        List<FlightDetailsDTO> flights = flightService.getFlightsByFlightNumber(flightNumber);
        ResponsePayload<List<FlightDetailsDTO>> res = new ResponsePayload<>(
            "SUCCESS",
            "Flight fetched successfully",
            flights
        );

        return ResponseEntity.ok(res);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponsePayload<List<FlightDetailsDTO>>> getFlightByFilter(
        @RequestParam Long departureId,
        @RequestParam Long arrivalId,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime departureDate,
        @RequestParam int passengers
    ) {
        List<FlightDetailsDTO> flights = flightService.getFlightByOneWayFilter(departureId, arrivalId, departureDate, passengers);

        ResponsePayload<List<FlightDetailsDTO>> res = new ResponsePayload<>(
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

        ResponsePayload<Flight> res = new ResponsePayload<>(
            "SUCCESS",
            "Flight updated successfully",
            updatedFlight
        );

        return ResponseEntity.ok(res);
    }

    @DeleteMapping
    public ResponseEntity<ResponsePayload<String>> deleteFlight(@RequestParam String flightNumber) {
        flightService.deleteFlight(flightNumber);

        ResponsePayload<String> res = new ResponsePayload<>(
            "SUCCESS",
            "Flight deleted successfully",
            flightNumber
        );

        return ResponseEntity.ok(res);
    }
}
