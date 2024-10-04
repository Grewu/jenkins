package dao.impl;

import dao.api.TaskDao;
import model.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TaskDaoImpl extends AbstractDaoImpl<Long, Task> implements TaskDao {


}
