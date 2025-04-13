package br.com.ivogoncalves.ms_customer.infra.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.ivogoncalves.ms_customer.infra.exceptions.AttributeValidationException;
import br.com.ivogoncalves.ms_customer.infra.exceptions.ExceptionResponse;
import br.com.ivogoncalves.ms_customer.infra.exceptions.ResourceNotFoundException;

/**
 * CustomizedExceptionHandler
 * 
 * @author ivogoncalves
 * @since 2023-10-01
 */

@ControllerAdvice
@RestController
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exception = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(true));
		return new ResponseEntity<ExceptionResponse>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleNotFoundException(Exception ex, WebRequest request) {
		ExceptionResponse exception = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(true));
		return new ResponseEntity<ExceptionResponse>(exception,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AttributeValidationException.class)
	public ResponseEntity<ExceptionResponse> handleAttributeValidationException(Exception ex, WebRequest request) {
		ExceptionResponse exception = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(true));
		return new ResponseEntity<ExceptionResponse>(exception,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
	public ResponseEntity<ExceptionResponse> handleConstraintViolationException(
	        jakarta.validation.ConstraintViolationException ex, WebRequest request) {

	    StringBuilder sb = new StringBuilder("Validation error: ");
	    ex.getConstraintViolations().forEach(violation -> {
	        sb.append(violation.getPropertyPath()).append(": ");
	        sb.append(violation.getMessage()).append("; ");
	    });

	    ExceptionResponse exception = new ExceptionResponse(new Date(), sb.toString(), request.getDescription(true));
	    return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
	}
}
