package dao.impl;

import dao.api.ProjectDao;
import model.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDaoImpl extends AbstractDaoImpl<Long, Project> implements ProjectDao {


}
