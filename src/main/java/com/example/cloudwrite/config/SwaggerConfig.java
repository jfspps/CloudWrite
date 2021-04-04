package com.example.cloudwrite.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {

        Contact contact = new Contact();
        contact.setEmail("jfsppsDev@gmail.com");
        contact.setName("James");
        contact.setUrl("https://github.com/jfspps/CloudWrite");

        return new OpenAPI()
                .info(new Info().title("CloudWrite")
                        .description("Collaborative article planning in the cloud")
                        .version("v0.0.1")
//                        .termsOfService("Terms of Service URL goes here")
                        .contact(contact)
                        .license(new License().name("GNU General Public License v2.0").url("https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("More about SpringDoc and OpenAPI 3")
                        .url("https://springdoc.org/"));
    }
}
