package com.scotiabank.copilot.crud.exception;


import com.scotiabank.copilot.crud.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                "Resource Not Found"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException ex) {
        // Creamos un ErrorResponse con el código de estado 409
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),  // Código de estado HTTP 409
                ex.getMessage(),              // Mensaje de error
                "Conflict"                   // Tipo de error
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}
