package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.interceptor.PermissionInterceptor;

@Component
@ComponentScan(basePackages="com.controller")
public class WebApplicationConfig extends WebMvcConfigurerAdapter{
	
	@Bean
    public PermissionInterceptor interceptor() {
        return new PermissionInterceptor();
    }
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		 registry.addInterceptor(interceptor()).addPathPatterns("/**");
	}
	
}
