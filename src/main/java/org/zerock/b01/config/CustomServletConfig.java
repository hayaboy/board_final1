package org.zerock.b01.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; //@EnableWebMvc를 통해 활성화된 Spring MVC에 대한 Java 기반 구성을 사용자 정의하기 위한 콜백 메서드를 정의

@Configuration
@EnableWebMvc  //To customize the imported configuration, implement the interface WebMvcConfigurer
public class CustomServletConfig implements WebMvcConfigurer {


    //ResourceHandlerRegistry
    // 웹 브라우저에서 효율적인 로딩을 위해 최적화된 캐시 헤더 설정을 포함하여 Spring MVC를 통해 이미지, CSS 파일 등과 같은 정적 리소스를 제공하기 위한 리소스 핸들러 등록을 저장합니다.
    // 리소스는 웹 애플리케이션 루트 아래 위치, 클래스 경로 등에서 제공될 수 있습니다.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("classpath:/static/fonts/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/assets/**").
                addResourceLocations("classpath:/static/assets/");

    }
}
