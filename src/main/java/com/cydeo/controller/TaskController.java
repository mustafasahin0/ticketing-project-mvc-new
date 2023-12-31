package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/task")
public class TaskController {

    ProjectService projectService;
    UserService userService;
    TaskService taskService;

    public TaskController(ProjectService projectService, UserService userService, TaskService taskService) {
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/create")
    public String createTask(Model model){
        model.addAttribute("task", new TaskDTO());
        model.addAttribute("projects",projectService.findAll());
        model.addAttribute("employees", userService.employees());
        model.addAttribute("tasks", taskService.findAll());
        return "/task/create";
    }

    @PostMapping("/create")
    public String insertTask(@ModelAttribute TaskDTO taskDTO){
        taskService.save(taskDTO);

        return "redirect:/task/create";
    }

    @GetMapping("/update/{taskId}")
    public String editTask(@PathVariable Long taskId, Model model){

        model.addAttribute("task", taskService.findById(taskId));
        model.addAttribute("projects",projectService.findAll());
        model.addAttribute("employees", userService.employees());
        model.addAttribute("tasks", taskService.findAll());

        return "/task/update";
    }

    @PostMapping("/update/{taskId}")
    public String updateTask(@PathVariable Long taskId, @ModelAttribute TaskDTO taskDTO){
        taskDTO.setId(taskId);
        taskDTO.setTaskStatus(taskService.findById(taskId).getTaskStatus());
        taskDTO.setAssignedDate(taskService.findById(taskId).getAssignedDate());
        taskService.update(taskDTO);

        return "redirect:/task/create";
    }

    @GetMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable Long taskId){

        taskService.deleteById(taskId);
        return "redirect:/task/create";
    }
}
