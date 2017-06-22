package test.service.nlp;

import java.util.Map;

import test.service.exception.NlpServiceException;

/**
 * Nlp Service interface
 * 
 * @author minkeat.wong
 *
 */
public interface NlpService {

	/**
	 * 
	 * @param sentence
	 * @return
	 * @throws NlpServiceException
	 */
	String classify(String sentence) throws NlpServiceException;
	
	/**
	 * 
	 * @param sentence
	 * @return
	 * @throws NlpServiceException
	 */
	Map<String, Object> process(String sentence) throws NlpServiceException;
}
