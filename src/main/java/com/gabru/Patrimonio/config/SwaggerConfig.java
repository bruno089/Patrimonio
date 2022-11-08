package com.gabru.Patrimonio.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
@Data
public class SwaggerConfig {

    private String title;
    private String description;
    private String version;
    private String termsOfServiceUrl;
    private String license;
    private String licenseUrl;
    private String contactName;
    private String contactUrl;
    private String contactEmail;
    @Bean
    Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securitySchemes(basicScheme())
                .securityContexts(Arrays.asList(securityContext()))
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation((RestController.class)))
                .paths(PathSelectors.any())
                .build()
                ;
    }
    private ApiInfo apiInfo(){
        title = "API Patrimonio";
        description = "Permite guardar y gestionar gastos e ingresos y claisifcarlos en conceptos." +
                "Ademas posteriomente consultarlos a traves de algunas consultas establecidas. ";
        version = "V 1.0.0";
        termsOfServiceUrl = "Patrimonio API";
        contactName = "GHGB - Bruno Lopez";
        contactUrl = "www.brunolopezcross.com";
        contactEmail = "bruno.lopezcross@gmail.com";
        license = "Apache 2.0";
        licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0";

        return new ApiInfo(title, description,version,"",
                new Contact(contactName,contactUrl,contactEmail)
                ,license,licenseUrl, Collections.emptyList());
    }
    private List<SecurityScheme> basicScheme() {
        List<SecurityScheme> schemeList = new ArrayList<>();
        schemeList.add(new BasicAuth("basicAuth"));
        return schemeList;
    }
    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }
    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global","AccesEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("basicAuth",authorizationScopes));
    }
}
