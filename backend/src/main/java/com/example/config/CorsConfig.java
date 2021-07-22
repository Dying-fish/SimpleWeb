package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 跨域问题，放行Option
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //  允许所有路由进行跨域调用
        registry.addMapping("/**").
                //  允许所有域名进行跨域调用
                allowedOriginPatterns("*").
                //放行全部原始头信息
                allowedHeaders("*").
                //允许跨域调用的请求方法
                allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE").
                //  允许跨越发送cookie
                allowCredentials(true).
                maxAge(3600L);
    }
}
