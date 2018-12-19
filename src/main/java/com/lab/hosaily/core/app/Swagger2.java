package com.lab.hosaily.core.app;

import com.google.common.base.Predicate;
import com.lab.hosaily.core.app.webservice.ProfileRestController;
import com.lab.hosaily.core.client.webservice.AgreementRestController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;
import static com.google.common.base.Predicates.or;

@EnableWebMvc
@ComponentScan(basePackages = {"com.lab.hosaily.core.app","com.lab.hosaily.core.client","com.lab.hosaily.core.account.webservice"})
@Profile("development222")
@EnableSwagger2
@Configuration
public class Swagger2 {
    //swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.lab.hosaily.core"))
//                .paths(PathSelectors.regex("*/agreement/*"))
                .paths(PathSelectors.any())
                .build();
    }
    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("永恒情书 app端 API")
                //创建人
                .contact(new Contact("Issac Song", "http://www.baidu.com", ""))
                //版本号
                .version("12597.2.31.22.0")
                //描述
                .description("API 描述")
                .build();
    }

    private Predicate<String> paths() {
        return or(regex("/api/1.0/app/*"));
    }


}
