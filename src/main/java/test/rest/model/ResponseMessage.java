package test.rest.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResponseMessage {

	@ApiModelProperty(value="response message", required=true)
	private String message;
	
	@ApiModelProperty(value="comma separated context(s) tag")
	private String contextTags;
}
