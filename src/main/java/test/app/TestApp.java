package test.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import lombok.extern.slf4j.Slf4j;
import test.app.config.TestAppConfig;

/**
 * Spring boot application for Test PoC
 * @author Minkeat.Wong
 *
 */
@SpringBootApplication(scanBasePackages = {"test.rest.controller"})
@Slf4j
public class TestApp {
	
	public static final void main(String[] args) throws Exception{
		log.info("Starting standalone spring boot application");
		
		new SpringApplicationBuilder(TestApp.class).sources(TestAppConfig.class)
												   .run(args);

	}

}
