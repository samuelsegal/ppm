package com.sms.ppm.services;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sms.ppm.domain.Project;

public interface ProjectService {
    Project saveOrUpdateProject(Project project, String string);

    Project findProjectByIdentifier(String projectId, String username);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    Iterable<Project> findAllProjects();
    
    Iterable<Project> findAllProjectsByUser(String username);

    void deleteProjectByIdentifier(String projectid, String username);
}
