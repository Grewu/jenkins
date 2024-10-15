package ru.senla.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.ProjectMapper;
import ru.senla.model.dto.request.ProjectRequest;
import ru.senla.model.dto.response.ProjectResponse;
import ru.senla.model.entity.Project;
import ru.senla.model.entity.UserProfile;
import ru.senla.repository.api.ProjectRepository;
import ru.senla.service.api.ProjectService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    @Transactional
    public ProjectResponse create(ProjectRequest projectRequest) {
        var project = projectMapper.toProject(projectRequest);
        return projectMapper.toProjectResponse(projectRepository.save(project));
    }

    @Override
    public Page<ProjectResponse> getAll(Pageable pageable) {
        return projectRepository.findAll(pageable)
                .map(projectMapper::toProjectResponse);
    }

    @Override
    public ProjectResponse getById(Long id) {
        return projectRepository.findById(id)
                .map(projectMapper::toProjectResponse)
                .orElseThrow(() -> new EntityNotFoundException(Project.class, id));
    }

    @Override
    @Transactional
    public ProjectResponse update(Long id, ProjectRequest projectRequest) {
        return projectRepository.findById(id)
                .map(current -> projectMapper.update(projectRequest, current))
                .map(projectRepository::save)
                .map(projectMapper::toProjectResponse)
                .orElseThrow(() -> new EntityNotFoundException(UserProfile.class, id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }
}
