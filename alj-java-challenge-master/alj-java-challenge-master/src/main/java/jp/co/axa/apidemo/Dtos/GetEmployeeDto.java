package jp.co.axa.apidemo.Dtos;


import jp.co.axa.apidemo.entities.Employee;
import lombok.Data;

@Data
public class GetEmployeeDto extends EmployeeStandardResponse {
    public GetEmployeeDto(Employee employee) {
        this.setEmployeeId(employee.getId());
        this.setEmployeeDepartment(employee.getDepartment());
        this.setEmployeeSalary(employee.getSalary());
        this.setEmployName(employee.getName());
    }
}
