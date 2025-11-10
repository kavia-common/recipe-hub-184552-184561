package com.example.recipebackend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration for Swagger UI documentation.
 */
@Configuration
public class OpenApiConfig {

    // PUBLIC_INTERFACE
    @Bean
    public OpenAPI recipeHubOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Recipe Hub API")
                        .description("REST API for managing recipes, categories, and user auth.")
                        .version("0.1.0")
                        .contact(new Contact()
                                .name("Recipe Hub")
                                .email("support@example.com")))
                .addTagsItem(new Tag().name("Recipes").description("Recipe management endpoints"))
                .addTagsItem(new Tag().name("Categories").description("Category listing endpoints"))
                .addTagsItem(new Tag().name("Auth").description("User authentication endpoints"))
                .externalDocs(new ExternalDocumentation()
                        .description("Swagger UI")
                        .url("/swagger-ui.html"));
    }
}
