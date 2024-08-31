package service.impl;

import dao.api.ProjectDao;
import mapper.ProjectMapper;
import model.dto.request.ProjectRequest;
import model.dto.response.ProjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.api.ProjectService;

import java.util.List;

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
    public ProjectResponse create(ProjectRequest projectRequest) {
        var project = projectMapper.toProject(projectRequest);
        return projectMapper.toProjectResponse(projectDao.create(project));
    }

    @Override
    public List<ProjectResponse> getAll() {
        return projectMapper.toListOfProjectResponse(projectDao.findAll());
    }

    @Override
    public ProjectResponse getById(Long id) {
        return projectDao.findById(id)
                .map(projectMapper::toProjectResponse)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id " + id));
    }

    @Override
    public ProjectResponse update(ProjectRequest projectRequest) {
        var project = projectMapper.toProject(projectRequest);
        return projectMapper.toProjectResponse(projectDao.update(project));
    }

    @Override
    public boolean delete(Long id) {
        return projectDao.delete(id);
    }
}
