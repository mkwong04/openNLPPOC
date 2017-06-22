package test.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;

import test.rest.model.ResponseMessage;

public abstract class AbstractRESTController {
	
	/**
	 * 
	 * @param message
	 * @return
	 */
	protected ResponseEntity<ResponseMessage> ok(String message){
		
		ResponseMessage body = new ResponseMessage();
		
		body.setMessage(message);
		return ok(body);
	}
	
	/**
	 * 
	 * @param body
	 * @return
	 */
	protected <T>ResponseEntity<T> ok(T body){
		return buildResponseEntity(body, HttpStatus.OK);
	}

	
	/**
	 * 
	 * @param message
	 * @return
	 */
	protected ResponseEntity<ResponseMessage> internalServerError(String message){
		
		ResponseMessage body = new ResponseMessage();
		
		body.setMessage(message);
		return internalServerError(body);
	}
	
	/**
	 * 
	 * @param body
	 * @return
	 */
	protected <T>ResponseEntity<T> internalServerError(T body){
		return buildResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * 
	 * @param body
	 * @param status
	 * @return
	 */
	private <T>ResponseEntity<T> buildResponseEntity(T body, HttpStatus status){
		BodyBuilder builder = ResponseEntity.status(status);
		
		return builder.body(body);
	}
}
