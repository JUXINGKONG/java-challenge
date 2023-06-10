package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.Dtos.GetEmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    List<Employee> testEmployees = new ArrayList<>();
    final int TEST_EMPLOYEE_LIST_SIZE = 10;
    final String TEST_EMPLOY_NAME_PREFIX = "TEST_NAME_";
    final String TEST_EMPLOY_DEPARTMENT_PREFIX = "TEST_DEPARTMENT_";


    @BeforeEach
    void setUp() {
        for (int index = 0; index < TEST_EMPLOYEE_LIST_SIZE; index++) {
            Employee randomEmployee =
                    new Employee((long) index,
                            TEST_EMPLOY_NAME_PREFIX + index,
                            index * 100,
                            TEST_EMPLOY_DEPARTMENT_PREFIX + index);
            testEmployees.add(randomEmployee);
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void should_return_all_employees() {
        given(employeeRepository.findAll()).willReturn(testEmployees);
        List<GetEmployeeDto> allEmployee = employeeService.getAllEmployees();
        assertThat(allEmployee).isNotNull();
        assertThat(allEmployee.size()).isEqualTo(10);
    }

    @Test
    void should_return_employee_by_test_index() {
        int testIndex = 3;
        Employee expectedEmployee = Employee.builder()
                .id((long) testIndex)
                .name(TEST_EMPLOY_NAME_PREFIX + testIndex)
                .salary(testIndex * 100)
                .department(TEST_EMPLOY_DEPARTMENT_PREFIX + testIndex)
                .build();

        given(employeeRepository
                .findById((long) testIndex)).
                willReturn(Optional.ofNullable(testEmployees.get(testIndex)));
        Employee resultEmployee = employeeService.getEmployeeById((long) testIndex);

        assertThat(resultEmployee).isNotNull();
        assertThat(resultEmployee.getId()).isEqualTo(expectedEmployee.getId());
        assertThat(resultEmployee.getDepartment()).isEqualTo(expectedEmployee.getDepartment());
        assertThat(resultEmployee.getName()).isEqualTo(expectedEmployee.getName());
        assertThat(resultEmployee.getSalary()).isEqualTo(expectedEmployee.getSalary());
    }

    @Test
    void saveEmployee() {
        int testIndex = 99;
        Employee expectedEmployee = Employee.builder()
                .id((long) testIndex)
                .name(TEST_EMPLOY_NAME_PREFIX + testIndex)
                .salary(testIndex * 100)
                .department(TEST_EMPLOY_DEPARTMENT_PREFIX + testIndex)
                .build();
    }

    @Test
    void deleteEmployeeById() {
    }

    @Test
    void updateEmployee() {
    }
}