package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.Dtos.EmployeeDto;
import jp.co.axa.apidemo.Const.UpdateEmployeeResult;
import jp.co.axa.apidemo.Dtos.GetEmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<GetEmployeeDto> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<GetEmployeeDto> allEmployeeList = new ArrayList<>();
        if (!employeeList.isEmpty()) {
            for (Employee employee : employeeList) {
                GetEmployeeDto getEmployeeDto = new GetEmployeeDto(employee);
                allEmployeeList.add(getEmployeeDto);
            }
        }
        return allEmployeeList;
    }

    public Employee getEmployeeById(Long employeeId) {
        //employee id is auto-generated which cannot be negative
        if (employeeId < 0) return null;
        Optional<Employee> employeeFoundByRepository =
                employeeRepository.findById(employeeId);
        if (employeeFoundByRepository.isPresent()) {
            return employeeFoundByRepository.get();
        }
        return null;
    }

    public void saveEmployee(Employee employeeToBeSave){
        employeeRepository.save(employeeToBeSave);
    }

    @Override
    public void saveEmployee(EmployeeDto employeeDto) {
        Employee employeeToBeSave =
                new Employee(employeeDto.getName(),
                        employeeDto.getSalary(), employeeDto.getDepartment());
        saveEmployee(employeeToBeSave);
    }

    public void deleteEmployeeById(Long employeeId){
        employeeRepository.deleteById(employeeId);
    }

    public void updateEmployee(Employee employeeToBeUpdate) {
        employeeRepository.save(employeeToBeUpdate);
    }

    /**
     * Update employee if it exists.<br>
     * If cannot find employee with id of employeeId, return NOT_FOUND message.
     *
     * @see UpdateEmployeeResult
     * @param employeeId id of employee to be update
     * @param employeeDto update information
     * @return update result
     * @since 2023/06/12
     * @author JU XINGKONG
     */
    @Override
    public UpdateEmployeeResult updateEmployee(
            Long employeeId ,EmployeeDto employeeDto) {
        Employee currentEmployee = getEmployeeById(employeeId);
        if (currentEmployee != null) {
            currentEmployee.setDepartment(employeeDto.getDepartment());
            currentEmployee.setName(employeeDto.getName());
            currentEmployee.setSalary(employeeDto.getSalary());
            updateEmployee(currentEmployee);
            return UpdateEmployeeResult.SUCCESS;
        }
        return UpdateEmployeeResult.NOT_FOUND;
    }
}