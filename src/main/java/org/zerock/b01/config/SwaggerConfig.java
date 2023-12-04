package org.zerock.b01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket; //Springfox 프레임워크의 기본 인터페이스로 사용되는 빌더입니다. 구성을 위한 합리적인 기본값과 편의 방법을 제공합니다.

@Configuration
public class SwaggerConfig {


    @Bean
    public Docket api() {

        //Swagger UI 화면 설정
//OpenAPI Specification (OAS)의 버전 3.0, OpenAPI Specification 3.0을 사용하여 API 문서를 생성하기 위한 SpringFox의 Docket 객체를 만든다는 것입니다.
// OpenAPI Specification은 API에 대한 명세를 기술하기 위한 표준이며, 버전 3.0은 그 중에서도 특정 버전을 의미합니다. 이를 통해 API의 설계와 문서화를 효과적으로 관리할 수 있습니다.
        return new Docket(DocumentationType.OAS_30).useDefaultResponseMessages(false).select()
                .apis(RequestHandlerSelectors.basePackage("org.zerock.b01.controller")).paths(PathSelectors.any()).build().apiInfo(apiInfo());
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Boot 01 Project Swagger")
                .build();
    }

}
