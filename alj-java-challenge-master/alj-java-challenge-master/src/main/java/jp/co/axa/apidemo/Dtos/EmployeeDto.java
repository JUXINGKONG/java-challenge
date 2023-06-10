package jp.co.axa.apidemo.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Hold employee information except employee id.<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    @NotBlank(message = "name must not be empty!")
    @Pattern(regexp = "[a-zA-Z]+$",
            message = "name is invalid!")
    private String name;

    @Min(value = 1, message = "salary is invalid!")
    private Integer salary;

    @NotBlank(message = "department must not be empty!")
    @Size(min = 1, message = "department is invalid!")
    private String department;

}
