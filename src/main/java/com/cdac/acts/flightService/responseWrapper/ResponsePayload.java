package com.cdac.acts.flightService.responseWrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class ResponsePayload<T> {

	private String status;
	private String message;
	private T data;
}
