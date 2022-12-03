package com.mungwin.payngwinteller.security.exception;

import com.mungwin.payngwinteller.domain.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApplicationExceptionHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(value = { ApiException.class })
    public ResponseEntity<ApiResponse<ApiException>> handleApiException(ApiException ex, HttpServletRequest request) {
        logger.warn("Handling Error: {}", ex.getCode());
        ApiResponse<ApiException> response = new ApiResponse<>();
        response.setErrors(new ArrayList<>());
        response.getErrors().add(ex);
        return new ResponseEntity<>(response, ex.getStatus());
    }
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<ApiResponse<ValidationErrorResponse>> handleValidationErrors(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        logger.warn("Handling Error: {}", ex.getMessage());
        List<FieldError> fieldErrorList = ex.getBindingResult().getFieldErrors();
        ApiResponse<ValidationErrorResponse> apiResponse = new ApiResponse<>();
        apiResponse.setErrors(new ArrayList<>());
        for (FieldError fieldError: fieldErrorList) {
            ValidationErrorResponse errorResponse = new ValidationErrorResponse();
            errorResponse.setMessage(fieldError.getDefaultMessage());
            errorResponse.setField(fieldError.getField());
            apiResponse.getErrors().add(errorResponse);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ApiResponse<ErrorResponse>> handleRuntimeException(Exception ex, HttpServletRequest request) {
        logger.warn("Handling Error: {}", ex.getMessage());
        ApiResponse<ErrorResponse> response = new ApiResponse<>();
        response.setErrors(new ArrayList<>());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setCode("UNDOCUMENTED");
        response.getErrors().add(errorResponse);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
