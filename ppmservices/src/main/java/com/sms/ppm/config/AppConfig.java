package com.sms.ppm.config;

import com.sms.ppm.domain.Project;
import com.sms.ppm.services.impl.ProjectServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class AppConfig {

    @Autowired
    private ProjectServiceImpl projectService;

    @Bean
    CommandLineRunner run(){
        return args -> {
            Iterable<Project> projects = projectService.findAllProjects();
            if(log.isDebugEnabled()){
                projects.forEach(project -> {
                    log.debug("Found project: {}", project);
                });
            }
        };
    }

}