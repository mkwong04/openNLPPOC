package test.rest.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Message model 
 * 
 * @author Minkeat.Wong
 *
 */
@Data
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;

}
