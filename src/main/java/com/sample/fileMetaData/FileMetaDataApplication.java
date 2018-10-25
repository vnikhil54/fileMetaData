package com.sample.fileMetaData;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@SpringBootApplication
public class FileMetaDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileMetaDataApplication.class, args);
    }

	/**
     *
     * apiInfo : Sets the api's meta information as included in the json ResourceListing response.
     *
     * groupName : the unique identifier of this swagger group/configuration
     *
     * consumes and produces are the media types.
     *
     */
    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).groupName("fileMetaData").consumes(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
                .produces(Collections.singleton(MediaType.APPLICATION_JSON_VALUE)).select().paths(regex("/*.*")).build();
    }

    /**
     * Sets the api's meta information as included in the json ResourceListing response.
     *
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("File-Meta-Data Services").build();
    }
}
