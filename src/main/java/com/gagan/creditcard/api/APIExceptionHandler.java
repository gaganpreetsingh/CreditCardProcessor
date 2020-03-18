package com.gagan.creditcard.api;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gagan.creditcard.api.response.ApiResponse;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		FieldError fieldError = ex.getBindingResult().getFieldError();
		ApiResponse apiResp = new ApiResponse();
		apiResp.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
		apiResp.setData(fieldError.getDefaultMessage());
		return ResponseEntity.badRequest().body(apiResp);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<Object> handleConstraintViolationException(Exception ex, WebRequest request) {

		ApiResponse apiResp = new ApiResponse();
		apiResp.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
		apiResp.setData(ex.getMessage());
		return ResponseEntity.badRequest().body(apiResp);
	}
}