package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl extends AbstractMapService<ProjectDTO, String> implements ProjectService {

    TaskService taskService;

    public ProjectServiceImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO save(ProjectDTO object) {
        if (Objects.isNull(object.getProjectStatus())) {
            object.setProjectStatus(super.findById(object.getProjectCode()).getProjectStatus());
        }
        return super.save(object.getProjectCode(), object);
    }

    @Override
    public List<ProjectDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void update(ProjectDTO object) {
        super.update(object.getProjectCode(), object);
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);
    }

    @Override
    public ProjectDTO findById(String id) {
        return super.findById(id);
    }

    @Override
    public void complete(ProjectDTO projectDTO) {
        projectDTO.setProjectStatus(Status.COMPLETE);
        super.save(projectDTO.getProjectCode(), projectDTO);
    }

    @Override
    public List<ProjectDTO> findAllNonCompletedProjects() {
        return super.findAll().stream().filter(each -> !each.getProjectStatus().equals(Status.COMPLETE)).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager) {
        // one goal - build that ProjectDTO - w AllArgConstructor
        List<ProjectDTO> projectDTOList = findAll().stream().filter(project -> project.getAssignedManager().equals(manager))
                .map(project -> {

                    List<TaskDTO> taskDTOList = taskService.findTaskByManager(manager);

                    int completeTaskCounts = (int) taskDTOList.stream().filter(each -> each.getProject().equals(project) && each.getTaskStatus().equals(Status.COMPLETE)).count();
                    int unfinishedTaskCounts = (int) taskDTOList.stream().filter(each -> each.getProject().equals(project) && !each.getTaskStatus().equals(Status.COMPLETE)).count();

                    project.setUnfinishedTaskCounts(unfinishedTaskCounts);
                    project.setCompleteTaskCounts(completeTaskCounts);

                    return project;
                }).toList();

        return projectDTOList;
    }
}
