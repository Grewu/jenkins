package service.impl;

import dao.api.DepartmentDao;
import mapper.DepartmentMapper;
import model.dto.request.DepartmentRequest;
import model.dto.response.DepartmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.api.DepartmentService;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentDao departmentDao;
    private final DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentDao departmentDao, DepartmentMapper departmentMapper) {
        this.departmentDao = departmentDao;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public DepartmentResponse create(DepartmentRequest departmentRequest) {
        var department = departmentMapper.toDepartment(departmentRequest);
        return departmentMapper.toDepartmentResponse(departmentDao.create(department));
    }

    @Override
    public List<DepartmentResponse> getAll() {
        var department = departmentDao.findAll();
        return departmentMapper.toListOfDepartmentResponse(department);
    }

    @Override
    public DepartmentResponse getById(Long id) {
        return departmentDao.findById(id)
                .map(departmentMapper::toDepartmentResponse)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with id " + id));
    }

    @Override
    public DepartmentResponse update(DepartmentRequest departmentRequest) {
        var comment = departmentMapper.toDepartment(departmentRequest);
        return departmentMapper.toDepartmentResponse(departmentDao.update(comment));
    }

    @Override
    public boolean delete(Long id) {
        return departmentDao.delete(id);
}
}
