package com.springboot.prac.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;
import static com.google.common.base.Predicates.or;

@Configuration
@EnableSwagger2
@ConfigurationProperties("swagger-conf.api")
@ConditionalOnProperty(name="swagger-conf.api.swagger.enable", havingValue = "true", matchIfMissing = false)
public class SwaggerConfig {

    private String title;
    private String version;
    private String description;
    private String basePackage;
    private String contactName;
    private String contactEmail;

    @Bean
    public Docket postApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("post")
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(this.postPaths())
                .build();
    }

    @Bean
    public Docket getApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("get")
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(this.getPaths())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .contact(new Contact(contactName, null, contactEmail))
                .version(version)
                .build();
    }

    private Predicate<String> postPaths() {
        return or(
                regex("/api/add.*"),
                regex("/api/update.*"),
                regex("/api/delete.*") );
    }

    private Predicate<String> getPaths() {
        return regex("/api/get.*");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
