package com.library.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @Author: 0045M
 * @Description:
 * @Date: 17:32 2020/5/23
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean //配置docket以配置Swagger具体参数
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.library.controller")).build();
    }

    private ApiInfo apiInfo(){
        Contact contact = new Contact("mrh","https://blog.csdn.net/ban0045","851417245@qq.com");
        return new ApiInfo(
                "图书管理系统Api文档",
                "图书管理系统Api文档",
                "v1.0",
                "https://blog.csdn.net/ban0045",
                contact,
                "Apach 2.0许可",
                "https://blog.csdn.net/ban0045",
                new ArrayList<>()
        );

    }
}
