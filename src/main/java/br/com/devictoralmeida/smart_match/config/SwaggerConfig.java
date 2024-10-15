package br.com.devictoralmeida.smart_match.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  OpenAPI openAPI() {
    return new OpenAPI()
      .info(new Info().title("Smart Match").description("API responsável por gestão de vagas").version("1").contact(new Contact()
        .name("Victor Almeida")
        .email("victoremmanuelmn@gmail.com")));
  }

  private io.swagger.v3.oas.models.security.SecurityScheme createSecurityScheme() {
    return new SecurityScheme().name("jwt_auth").type(SecurityScheme.Type.HTTP).scheme("bearer")
      .bearerFormat("JWT");
  }
}
