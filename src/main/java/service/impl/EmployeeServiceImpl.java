package service.impl;

import dao.api.EmployeeDao;
import mapper.EmployeeMapper;
import model.dto.request.EmployeeRequest;
import model.dto.response.EmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.api.EmployeeService;

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
    public void delete(Long id) {
        employeeDao.delete(id);
    }
}
