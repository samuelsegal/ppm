package com.sms.ppm.services;

import com.sms.ppm.domain.ProjectTask;

public interface ProjectTaskService {
    ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask);

    Iterable<ProjectTask> findBacklogById(String id);

    ProjectTask findPTByProjectSequence(String backlog_id, String pt_sequence);

    ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_sequence);

    void deleteByProjectSequence(String backlog_id, String pt_sequence);
}
