package test.app.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizer;
import opennlp.tools.doccat.DocumentCategorizerME;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring bean application configuration
 * @author Minkeat.Wong
 *
 */
//enable swagger 2 feature
@EnableSwagger2
@Slf4j
@Configuration
public class TestAppConfig {
	
	/**
	 * load serialized trained model + initialize maxent implementation doc categorizer
	 * @return
	 * @throws IOException
	 */
	@Bean
	public DocumentCategorizer docCategorizer() throws IOException{
		
		try(InputStream is = TestAppConfig.class.getResourceAsStream("/trainedModel")){
			log.info("initializing Doc cat model from serialized : {}",is);
			DoccatModel model = new DoccatModel(is);
			
			DocumentCategorizer docCategorizer = new DocumentCategorizerME(model);
			log.info("Maxent implementation of doc Categorizer initialized");
			return docCategorizer;
		}
	}
	
	/** Swagger 2 UI docket **/
	@Bean
	public Docket swaggerUI(){
		log.info("Initializing Swagger 2 UI docket...");
		
		return new Docket(DocumentationType.SWAGGER_2).select()
													  .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
													  .build()
													  .apiInfo(new ApiInfoBuilder().title("Open NLP PoC API")
															                       .description("PoC API")
															                       .build())
													  //don't generate with default response code
													  .useDefaultResponseMessages(false);
	}
	

}
