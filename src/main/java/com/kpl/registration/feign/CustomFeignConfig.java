package com.kpl.registration.feign;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CustomFeignConfig {

    @Bean
    public RequestInterceptor authInterceptor() {
        return template -> {
            ServletRequestAttributes attrs =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attrs != null) {
                var token = attrs.getRequest().getHeader("Authorization");
                template.header("Authorization", token);
            }
        };
    }
}

