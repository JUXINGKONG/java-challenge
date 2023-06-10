package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.Dtos.EmployeeDto;
import jp.co.axa.apidemo.Const.UpdateEmployeeResult;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.Dtos.GetEmployeeDto;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Value("${employee.id.validate.failed}")
    String EMPLOYEE_ID_IS_INVALID;
    @Value("${employee.not.found}")
    String EMPLOYEE_NOT_FOUND;


    @Autowired
    private EmployeeService employeeService;

    static final String ERROR_MESSAGE_DELIMITER = "; ";

    /**
     * Try to get all employee information from DB and return all of them.<br>
     * return empty if service cannot find any.<br>
     * @return List of GetEmployDto
     * @see GetEmployeeDto
     * @since 2023/06/12
     * @author JU XINGKONG
     */
    @GetMapping("/employees")
    public @ResponseBody List<GetEmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    /**
     * Try to get employee by id received from path variable.<br>
     * return 200 and employee information if get employee from DB successfully<br>
     * return 400 if path variable is not numeric.<br>
     * return 404 if employee cannot be found in DB with id equals path variable<br>
     *
     * @see GetEmployeeDto
     * @param employeeIdString employee id
     * @return employee information founded
     * @since 2023/06/12
     * @author JU XINGKONG
     */
    @GetMapping("/employees/{employeeId}")
    public @ResponseBody GetEmployeeDto getEmployeeById(
            @PathVariable(name="employeeId") String employeeIdString) {
        try {
            Long employeeId = Long.valueOf(employeeIdString);
            Employee resultEmployee = employeeService.getEmployeeById(employeeId);
            if (resultEmployee != null)
                return new GetEmployeeDto(resultEmployee);
        } catch (NumberFormatException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, EMPLOYEE_ID_IS_INVALID);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, EMPLOYEE_NOT_FOUND);
    }

    /**
     * Try to save employee
     * return 200 if save success<br>
     * return 400 if there are field errors
     *
     * @param employee employeeDto contains employee information
     * @param errors request errors
     * @since 2023/06/12
     * @author JU XINGKONG
     */
    @PostMapping("/employees")
    public void saveEmployee(
            @RequestBody @Validated EmployeeDto employee, Errors errors){
        if (errors.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, getAllFieldErrorMessage(errors));
        }
        employeeService.saveEmployee(employee);
    }

    /**
     * Try to delete employee.
     * return 200 if delete success.<br>
     * return 400 if employee id is not numeric.<br>
     * @param employeeIdString employee id
     * @since 2023/06/12
     * @author JU XINGKONG
     */
    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(
            @PathVariable(name="employeeId")String employeeIdString){
        try {
            Long employeeId = Long.valueOf(employeeIdString);
            employeeService.deleteEmployeeById(employeeId);
        } catch (NumberFormatException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, EMPLOYEE_ID_IS_INVALID);
        }
    }

    /**
     * update employee by id.<br>
     * return 200 if update success.<br>
     * return 400 if request body has invalid field.<br>
     * return 404 if cannot find employee to be updated.<br>
     * @see UpdateEmployeeResult
     * @param employeeDto employee information
     * @param employeeIdString string of employee id
     * @param errors request errors
     * @since 2023/06/12
     * @author JU XINGKONG
     */
    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(@RequestBody EmployeeDto employeeDto,
                               @PathVariable(name="employeeId")
                               String employeeIdString,
                               Errors errors){
        if (errors.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, getAllFieldErrorMessage(errors));
        }
        try {
            Long employeeId = Long.valueOf(employeeIdString);
            UpdateEmployeeResult updateResult =
                    employeeService.updateEmployee(employeeId, employeeDto);
            if (updateResult == UpdateEmployeeResult.NOT_FOUND)
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, EMPLOYEE_NOT_FOUND);
        } catch (NumberFormatException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,EMPLOYEE_ID_IS_INVALID);
        }
    }

    /**
     * extract all field errors in request, and concat all of them.<br>
     * separated by ;
     *
     * @param errors request errors
     * @return all field error messages
     * @since 2023/06/12
     * @author JU XINGKONG
     */
    private String getAllFieldErrorMessage(Errors errors) {
        StringBuilder errorMsgBuilder = new StringBuilder();
        List<FieldError> allFieldErrors = errors.getFieldErrors();
        for (FieldError err : allFieldErrors) {
            errorMsgBuilder.append(err.getDefaultMessage())
                    .append(ERROR_MESSAGE_DELIMITER);
        }
        return errorMsgBuilder.toString();
    }
}
