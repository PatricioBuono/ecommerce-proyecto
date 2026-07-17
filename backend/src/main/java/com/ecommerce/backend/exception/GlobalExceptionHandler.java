package com.ecommerce.backend.exception;

import com.ecommerce.backend.dto.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(ValidationException ex){

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getCampo(), ex.getMessage());

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
