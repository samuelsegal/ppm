package com.sms.ppm.services;

import com.sms.ppm.domain.Project;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectService {
    Project saveOrUpdateProject(Project project);

    Project findProjectByIdentifier(String projectId);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    Iterable<Project> findAllProjects();

    void deleteProjectByIdentifier(String projectid);
}
