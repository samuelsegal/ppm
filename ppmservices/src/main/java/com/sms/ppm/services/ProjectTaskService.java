package com.sms.ppm.services;

import com.sms.ppm.domain.Backlog;
import com.sms.ppm.domain.Project;
import com.sms.ppm.domain.ProjectTask;
import com.sms.ppm.exceptions.ProjectNotFoundException;
import com.sms.ppm.repositories.BacklogRepository;
import com.sms.ppm.repositories.ProjectRepository;
import com.sms.ppm.repositories.ProjectTaskRepository;
import com.sms.ppm.util.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        try {
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);
            Integer backLogSequence = backlog.getPTSequence();
            backlog.setPTSequence(++backLogSequence);
            projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + backLogSequence);
            projectTask.setProjectIdentifier(backlog.getProjectIdentifier());

            if (projectTask.getStatus() == null || "".equals(projectTask.getStatus())) {
                projectTask.setStatus(Status.TODO.toString());
            }
            if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
                projectTask.setPriority(3);
            }
            log.info("Adding projectTask: {}", projectTask);
            return projectTaskRepository.save(projectTask);
        } catch (Exception e) {
            log.error("Error adding project task: {}", e.getMessage());
            throw new ProjectNotFoundException("Project with ID: " + projectIdentifier + " does not exist.");
        }
    }

    public Iterable<ProjectTask> findBacklogById(String id){
        Project project  = projectRepository.findByProjectIdentifier(id);
        if(project == null){
            throw new ProjectNotFoundException("Project with ID: " + id + " does not exist.");
        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_sequence){
        return projectTaskRepository.findByProjectSequence(pt_sequence);
    }
}
