package service.impl;

import dao.api.DepartmentDao;
import exception.EntityNotFoundException;
import mapper.DepartmentMapper;
import model.dto.request.DepartmentRequest;
import model.dto.response.DepartmentResponse;
import model.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.api.DepartmentService;

import java.util.List;
import java.util.stream.Collectors;

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
    @Transactional
    public DepartmentResponse create(DepartmentRequest departmentRequest) {
        var department = departmentMapper.toDepartment(departmentRequest);
        return departmentMapper.toDepartmentResponse(departmentDao.create(department));
    }

    @Override
    public List<DepartmentResponse> getAll() {
        return departmentDao.findAll().stream()
                .map(departmentMapper::toDepartmentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentResponse getById(Long id) {
        return departmentDao.findById(id)
                .map(departmentMapper::toDepartmentResponse)
                .orElseThrow(() -> new EntityNotFoundException(Department.class, id));
    }

    @Override
    @Transactional
    public DepartmentResponse update(Long id, DepartmentRequest departmentRequest) {
        var department = departmentMapper.toDepartment(departmentRequest);
        return departmentMapper.toDepartmentResponse(departmentDao.update(department));
    }

    @Override
    @Transactional
    public void delete(DepartmentRequest departmentRequest) {
        var department = departmentMapper.toDepartment(departmentRequest);
        departmentDao.delete(department);
    }
}
