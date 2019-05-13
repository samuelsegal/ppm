package com.sms.ppm.services.impl;

import com.sms.ppm.domain.Backlog;
import com.sms.ppm.domain.Project;
import com.sms.ppm.domain.ProjectTask;
import com.sms.ppm.exceptions.ProjectNotFoundException;
import com.sms.ppm.repositories.BacklogRepository;
import com.sms.ppm.repositories.ProjectRepository;
import com.sms.ppm.repositories.ProjectTaskRepository;
import com.sms.ppm.services.ProjectTaskService;
import com.sms.ppm.util.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProjectTaskServiceImpl implements ProjectTaskService {


    private BacklogRepository backlogRepository;
    private ProjectTaskRepository projectTaskRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectTaskServiceImpl(BacklogRepository backlogRepository, ProjectTaskRepository projectTaskRepository,
			ProjectRepository projectRepository) {
		super();
		this.backlogRepository = backlogRepository;
		this.projectTaskRepository = projectTaskRepository;
		this.projectRepository = projectRepository;
	}

	@Override
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
        try {
            Backlog backlog = backlogRepository.findByProjectIdentifierAndProjectPpmuserUsername(projectIdentifier, username);
            projectTask.setBacklog(backlog);
            Integer backLogSequence = backlog.getPTSequence();
            backlog.setPTSequence(++backLogSequence);
            projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + backLogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            if (projectTask.getStatus() == null || "".equals(projectTask.getStatus())) {
                projectTask.setStatus(Status.TO_DO.toString());
            }
            if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
                projectTask.setPriority(3);
            }
            log.info("Adding projectTask: {}", projectTask);
            return projectTaskRepository.save(projectTask);
        } catch (Exception e) {
            log.error("Error adding project task: {}", e.getMessage());
            throwProjectNotFoundException(projectIdentifier);
        }
        return null;
    }

    @Override
    public Iterable<ProjectTask> findBacklogByIdAndUsername(String id, String username) {
        Project project = projectRepository.findByProjectIdentifierAndPpmuserUsername(id, username);
        if (project == null) {
            throwProjectNotFoundException(id);
        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    @Override
    public Iterable<ProjectTask> findAllProjectTasks() {
        return projectTaskRepository.findAll();
    }

    @Override
    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_sequence, String username) {


        //make sure backlog exists
        Backlog backlog = backlogRepository.findByProjectIdentifierAndProjectPpmuserUsername(backlog_id, username);
        if (backlog == null) {
            throwProjectNotFoundException(backlog_id);
        }
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_sequence);
        if (projectTask == null) {
            throw new ProjectNotFoundException("Project Task with sequence " + pt_sequence + " does not exist");
        }

        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project task " + pt_sequence + " does not exist in project " + backlog_id);
        }
        return projectTask;
    }

    @Override
    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_sequence, String username){
        //waiting for reason to do this.going to revisit
        ProjectTask pt = findPTByProjectSequence(backlog_id, pt_sequence, username);
        pt = updatedTask;
        return projectTaskRepository.save(pt);
    }

    @Override
    public void deleteByProjectSequence(String backlog_id, String pt_sequence, String username) {
        ProjectTask pt = findPTByProjectSequence(backlog_id, pt_sequence, username);
        projectTaskRepository.delete(pt);
    }

    private void throwProjectNotFoundException(String id) {
        throw new ProjectNotFoundException("Project with ID: " + id + " does not exist.");
    }
}
