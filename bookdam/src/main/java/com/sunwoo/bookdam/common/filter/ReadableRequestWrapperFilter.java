package com.sunwoo.bookdam.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ReadableRequestWrapperFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // HttpServletRequest만 래핑
        if (request instanceof HttpServletRequest) {
            ReadableRequestWrapper wrapper = new ReadableRequestWrapper((HttpServletRequest) request);
            chain.doFilter(wrapper, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Bean
    public FilterRegistrationBean<ReadableRequestWrapperFilter> readableRequestWrapperFilterRegistration() {
        FilterRegistrationBean<ReadableRequestWrapperFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new ReadableRequestWrapperFilter());
        registration.addUrlPatterns("/*"); // 원하는 경로만 적용
        registration.setOrder(1); // 순서 (낮을수록 먼저)
        return registration;
    }

}

