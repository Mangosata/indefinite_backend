package com.overwall.demo.config;

import com.overwall.demo.component.LoginHandlerInterceptor;
import com.overwall.demo.component.MyLocalResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

//    @Bean
//    public WebMvcConfigurer webMvcConfigurerAdapter() {
//        WebMvcConfigurer adapter = new WebMvcConfigurer() {
//            @Override
//            public void addViewControllers(ViewControllerRegistry registry) {
//                registry.addViewController("/").setViewName("mylogin");
//                registry.addViewController("/index.html").setViewName("mylogin");
//                registry.addViewController("/main.html").setViewName("dashboard");
//            }
//
//            @Override
//            public void addInterceptors(InterceptorRegistry registry) {
//                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
//                        .excludePathPatterns("/index.html", "/", "/users/login", "/static/**");
//            }
//        };
//        return adapter;
//    }

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocalResolver();
    }
}
