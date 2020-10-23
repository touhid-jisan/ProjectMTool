package io.touhidjisan.projectmtool.services;

import io.touhidjisan.projectmtool.model.Backlog;
import io.touhidjisan.projectmtool.model.ProjectTask;
import io.touhidjisan.projectmtool.repositories.BacklogRepository;
import io.touhidjisan.projectmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {
    private ProjectTaskRepository projectTaskRepository;
    private BacklogRepository  backlogRepository;

    @Autowired
    public ProjectTaskService(ProjectTaskRepository projectTaskRepository, BacklogRepository backlogRepository) {
        this.projectTaskRepository = projectTaskRepository;
        this.backlogRepository = backlogRepository;
    }

    public ProjectTask addProjectTask(String projectIdentifier , ProjectTask projectTask) {
        // PTs to be added to a specific project, project != null, Backlog exists
        // set the backlog to the project task
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

        // set the bl to pt
        projectTask.setBacklog(backlog);

        // we want our project id sequence to be like AAAA-1 AAAA-3... -100 101
        Integer BacklogSequence = backlog.getPTSequence();

        // update the Backlog sequence
        BacklogSequence++;
        backlog.setPTSequence(BacklogSequence);

        // add sequence to Project Task
        projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        // initial priority, when priority null
        if(projectTask.getPriority() ==null) {
            projectTask.setPriority(3);
        }

        // initial status when status is null
        if(projectTask.getStatus() == "" || projectTask.getStatus() == null) {
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
    }
}
