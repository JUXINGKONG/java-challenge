package jp.co.axa.apidemo.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeStandardResponse {
    private Long employeeId;
    private String employName;
    private int employeeSalary;
    private String employeeDepartment;
}
