package com.challenge.powerledger.validation;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.*;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice()
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is required";
		return new ResponseEntity<Object>(new ErrorResponse<>(Arrays.asList(error)), HttpStatus.BAD_REQUEST);
	}
 
	/**
	 * Exception thrown when {@link org.springframework.validation.annotation.Validated} is used in controller.
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<?> handle(ConstraintViolationException ex, HttpServletRequest request) throws Exception {
		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		String errorMessage = "";
	    if (!violations.isEmpty()) {
	        StringBuilder builder = new StringBuilder();
	        violations.forEach(violation -> builder.append(" " + violation.getMessage()));
	        errorMessage = builder.toString();
	    } else {
	        errorMessage = "ConstraintViolationException occured.";
	    }
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
   }
}
