package jp.co.axa.apidemo;

import jp.co.axa.apidemo.controllers.EmployeeController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class ApiDemoApplicationTests {
	@Autowired
	private EmployeeController employeeController;

	@Test
	public void contextLoads() {
		assertThat(employeeController).isNotNull();
	}


}
