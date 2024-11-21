package service.impl;

import dao.api.ProjectDao;
import exception.EntityNotFoundException;
import mapper.ProjectMapper;
import model.dto.request.ProjectRequest;
import model.dto.response.ProjectResponse;
import model.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.api.ProjectService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectDao projectDao;
    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectServiceImpl(ProjectDao projectDao, ProjectMapper projectMapper) {
        this.projectDao = projectDao;
        this.projectMapper = projectMapper;
    }

    @Override
    @Transactional
    public ProjectResponse create(ProjectRequest projectRequest) {
        var project = projectMapper.toProject(projectRequest);
        return projectMapper.toProjectResponse(projectDao.create(project));
    }

    @Override
    public List<ProjectResponse> getAll() {
        return projectDao.findAll().stream()
                .map(projectMapper::toProjectResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponse getById(Long id) {
        return projectDao.findById(id)
                .map(projectMapper::toProjectResponse)
                .orElseThrow(() -> new EntityNotFoundException(Project.class, id));
    }

    @Override
    @Transactional
    public ProjectResponse update(Long id, ProjectRequest projectRequest) {
        var project = projectMapper.toProject(projectRequest);
        return projectMapper.toProjectResponse(projectDao.update(project));
    }

    @Override
    @Transactional
    public void delete(ProjectRequest projectRequest) {
        var project = projectMapper.toProject(projectRequest);
        projectDao.delete(project);
    }
}
