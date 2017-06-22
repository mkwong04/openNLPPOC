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
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import test.service.nlp.NlpService;
import test.service.nlp.opennlp.OpenNlpServiceImpl;
import test.service.rule.RuleService;
import test.service.rule.simple.SimpleRuleServiceImpl;

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
	 * load serialized standard tokenizer model 
	 * @return
	 * @throws IOException
	 */
	@Bean
	public Tokenizer tokenizer() throws IOException{
		
		try(InputStream tkis = TestAppConfig.class.getResourceAsStream("/en-token.bin")){
			log.info("initializing tokenizer model from serialized : {}",tkis);
			
			TokenizerModel tm = new TokenizerModel(tkis);
			TokenizerME tokenizer = new TokenizerME(tm);
			
			log.info("Max entropy implementation of tokenizer initialized");
			
			return tokenizer;
		}
	}
	
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
			log.info("Max entropy implementation of doc Categorizer initialized");
			return docCategorizer;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@Bean
	public NlpService nlpService(){
		
		log.info("initilizing openNLP nlp service");
		
		return new OpenNlpServiceImpl();
		
	}
	
	/**
	 * 
	 * @return
	 */
	@Bean
	public RuleService ruleService(){
		log.info("initilizing simple rule service");
		
		return new SimpleRuleServiceImpl();
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
