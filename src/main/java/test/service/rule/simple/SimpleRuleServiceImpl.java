package test.service.rule.simple;

import static test.service.rule.RuleServiceConstant.CLASSIFCATION;
import static test.service.rule.RuleServiceConstant.CONTEXT_TAG;
import static test.service.rule.RuleServiceConstant.RESPONSE_MSG;
import static test.service.rule.RuleServiceConstant.TOKEN_ACCOUNT;
import static test.service.rule.simple.SimpleRuleConstant.CTXT_BAL_INQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.service.exception.RuleServiceException;
import test.service.rule.RuleService;

public class SimpleRuleServiceImpl implements RuleService {

	@Override
	public Map<String, String> processRules(Map<String, Object> paramMap) throws RuleServiceException {
		
		Map<String, String> responseMap = new HashMap<>();
		
		String classification = (String)paramMap.get(CLASSIFCATION);
		String contextTags    = (String)paramMap.get(CONTEXT_TAG);
		
		
		List<String> responseCtxtList = new ArrayList<>();
		List<String> ctxtTagList = parseTags(contextTags);
		
		//#rule 1
		if(SimpleRuleConstant.BALANCE_INQUIRY.equals(classification) || ctxtTagList.contains(CTXT_BAL_INQ)){
			String account = (String)paramMap.get(TOKEN_ACCOUNT);
			//if account present
			if(account!=null){
				
				//TODO: query balance based on account
				
				//TODO: lookup response message
				responseMap.put(RESPONSE_MSG, "Your balance is $ 100.00");
			}
			//no account present
			else{
				responseCtxtList.add(CTXT_BAL_INQ);
				
				if(ctxtTagList.contains(CTXT_BAL_INQ)){
					responseMap.put(RESPONSE_MSG, "You may provide an invalid account ref, please provide a valid account ref starting with prefix 'AAA'. ");
				}
				else{
					//TODO: lookup response message
					responseMap.put(RESPONSE_MSG, "May we have your account ref?");
				}
			}
		}
		//fallback
		else{
			//TODO: lookup response message
			responseMap.put(RESPONSE_MSG, "We don't get that, can you be more precise?");
		}
		
		if(!responseCtxtList.isEmpty()){
			responseMap.put(CONTEXT_TAG, buildCtxtTags(responseCtxtList));
		}
		
		return responseMap;
	}
	
	/**
	 * 
	 * @param contextTags
	 * @return
	 */
	private List<String> parseTags(String contextTags){
		
		List<String> ctxtTagList = new ArrayList<>();
		
		if(contextTags!=null){
			String[] tags = contextTags.split(",");
			
			for(int idx=0; idx<tags.length; idx++){
				ctxtTagList.add(tags[idx].trim());
			}
		}
		
		return ctxtTagList;
	}
	
	/**
	 * 
	 * @param contextTagList
	 * @return
	 */
	private String buildCtxtTags(List<String> contextTagList){
		
		StringBuilder sb = new StringBuilder();
		
		for(String respCtxtTag : contextTagList){
			sb.append(respCtxtTag).append(",");
		}
		
		if(sb.length()>0){
			sb.setLength(sb.length()-1);
		}
		
		return sb.toString();
	}
}
