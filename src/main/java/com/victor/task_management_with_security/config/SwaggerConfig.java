package com.victor.task_management_with_security.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info =@Info(
                title = "Advanced Task Management Application",
                description = "The official documentation of this Task Management Application",
                version = "v1.0",
                contact = @Contact(
                        name = "Victor Adepoju",
                        email = "victoradepoju30@gmail.com",
                        url = "https://github.com/victoradepoju/TaskManagementPrunny"
                ),
                license = @License(
                        name = "Victor Adepoju",
                        url = "https://github.com/victoradepoju/TaskManagementPrunny"
                ),
                termsOfService = "Terms of service"
        ),
        externalDocs = @ExternalDocumentation(
                description = "Api Documentation",
                url = "https://github.com/victoradepoju/TaskManagementPrunny"
        ),
        servers = {
                @Server(
                        description = "Local Environment",
                        url = "http://localhost:8088"
                ),
                @Server(
                        description = "Production Environment",
                        url = "https://railway-taskmanagementprunny-production.up.railway.app/"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }

)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
}
