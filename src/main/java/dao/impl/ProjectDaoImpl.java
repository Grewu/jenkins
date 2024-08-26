package dao.impl;

import dao.api.ProjectDao;
import model.entity.Project;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProjectDaoImpl implements ProjectDao {

    private static final Map<Long, Project> database = new HashMap<>();
    private static final AtomicLong idGenerator = new AtomicLong(0);

    static {
        database.put(1L, new Project.Builder()
                .setId(1L)
                .setProjectName("Project Alpha")
                .setDescription("A project for developing Alpha features.")
                .setStartDate(LocalDateTime.of(2024, 1, 1, 0, 0))
                .setEndDate(LocalDateTime.of(2024, 12, 31, 23, 59))
                .setOwnerId(1L)
                .build());

        database.put(2L, new Project.Builder()
                .setId(2L)
                .setProjectName("Project Beta")
                .setDescription("A project for developing Beta features.")
                .setStartDate(LocalDateTime.of(2024, 2, 1, 0, 0))
                .setEndDate(LocalDateTime.of(2024, 11, 30, 23, 59))
                .setOwnerId(2L)
                .build());
    }

    @Override
    public Project create(Project project) {
        if(database.containsKey(project.getId())){
            throw new IllegalArgumentException("Key is already taken");
        }
        var newProject = new Project.Builder()
                .setId(project.getId())
                .setProjectName(project.getProjectName())
                .setDescription(project.getDescription())
                .setStartDate(project.getStartDate())
                .setEndDate(project.getEndDate())
                .setOwnerId(project.getOwnerId())
                .build();
        database.put(newProject.getId(), newProject);
        return newProject;
    }

    @Override
    public List<Project> findAll() {
        return List.copyOf(database.values());
    }

    @Override
    public Optional<Project> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Project update(Project project) {
        var existingProject = database.get(project.getId());
        if (existingProject != null) {
            var updateProject = new Project.Builder()
                    .setId(project.getId())
                    .setProjectName(project.getProjectName())
                    .setDescription(project.getDescription())
                    .setStartDate(project.getStartDate())
                    .setEndDate(project.getEndDate())
                    .setOwnerId(project.getOwnerId())
                    .build();
            database.put(updateProject.getId(), updateProject);
            return updateProject;
        }
        throw new IllegalArgumentException("Not found project " + project.getId());
    }

    @Override
    public boolean delete(Long id) {
        return database.remove(id) != null;
    }

}
