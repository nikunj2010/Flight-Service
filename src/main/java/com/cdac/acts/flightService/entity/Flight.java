package com.cdac.acts.flightService.entity;

import java.time.LocalDateTime;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "flights")
public class Flight {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 	private Long id;

	    @Column(name = "flightNumber", nullable = false, unique = true)
	    private String flightNumber;

	    @Column(name = "airplaneId", nullable = false)
	    private Long airplaneId;

	    @Column(name = "departureAirportId", nullable = false)
	    private Long departureAirportId;

	    @Column(name = "arrivalAirportId", nullable = false)
	    private Long arrivalAirportId;

	    @Column(name = "departureTime", nullable = false)
	    private LocalDateTime departureTime;

	    @Column(name = "arrivalTime", nullable = false)
	    private LocalDateTime arrivalTime;

	    @Column(name = "price", nullable = false)
	    private Integer price;

	    @Column(name = "boardingGate")
	    private String boardingGate;

	    @Column(name = "totalSeats", nullable = false)
	    private Integer totalSeats;

	    @Column(name = "created_at")
	    private LocalDateTime createdAt;

	    @Column(name = "updated_at")
	    private LocalDateTime updatedAt;
	    
	    @Column(name = "availableSeats")
	    int availableSeats;
}
