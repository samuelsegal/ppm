package com.sms.ppm.services.impl;

import com.sms.ppm.domain.Backlog;
import com.sms.ppm.domain.Project;
import com.sms.ppm.exceptions.ProjectIdException;
import com.sms.ppm.repositories.BacklogRepository;
import com.sms.ppm.repositories.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ProjectServiceImpl implements com.sms.ppm.services.ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BacklogRepository backlogRepository;

    @Override
    public Project saveOrUpdateProject(Project project) {
        String identifier = project.getProjectIdentifier().toUpperCase();
        try {
            project.setProjectIdentifier(identifier);
            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBackLog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            } else {
                project.setBackLog(backlogRepository.findByProjectIdentifier(identifier));
            }
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + "' already not exist");
        }

    }


    @Override
    public Project findProjectByIdentifier(String projectId) {

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project ID '" + "' does not exist");

        }


        return project;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public Iterable<Project> findAllProjects() {
        Iterable<Project> allProjects = projectRepository.findAll();
        if(log.isDebugEnabled()){
            allProjects.forEach(project -> {
                log.debug("Found project: {}", project);
            });
        }
        return allProjects;
    }


    @Override
    public void deleteProjectByIdentifier(String projectid) {
        Project project = projectRepository.findByProjectIdentifier(projectid.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Cannot Project with ID '" + projectid + "'. This project does not exist");
        }

        projectRepository.delete(project);
    }
}