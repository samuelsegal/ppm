package com.sms.ppm.services;

import com.sms.ppm.domain.Backlog;
import com.sms.ppm.domain.ProjectTask;
import com.sms.ppm.repositories.BacklogRepository;
import com.sms.ppm.repositories.ProjectTaskRepository;
import com.sms.ppm.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){
       Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
       projectTask.setBacklog(backlog);
       Integer backLogSequence = backlog.getPTSequence();
       backLogSequence++;
       projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + backLogSequence);
       projectTask.setProjectIdentifier(backlog.getProjectIdentifier());

       if(projectTask.getStatus() == "" || projectTask.getStatus() == null){
           projectTask.setStatus(Status.TODO.toString());
       }
       return projectTaskRepository.save(projectTask);
    }
}
