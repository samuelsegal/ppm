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

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        try {
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
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
    public Iterable<ProjectTask> findBacklogById(String id) {
        Project project = projectRepository.findByProjectIdentifier(id);
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
    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_sequence) {


        //make sure backlog exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
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
    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_sequence){
        //waiting for reason to do this.going to revisit
        ProjectTask pt = findPTByProjectSequence(backlog_id, pt_sequence);
        pt = updatedTask;
        return projectTaskRepository.save(pt);
    }

    @Override
    public void deleteByProjectSequence(String backlog_id, String pt_sequence) {
        ProjectTask pt = findPTByProjectSequence(backlog_id, pt_sequence);
        projectTaskRepository.delete(pt);
    }

    private void throwProjectNotFoundException(String id) {
        throw new ProjectNotFoundException("Project with ID: " + id + " does not exist.");
    }
}
