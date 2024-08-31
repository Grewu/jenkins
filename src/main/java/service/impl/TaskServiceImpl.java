package service.impl;

import dao.api.TaskDao;
import mapper.TaskMapper;
import model.dto.request.TaskRequest;
import model.dto.response.TaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.api.TaskService;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskServiceImpl(TaskDao taskDao, TaskMapper taskMapper) {
        this.taskDao = taskDao;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskResponse create(TaskRequest taskRequest) {
        var task = taskMapper.toTask(taskRequest);
        return taskMapper.toTaskResponse(taskDao.create(task));
    }

    @Override
    public List<TaskResponse> getAll() {
        return taskMapper.toListOfTaskResponse(taskDao.findAll());
    }

    @Override
    public TaskResponse getById(Long id) {
        return taskDao.findById(id)
                .map(taskMapper::toTaskResponse)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id " + id));
    }

    @Override
    public TaskResponse update(TaskRequest taskRequest) {
        var task = taskMapper.toTask(taskRequest);
        return taskMapper.toTaskResponse(taskDao.update(task));
    }

    @Override
    public boolean delete(Long id) {
        return taskDao.delete(id);
    }
}
