package ru.senla.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.senla.model.dto.request.ProjectRequest;
import ru.senla.model.dto.response.ProjectResponse;
import ru.senla.model.dto.response.TaskResponse;
import ru.senla.service.AbstractService;

public interface ProjectService extends AbstractService<Long, ProjectRequest, ProjectResponse> {
    Page<TaskResponse> getAllTaskRelatedToProjectByProjectId(Long projectId, Pageable pageable);
}
