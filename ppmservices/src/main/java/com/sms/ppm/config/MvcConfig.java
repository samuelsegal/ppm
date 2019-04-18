package com.sms.ppm.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        Hibernate5Module h5module = new Hibernate5Module();
        h5module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
        h5module.enable(Hibernate5Module.Feature.FORCE_LAZY_LOADING);

        for (HttpMessageConverter<?> mc : converters) {
            if (mc instanceof MappingJackson2HttpMessageConverter || mc instanceof MappingJackson2XmlHttpMessageConverter) {
                ((AbstractJackson2HttpMessageConverter) mc).getObjectMapper().registerModule(h5module);
            }
        }
    }
    @Bean
    public Module datatypeHibernateModule() {
        return new Hibernate5Module();
    }

}