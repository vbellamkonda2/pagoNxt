package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class Swagger2UiConfiguration {
	@Bean
	@Profile("!prod")
	  public OpenAPI cmdmOpenAPI() {
	      return new OpenAPI()
	              .info(new Info().title("Internet Shop Services")
	              .description("Shopping Cart and Payment operations")
	              .version("1.0")
	              .license(new License().name("API License").url("https://www.pagonxt.de/")))
	              .externalDocs(new ExternalDocumentation()
	              .description("Santander")
	              .url("https://www.santander.com/en/home/"));
	  }
}
