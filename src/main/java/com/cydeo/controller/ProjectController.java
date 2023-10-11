package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/project")
public class ProjectController {

    UserService userService;
    ProjectService projectService;

    public ProjectController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("/create")
    public String createProject(Model model) {

        model.addAttribute("project", new ProjectDTO());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("managers", userService.managers());

        return "/project/create";
    }

    @PostMapping("/create")
    public String insertProject(@ModelAttribute ProjectDTO projectDTO) {
        projectService.save(projectDTO);

        return "redirect:/project/create";
    }

    @GetMapping("/update/{projectCode}")
    public String editProject(@PathVariable String projectCode, Model model) {

        model.addAttribute("project", projectService.findById(projectCode));
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("managers", userService.managers());

        return "/project/update";
    }

    @PostMapping("/update")
    public String updateProject(@ModelAttribute ProjectDTO projectDTO) {

        projectService.save(projectDTO);

        return "redirect:/project/create";
    }

    @GetMapping("/delete/{projectCode}")
    public String updateProject(@PathVariable String projectCode) {

        projectService.deleteById(projectCode);

        return "redirect:/project/create";
    }

    @GetMapping("/complete/{projectCode}")
    public String completeProject(@PathVariable String projectCode) {

        projectService.findById(projectCode).setProjectStatus(Status.COMPLETE);

        return "redirect:/project/create";
    }


}
