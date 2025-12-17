package com.muhardin.endy.belajar.htmx.controller;

import com.muhardin.endy.belajar.htmx.model.Project;
import com.muhardin.endy.belajar.htmx.repository.ProjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ProjectController {

    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping("/projects")
    public String getProjects(Model model) {
        List<Project> projects = projectRepository.findAllByOrderByCreatedAtDesc();
        model.addAttribute("projects", projects);
        return "fragments/project-list";
    }

    @PostMapping("/projects")
    public String createProject(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam LocalDate startDate,
            @RequestParam(required = false) BigDecimal budget,
            @RequestParam String priority,
            Model model,
            jakarta.servlet.http.HttpServletResponse response) {

        // Create and save project
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setStartDate(startDate);
        project.setBudget(budget);
        project.setPriority(Project.Priority.valueOf(priority.toUpperCase()));

        Project savedProject = projectRepository.save(project);

        // Trigger HTMX events: close modal + refresh list
        response.setHeader("HX-Trigger", "projectCreated, refreshProjects");

        // Return empty response since we'll refresh the whole list
        return "fragments/empty";
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable Long id,
            jakarta.servlet.http.HttpServletResponse response) {

        projectRepository.deleteById(id);

        // Trigger refresh of project list
        response.setHeader("HX-Trigger", "refreshProjects");

        return ResponseEntity.ok().build();
    }
}
