package com.proyect.employee.employee.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
@Configuration
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",

        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@RequiredArgsConstructor
public class SwaggerConfig {

    private final EnvironmentConfig environmentConfig;
    @Bean
    public OpenAPI myOpenAPI() {
        String description = String.format("Credentials: </br>{" +
                        "</br>&nbsp;&nbsp;\"username\": \"%s\"," +
                        "</br>&nbsp;&nbsp;\"password\": \"%s\"</br>}",
                environmentConfig.getUser_admin(), environmentConfig.getPass_admin());

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .info(
                    new Info()
                        .title("Microservice for employee management")
                        .description(description)
                        .version("0.11")
        );
    }
}
