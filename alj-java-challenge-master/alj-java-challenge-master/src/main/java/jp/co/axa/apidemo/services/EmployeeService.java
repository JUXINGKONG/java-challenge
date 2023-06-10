package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.Const.UpdateEmployeeResult;
import jp.co.axa.apidemo.Dtos.EmployeeDto;
import jp.co.axa.apidemo.Dtos.GetEmployeeDto;
import jp.co.axa.apidemo.entities.Employee;

import java.util.List;

public interface EmployeeService {

    List<GetEmployeeDto> getAllEmployees();

    Employee getEmployeeById(Long employeeId);

    void saveEmployee(Employee employee);

    void saveEmployee(EmployeeDto employeeDto);

    void deleteEmployeeById(Long employeeId);

    void updateEmployee(Employee employee);

    UpdateEmployeeResult updateEmployee(Long employeeId , EmployeeDto employeeDto);
}