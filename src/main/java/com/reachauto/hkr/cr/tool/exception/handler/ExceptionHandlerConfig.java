package com.reachauto.hkr.cr.tool.exception.handler;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Created with IntelliJ IDEA.
 * User: chenxiangning
 * Date: 2018-01-19 13:29
 * This is my work in reachauto code.
 * mail:chenxiangning@reachauto.com
 * Description:
 */
@Configuration
public class ExceptionHandlerConfig {
    public ExceptionHandlerConfig() {
    }

    @Bean
    protected MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("MessageSource");
        return messageSource;
    }
}