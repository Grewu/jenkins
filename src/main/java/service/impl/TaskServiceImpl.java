package service.impl;

import dao.api.TaskDao;
import exception.EntityNotFoundException;
import mapper.TaskMapper;
import model.dto.request.TaskRequest;
import model.dto.response.TaskResponse;
import model.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.api.TaskService;

import java.util.List;
import java.util.stream.Collectors;

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
    @Transactional
    public TaskResponse create(TaskRequest taskRequest) {
        var task = taskMapper.toTask(taskRequest);
        return taskMapper.toTaskResponse(taskDao.create(task));
    }

    @Override
    public List<TaskResponse> getAll() {
        return taskDao.findAll().stream()
                .map(taskMapper::toTaskResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse getById(Long id) {
        return taskDao.findById(id)
                .map(taskMapper::toTaskResponse)
                .orElseThrow(() -> new EntityNotFoundException(Task.class, id));
    }

    @Override
    @Transactional
    public TaskResponse update(Long id, TaskRequest taskRequest) {
        var task = taskMapper.toTask(taskRequest);
        return taskMapper.toTaskResponse(taskDao.update(task));
    }

    @Override
    @Transactional
    public void delete(TaskRequest taskRequest) {
        var task = taskMapper.toTask(taskRequest);
        taskDao.delete(task);
    }
}
