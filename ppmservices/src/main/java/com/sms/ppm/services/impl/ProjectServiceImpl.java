package com.sms.ppm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sms.ppm.domain.Backlog;
import com.sms.ppm.domain.PPMUser;
import com.sms.ppm.domain.Project;
import com.sms.ppm.exceptions.ProjectIdException;
import com.sms.ppm.repositories.BacklogRepository;
import com.sms.ppm.repositories.PPMUserRepository;
import com.sms.ppm.repositories.ProjectRepository;

@Service
public class ProjectServiceImpl implements com.sms.ppm.services.ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BacklogRepository backlogRepository;
    @Autowired
    private PPMUserRepository ppmUserRepository;
    @Override
    public Project saveOrUpdateProject(Project project, String username) {

        try {
        	PPMUser user = ppmUserRepository.findByUsername(username);
        	project.setPpmuser(user);
            String identifier = project.getProjectIdentifier().toUpperCase();
            project.setProjectIdentifier(identifier);
            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            } else {
                project.setBacklog(backlogRepository.findByProjectIdentifier(identifier));
            }
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + "' already not exist");
        }

    }


    @Override
    public Project findProjectByIdentifier(String projectId, String username) {

        Project project = projectRepository.findByProjectIdentifierAndPpmuserUsername(projectId.toUpperCase(), username);

        if (project == null) {
            throw new ProjectIdException("Project ID '" + "' does not exist");

        }


        return project;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public Iterable<Project> findAllProjects() {
        Iterable<Project> allProjects = projectRepository.findAll();
        return allProjects;
    }


    @Override
    public void deleteProjectByIdentifier(String projectid, String username) {
        Project project = projectRepository.findByProjectIdentifierAndPpmuserUsername(projectid.toUpperCase(), username);

        if (project == null) {
            throw new ProjectIdException("Cannot Project with ID '" + projectid + "'. This project does not exist");
        }

        projectRepository.delete(project);
    }


	@Override
	public Iterable<Project> findAllProjectsByUser(String username) {
        Iterable<Project> allProjects = projectRepository.findAllByPpmuserUsername(username);
        return allProjects;
	}



}