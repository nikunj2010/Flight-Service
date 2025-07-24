package com.cdac.acts.flightService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cdac.acts.flightService.responseWrapper.ResponsePayload;

@RestControllerAdvice
public class FlightExceptionHandler {

    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<ResponsePayload<Object>> handleResourceNotFound(FlightNotFoundException ex) {
        ResponsePayload<Object> res = new ResponsePayload<>(
            "FAILURE",
            ex.getMessage(),
            null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(CreateFlightException.class)
    public ResponseEntity<ResponsePayload<Object>> handleResourceCreate(CreateFlightException ex) {
        ResponsePayload<Object> res = new ResponsePayload<>(
            "FAILURE",
            ex.getMessage(),
            null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(DeleteFlightException.class)
    public ResponseEntity<ResponsePayload<Object>> handleResourceDelete(DeleteFlightException ex) {
        ResponsePayload<Object> res = new ResponsePayload<>(
            "FAILURE",
            ex.getMessage(),
            null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(UpdateFlightException.class)
    public ResponseEntity<ResponsePayload<Object>> handleResourceUpdate(UpdateFlightException ex) {
        ResponsePayload<Object> res = new ResponsePayload<>(
            "FAILURE",
            ex.getMessage(),
            null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponsePayload<Object>> handleOtherExceptions(Exception ex) {
        ResponsePayload<Object> res = new ResponsePayload<>(
            "FAILURE",
            "Unexpected error: " + ex.getMessage(),
            null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }
}
