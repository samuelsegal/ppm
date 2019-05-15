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
import com.sms.ppm.util.MessageConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProjectServiceImpl implements com.sms.ppm.services.ProjectService {

	private ProjectRepository projectRepository;
	private BacklogRepository backlogRepository;
	private PPMUserRepository ppmUserRepository;

	@Autowired
	public ProjectServiceImpl(ProjectRepository projectRepository, BacklogRepository backlogRepository,
			PPMUserRepository ppmUserRepository) {
		super();
		this.projectRepository = projectRepository;
		this.backlogRepository = backlogRepository;
		this.ppmUserRepository = ppmUserRepository;
	}

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
				project.setBacklog(
						backlogRepository.findByProjectIdentifierAndProjectPpmuserUsername(identifier, username));
			}
			return projectRepository.save(project);

		} catch (Exception e) {
			log.error("Error Saving Project: {}", e.getMessage());
			throw new ProjectIdException(
					MessageConstants.MSG_PROJECT_EXISTS.replace("{}", project.getProjectIdentifier()));
		}

	}

	@Override
	public Project findProjectByIdentifier(String projectId, String username) {

		Project project = projectRepository.findByProjectIdentifierAndPpmuserUsername(projectId.toUpperCase(),
				username);

		if (project == null) {
			log.error(MessageConstants.MSG_PROJECT_NOT_EXIST.replace("{}", projectId));
			throw new ProjectIdException(MessageConstants.MSG_PROJECT_NOT_EXIST.replace("{}", projectId));
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
		Project project = projectRepository.findByProjectIdentifierAndPpmuserUsername(projectid.toUpperCase(),
				username);

		if (project == null) {
			log.error(MessageConstants.ERROR_PROJECT_NOT_EXIST.replace("{}", projectid));
			throw new ProjectIdException(MessageConstants.ERROR_PROJECT_NOT_EXIST.replace("{}", projectid));
		}

		projectRepository.delete(project);
	}

	@Override
	public Iterable<Project> findAllProjectsByUser(String username) {
		Iterable<Project> allProjects = projectRepository.findAllByPpmuserUsername(username);
		return allProjects;
	}

}