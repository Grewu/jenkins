package dao.impl;

import dao.api.DepartmentDao;
import model.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentDaoImpl extends AbstractDaoImpl<Long, Department> implements DepartmentDao {


}
