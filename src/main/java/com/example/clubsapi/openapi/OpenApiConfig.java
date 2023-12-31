package com.example.clubsapi.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Clubs API",
                description = "This Apllication shows the role based Authenticaiton jpa mapping and ecpection Handlling" +
                        "This application has feature like" +
                        "1) User Registration and Login" +
                        "2) Register club and event with Moderator access" +
                        "add delete edit clubs and events"

        ),
        servers = @Server(
                description = "Local Dev",
                url="http://localhost:5000"
        ),security = {
        @SecurityRequirement(
                name = "Bearer Token"
        )
}
)
@SecurityScheme(
        name = "Bearer Token",
        description = "JWT Auth Description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
