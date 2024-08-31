package service.impl;

import dao.api.EmployeeDao;
import mapper.EmployeeMapper;
import model.dto.request.EmployeeRequest;
import model.dto.response.CommentResponse;
import model.dto.response.EmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.api.EmployeeService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        var employee = employeeMapper.toEmployee(employeeRequest);
        return employeeMapper.toEmployeeResponse(employeeDao.create(employee));
    }

    @Override
    public List<EmployeeResponse> getAll() {
        var employees = employeeDao.findAll();
        return employeeMapper.toListOfEmployeeResponse(employees);
    }

    @Override
    public EmployeeResponse getById(Long id) {
        return employeeDao.findById(id)
                .map(employeeMapper::toEmployeeResponse)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id " + id));
    }

    @Override
    public EmployeeResponse update(EmployeeRequest employeeRequest) {
        var employee = employeeMapper.toEmployee(employeeRequest);
        return employeeMapper.toEmployeeResponse(employeeDao.update(employee));
    }

    @Override
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
