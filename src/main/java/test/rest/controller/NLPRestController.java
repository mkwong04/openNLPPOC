package test.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.doccat.DocumentCategorizer;
import test.rest.model.RequestMessage;
import test.rest.model.ResponseMessage;

@RestController
@Slf4j
public class NLPRestController extends AbstractRESTController{
	
	@Autowired
	private DocumentCategorizer docCategorizer;

	/**
	 * 
	 * @param requestMsg
	 * @return
	 */

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
	@RequestMapping(path="/openNlp/ask",
					method=RequestMethod.POST,
					consumes={MediaType.APPLICATION_JSON_VALUE},
					produces={MediaType.APPLICATION_JSON_VALUE})
	
	@ResponseStatus
	@SuppressWarnings("rawtypes")
	public ResponseEntity chat(@RequestBody RequestMessage requestMsg){
		
		String queryMsg = requestMsg.getMessage();
		
		log.info("query message : {}", queryMsg);
		
		String[] tokens = queryMsg.split(" ");
		
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
		
		String responseMsg;
		
		if(notConclude){
			responseMsg = "Inconclusive";
		}
		else{
			responseMsg = docCategorizer.getBestCategory(prob);
		}
		
		return ok(responseMsg);
	}
}