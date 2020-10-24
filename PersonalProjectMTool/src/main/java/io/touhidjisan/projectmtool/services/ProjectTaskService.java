package io.touhidjisan.projectmtool.services;

import io.touhidjisan.projectmtool.exceptions.ProjectNotFoundException;
import io.touhidjisan.projectmtool.model.Backlog;
import io.touhidjisan.projectmtool.model.Project;
import io.touhidjisan.projectmtool.model.ProjectTask;
import io.touhidjisan.projectmtool.repositories.BacklogRepository;
import io.touhidjisan.projectmtool.repositories.ProjectRepository;
import io.touhidjisan.projectmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {
    private ProjectTaskRepository projectTaskRepository;
    private BacklogRepository  backlogRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectTaskService(ProjectTaskRepository projectTaskRepository, BacklogRepository backlogRepository, ProjectRepository projectRepository) {
        this.projectTaskRepository = projectTaskRepository;
        this.backlogRepository = backlogRepository;
        this.projectRepository = projectRepository;
    }

    public ProjectTask addProjectTask(String projectIdentifier , ProjectTask projectTask) {
        try {
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
        } catch (Exception ex) {
            throw  new ProjectNotFoundException("Project Not Found");
        }
    }

    public Iterable<ProjectTask> findBacklogById(String id) {
        Project project = projectRepository.findByProjectIdentifier(id);
        if(project == null) {
            throw new ProjectNotFoundException("Project id: '" + id + "' does not exists");
        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }
}
