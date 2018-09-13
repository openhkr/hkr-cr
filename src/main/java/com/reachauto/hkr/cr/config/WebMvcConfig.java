package com.reachauto.hkr.cr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by haojr on 17/06/05.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(resourceConverter());
        converters.add(jacksonConverter());
        converters.add(stringConverter());
    }

    @Bean
    MappingJackson2HttpMessageConverter jacksonConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = ObjectMapperHolder.getInstance().getMapper();
        converter.setObjectMapper(mapper);
        return converter;
    }

    @Bean
    StringHttpMessageConverter stringConverter() {
        return new StringHttpMessageConverter();
    }

    @Bean
    ResourceHttpMessageConverter resourceConverter() {
        return new ResourceHttpMessageConverter();
    }

}
