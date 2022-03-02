package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Configuration
@EnableSwagger2
@Import({ BeanValidatorPluginsConfiguration.class })
public class SwaggerConfig {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
    /**
     * swagger properties instance.
     */
    @Autowired
    private SwaggerInfoSettingsProperties swaggerProperties;

    /**
     * api info.
     * @return docket object
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.example.demo."))
                // .paths(PathSelectors.ant("/api/*"))
                .build().apiInfo(apiInfo()).forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(Pageable.class)
                .ignoredParameterTypes(java.sql.Date.class)
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    /**
     * context.
     * @return context
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN)).build();
    }
    /**
     * default.
     * @return list
     */
    private List<SecurityReference> defaultAuth() {
        final AuthorizationScope authorizationScope = new AuthorizationScope(
                "global", "accessEverything");
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    /**
     * api info config.
     *
     * @return api info
     */
    private ApiInfo apiInfo() {
        final String title = this.swaggerProperties.getTitle();
        final String description = this.swaggerProperties.getDescription();
        final String version = this.swaggerProperties.getVersion();
        final String termsOfServiceUrl = this.swaggerProperties
                .getTermsOfServiceUrl();
        final String name = this.swaggerProperties.getName();
        final String url = this.swaggerProperties.getUrl();
        final String email = this.swaggerProperties.getEmail();
        final Contact contact = new Contact(name, url, email);
        final String license = this.swaggerProperties.getLicense();
        final String licenseUrl = this.swaggerProperties.getLicenseUrl();

        return new ApiInfo(title, description, version, termsOfServiceUrl,
                contact, license, licenseUrl, new ArrayList<>());
    }

}

