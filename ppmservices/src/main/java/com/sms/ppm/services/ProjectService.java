package com.sms.ppm.services;

import com.sms.ppm.domain.Backlog;
import com.sms.ppm.domain.Project;
import com.sms.ppm.exceptions.ProjectIdException;
import com.sms.ppm.repositories.BacklogRepository;
import com.sms.ppm.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BacklogRepository backlogRepository;

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
            throw new ProjectIdException(new StringBuilder("Project ID '").append(identifier).append("' already not exist").toString());
        }

    }


    public Project findProjectByIdentifier(String projectId) {

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException(new StringBuilder("Project ID '").append(projectId.toUpperCase()).append("' does not exist").toString());

        }


        return project;
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }


    public void deleteProjectByIdentifier(String projectid) {
        Project project = projectRepository.findByProjectIdentifier(projectid.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Cannot Project with ID '" + projectid + "'. This project does not exist");
        }

        projectRepository.delete(project);
    }
}