package com.sms.ppm.services;

import com.sms.ppm.domain.Backlog;
import com.sms.ppm.domain.ProjectTask;
import com.sms.ppm.repositories.BacklogRepository;
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

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
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
    }
}
