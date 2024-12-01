package com.mountblue.Youtube_Clone.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("/home/kasetti-bhavya-sai-sri/Documents/Spring/Youtube_Clone/src/main/resources/static/images")
                .setCachePeriod(3600);
    }
}
