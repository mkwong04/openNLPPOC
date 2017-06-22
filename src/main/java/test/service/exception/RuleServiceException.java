package test.service.exception;

/**
 * Rule Service exception
 * @author minkeat.wong
 *
 */
public class RuleServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public RuleServiceException(){
		super();
	}
	
	/**
	 * 
	 * @param msg
	 */
	public RuleServiceException(String msg){
		super(msg);
	}
	
	/**
	 * 
	 * @param t
	 */
	public RuleServiceException(Throwable t){
		super(t);
	}

	/**
	 * 
	 * @param msg
	 * @param t
	 */
	public RuleServiceException(String msg, Throwable t){
		super(msg, t);
	}
}
