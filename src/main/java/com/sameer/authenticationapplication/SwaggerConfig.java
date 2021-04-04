package com.sameer.authenticationapplication;

import java.util.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket apiDocket() {


    ApiInfo apiInfo = new ApiInfo("Documentation for Login api",
                                  "",
                                  "1.0",
                                  "APACHE 2.0",
                                  new springfox.documentation.service.Contact(
                                      "Sameer Pande",
                                      "https://github.com/sameer766",
                                      "pandesameer76@gmail.com"),
                                  "Apache 2.0",
                                  "",
                                  Collections.emptyList());


    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.sameer.authenticationapplication"))
        .paths(PathSelectors.any())
        .build()
        .protocols(new HashSet<>(Arrays.asList("HTTP", "HTTPs")))
        .apiInfo(apiInfo);

  }

  @Bean
  public LinkDiscoverers discoverers() {
    List<LinkDiscoverer> plugins = new ArrayList<>();
    plugins.add(new CollectionJsonLinkDiscoverer());
    return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
  }

//
//  @Override
//  public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//    registry
//        .addResourceHandler("swagger-ui.html")
//        .addResourceLocations("classpath:/META-INF/resources/");
//
//    registry
//        .addResourceHandler("/webjars/**")
//        .addResourceLocations("classpath:/META-INF/resources/webjars/");
//  }


}
