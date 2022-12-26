package br.com.springjava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("RESTful API Java 18 and Spring")
						.version("v1")
						.description("Description example")
						.termsOfService("www.termosdeservicoexemplo.com")
						.license(new License().name("Apache 2.0")
								.url("www.urlexemplo.com"))); 
	}
	
}
