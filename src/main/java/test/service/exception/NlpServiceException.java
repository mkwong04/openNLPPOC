package test.service.exception;

/**
 * Natural Language Processing Service exception
 * @author minkeat.wong
 *
 */
public class NlpServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public NlpServiceException(){
		super();
	}
	
	/**
	 * 
	 * @param msg
	 */
	public NlpServiceException(String msg){
		super(msg);
	}
	
	/**
	 * 
	 * @param t
	 */
	public NlpServiceException(Throwable t){
		super(t);
	}

	/**
	 * 
	 * @param msg
	 * @param t
	 */
	public NlpServiceException(String msg, Throwable t){
		super(msg, t);
	}
}
