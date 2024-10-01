package service.impl;


import annotation.Transactional;
import dao.api.EmployeeDao;
import mapper.EmployeeMapper;
import model.dto.request.EmployeeRequest;
import model.dto.response.CommentResponse;

import dao.api.EmployeeDao;
import mapper.EmployeeMapper;
import model.dto.request.EmployeeRequest;

import model.dto.response.EmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.api.EmployeeService;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao, EmployeeMapper employeeMapper) {
        this.employeeDao = employeeDao;
        this.employeeMapper = employeeMapper;
    }

    @Override
    @Transactional
    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        var employee = employeeMapper.toEmployee(employeeRequest);
        return employeeMapper.toEmployeeResponse(employeeDao.create(employee));
    }

    @Override
    public List<EmployeeResponse> getAll() {
        return employeeDao.findAll().stream()
                .map(employeeMapper::toEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse getById(Long id) {
        return employeeDao.findById(id)
                .map(employeeMapper::toEmployeeResponse)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id " + id));
    }

    @Override
    @Transactional
    public EmployeeResponse update(EmployeeRequest employeeRequest) {
        var employee = employeeMapper.toEmployee(employeeRequest);
        return employeeMapper.toEmployeeResponse(employeeDao.update(employee));
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        return employeeDao.delete(id);
    }

    @Override
    public Map<EmployeeResponse, List<CommentResponse>> getAllEmployeesWithComments() {
        return employeeMapper.toMapOfEmployeeResponse(employeeDao.getAllEmployeesWithComments());
    }

    @Override
    public Map<EmployeeResponse, List<CommentResponse>> getEmployeeCommentsByDateRange(LocalDateTime startDate,
                                                                                       LocalDateTime endDate) {
        return employeeMapper.toMapOfEmployeeResponse(employeeDao.getEmployeeCommentsByDateRange(startDate, endDate));
    }

    @Override
    public Map<EmployeeResponse, List<CommentResponse>> getEmployeeCommentsByDepartmentId(Long departmentId) {
        return employeeMapper.toMapOfEmployeeResponse(employeeDao.getEmployeeCommentsByDepartmentId(departmentId));
    }

}
