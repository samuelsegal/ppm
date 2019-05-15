package com.sms.ppm.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@Profile(value = {"dev"})
public class DevConfig {

	@Bean
    CommandLineRunner run(){
        return args -> {
        	log.debug("Demonstration of using CommandLineRunner for dev purposes only");
        };
    }	
}
