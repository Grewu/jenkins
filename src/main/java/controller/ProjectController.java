package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.dto.request.ProjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import service.api.ProjectService;

import java.io.IOException;

@Controller
public class ProjectController {
    private final ProjectService projectService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProjectController(ProjectService projectService, ObjectMapper objectMapper) {
        this.projectService = projectService;
        this.objectMapper = objectMapper;
    }


    public String create(ProjectRequest projectRequest) throws IOException {
        return objectMapper.writeValueAsString(projectService.create(projectRequest));
    }

    public String getAll() throws IOException {
        return objectMapper.writeValueAsString(projectService.getAll());
    }

    public String getById(Long id) throws IOException {
        return objectMapper.writeValueAsString(projectService.getById(id));
    }

    public String update(ProjectRequest projectRequest) throws IOException {
        return objectMapper.writeValueAsString(projectService.update(projectRequest));
    }

    public void delete(Long id) {
        projectService.delete(id);
    }
}
