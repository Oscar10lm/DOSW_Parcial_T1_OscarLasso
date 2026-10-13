package edu.dosw.parcial.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("UNIVERSITY FOOD HUB API — DOSW Parcial T2")
                .description("API RESTful para la gestión y construcción de pedidos de cafetería universitaria integrando patrones Builder y Decorator con persistencia en MongoDB.")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Oscar David Lasso Martinez")
                    .email("oscar.lasso@mail.escuelaing.edu.co"))
                .license(new License().name("Apache 2.0")));
    }
}
