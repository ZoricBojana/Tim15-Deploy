package rs.travel.bookingWithEase.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

import javax.persistence.OptimisticLockException;

import rs.travel.bookingWithEase.model.ApiError;
import rs.travel.booking_with_ease.exceptions.*;

// https://www.toptal.com/java/spring-boot-rest-api-error-handling
// https://grokonez.com/spring-framework/spring-mvc/use-restcontrolleradvice-new-features-spring-framework-4-3
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
    @ExceptionHandler(EntityAlreadyExistsException.class)
    protected ResponseEntity<Object> handleEntityAlreadyExists(
    		EntityAlreadyExistsException ex) {
       
        ApiError apiError = new ApiError(CONFLICT);
        apiError.setMessage(ex.getMessage());
    
        return buildResponseEntity(apiError);
    }
    
    @ExceptionHandler(EntityNotEditableException.class)
    protected ResponseEntity<Object> handleEntityAlreadyExists(
    		EntityNotEditableException ex) {
       
        ApiError apiError = new ApiError(METHOD_NOT_ALLOWED);
        apiError.setMessage(ex.getMessage());
    
        return buildResponseEntity(apiError);
    }
    
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleMyException(
    		MyException ex) {
       
        ApiError apiError = new ApiError(METHOD_NOT_ALLOWED);
        apiError.setMessage(ex.getMessage());
    
        return buildResponseEntity(apiError);
    }
  

    @ExceptionHandler(OptimisticLockException.class)
    protected ResponseEntity<Object> handleOptimisticLockException(
    		OptimisticLockException ex) {
       
        ApiError apiError = new ApiError(CONFLICT);
        apiError.setMessage("Optimistic lock exception happend");
    
        return buildResponseEntity(apiError);
    }
  
  
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}