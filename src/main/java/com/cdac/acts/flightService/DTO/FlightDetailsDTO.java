package com.cdac.acts.flightService.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FlightDetailsDTO {

    public FlightDetailsDTO(Long long1, String string, Long long2, String string2, String string3,
			LocalDateTime localDateTime, LocalDateTime localDateTime2, Integer integer, String string4,
			Integer integer2, Integer integer3, LocalDateTime localDateTime3, LocalDateTime localDateTime4) {
		// TODO Auto-generated constructor stub
	}
	private Long id;
    private String flightNumber;
    private Long airplaneId;
    private String departureAirportName;
    private String arrivalAirportName;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer price;
    private String boardingGate;
    private Integer totalSeats;
    private Integer availableSeats;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
