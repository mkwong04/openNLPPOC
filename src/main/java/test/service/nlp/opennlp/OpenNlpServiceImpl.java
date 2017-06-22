package test.service.nlp.opennlp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import opennlp.tools.doccat.DocumentCategorizer;
import opennlp.tools.tokenize.Tokenizer;
import test.service.exception.NlpServiceException;
import test.service.nlp.NlpService;
import test.service.rule.RuleServiceConstant;

/**
 * OpenNLP implementation for NlpService
 * @author minkeat.wong
 *
 */
@Slf4j
public class OpenNlpServiceImpl implements NlpService {
	
	@Autowired
	private DocumentCategorizer docCategorizer;
	
	@Autowired
	private Tokenizer tokenizer;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> process(String sentence) throws NlpServiceException {
		
		Map<String, Object> nlpResultMap = new HashMap<>();
		String[] tokens = tokenizer.tokenize(sentence);

		String classification = classify(sentence);
		
		nlpResultMap.put(RuleServiceConstant.CLASSIFCATION, classification);
		
		//TODO: replaced with account name finder NLP
		for (String token : tokens) {
			if(token.startsWith("AAA")){
				nlpResultMap.put(RuleServiceConstant.TOKEN_ACCOUNT,token);
				break;
			}
		}
		
		//TODO: sentiment info
		
		return nlpResultMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String classify(String sentence) throws NlpServiceException {
		
		String[] tokens = tokenizer.tokenize(sentence);
		
		double[] prob = docCategorizer.categorize(tokens);
		log.info("Score Map : {}",docCategorizer.scoreMap(tokens));
		
		log.info("Results : {}", docCategorizer.getAllResults(prob));
		
		boolean notConclude = true;
		List<String> checkList = new ArrayList<>();
		
		//assume always at least 2
		for(int idx= 0; idx<prob.length; idx++){
			String check = String.valueOf(prob[idx]);
			
			if(checkList.isEmpty()){
				checkList.add(check);
			}
			//detected different prob, then is not undetermine case
			else if(!checkList.contains(check)){
				notConclude = false;
				break;
			}
		}
		
		String classification;
		
		if(notConclude){
			classification = "Inconclusive";
		}
		else{
			classification = docCategorizer.getBestCategory(prob);
		}
		
		return classification;
	}
	


}
