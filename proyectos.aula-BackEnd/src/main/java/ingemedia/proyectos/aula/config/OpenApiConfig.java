package ingemedia.proyectos.aula.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "jwt", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(apiInfo());
    }

    public Info apiInfo() {
        return new Info()
                .title("Sistema Proyectos Aula")
                .description("API para un sistema de gestion de proyectos de aula")
                .version("v1.0.0")
                .license(new License()
                        .name("Apache License, Version 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0"))
                .contact(new Contact()
                        .name("ELKIN GRANADOS")
                        .url("https://github.com/carlosfernandoufps/seminario-integrador-3-2023-I")
                        .email("elkinadriangc@ufps.edu.co"))
                .termsOfService("https://www.ufps.edu.co/");
    }

}
