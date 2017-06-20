package test.rest.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequestMessage {

	@ApiModelProperty(value="request sentence", required=true)
	private String message;
}
