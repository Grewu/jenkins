package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.dto.request.TaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import service.api.TaskService;

import java.io.IOException;

@Controller
public class TaskController {

    private final TaskService taskService;
    private final ObjectMapper objectMapper;

    @Autowired
    public TaskController(TaskService taskService, ObjectMapper objectMapper) {
        this.taskService = taskService;
        this.objectMapper = objectMapper;
    }

    public String create(TaskRequest taskRequest) throws IOException {
        return objectMapper.writeValueAsString(taskService.create(taskRequest));
    }

    public String getAll() throws IOException {
        return objectMapper.writeValueAsString(taskService.getAll());
    }

    public String getById(Long id) throws IOException {
        return objectMapper.writeValueAsString(taskService.getById(id));
    }

    public String update(TaskRequest taskRequest) throws IOException {
        return objectMapper.writeValueAsString(taskService.update(taskRequest));
    }

    public void delete(Long id) {
        taskService.delete(id);
    }

}
