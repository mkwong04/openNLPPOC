package test.rest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import test.rest.model.RequestMessage;
import test.rest.model.ResponseMessage;
import test.service.exception.NlpServiceException;
import test.service.exception.RuleServiceException;
import test.service.nlp.NlpService;
import test.service.rule.RuleService;
import test.service.rule.RuleServiceConstant;

@RestController
@Slf4j
public class NLPRestController extends AbstractRESTController{
	
	@Autowired
	private NlpService nlpService;
	
	@Autowired
	private RuleService ruleService;

	/**
	 * 
	 * @param requestMsg
	 * @return
	 */

	@CrossOrigin(origins={"*"},
			     methods={RequestMethod.POST},
			     allowedHeaders={"origin","content-type","accept","authorization"})
	// Swagger UI
	@ApiOperation(value="chat classification",
				  notes="chat classification endpoint",
				  consumes= MediaType.APPLICATION_JSON_VALUE,
				  produces= MediaType.APPLICATION_JSON_VALUE,
				  tags={"chat"})
	@ApiResponses(value={
					@ApiResponse(code=200, message="ok", response=ResponseMessage.class),
					@ApiResponse(code=500, message="Unexpected internal error", response=ResponseMessage.class)
				})
	//REST mapping
	@RequestMapping(path="/openNlp/classify",
					method=RequestMethod.POST,
					consumes={MediaType.APPLICATION_JSON_VALUE},
					produces={MediaType.APPLICATION_JSON_VALUE})
	
	@ResponseStatus
	@SuppressWarnings("rawtypes")
	public ResponseEntity classify(@RequestBody RequestMessage requestMsg){
		
		String queryMsg = requestMsg.getMessage();
		log.info("query message : {}", queryMsg);

		try {
			String classification = nlpService.classify(queryMsg);
			log.info("classification : {}",classification);

			return ok(classification);
		} 
		catch (NlpServiceException e) {
			log.error("classification error ",e);
			return internalServerError(e.getMessage());
		}
		
	}
	
	@CrossOrigin(origins={"*"},
		     methods={RequestMethod.POST},
		     allowedHeaders={"origin","content-type","accept","authorization"})
	// Swagger UI
	@ApiOperation(value="chat ask",
				  notes="chat ask endpoint",
				  consumes= MediaType.APPLICATION_JSON_VALUE,
				  produces= MediaType.APPLICATION_JSON_VALUE,
				  tags={"chat"})
	@ApiResponses(value={
					@ApiResponse(code=200, message="ok", response=ResponseMessage.class),
					@ApiResponse(code=500, message="Unexpected internal error", response=ResponseMessage.class)
				})
	//REST mapping
	@RequestMapping(path="/openNlp/chat",
					method=RequestMethod.POST,
					consumes={MediaType.APPLICATION_JSON_VALUE},
					produces={MediaType.APPLICATION_JSON_VALUE})
	
	@ResponseStatus
	@SuppressWarnings("rawtypes")
	public ResponseEntity chat(@RequestParam(required=false) String contexts,
							   @RequestBody RequestMessage requestMsg){
		
		try {
			//1. perform nlp to extract the info
			Map<String, Object> nplResultMap = nlpService.process(requestMsg.getMessage());
			
			//1.a if context present, add to rule param
			if(contexts!=null){
				nplResultMap.put(RuleServiceConstant.CONTEXT_TAG, contexts);
			}
			
			//2. fire the rule(s) and construct the response
			Map<String, String> responseMap = ruleService.processRules(nplResultMap);
			
			//3. format the response
			ResponseMessage responseMsg = new ResponseMessage();
			
			responseMsg.setMessage(responseMap.get(RuleServiceConstant.RESPONSE_MSG));
			responseMsg.setContextTags(responseMap.get(RuleServiceConstant.CONTEXT_TAG));
			
			return ok(responseMsg);
		} 
		catch (NlpServiceException | RuleServiceException e) {
			log.error("chat error ",e);
			return internalServerError(e.getMessage());
		}
		
	}
}