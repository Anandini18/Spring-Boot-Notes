package com.employee.EmployeeApplication;

import com.employee.EmployeeApplication.entity.Address;
import com.employee.EmployeeApplication.entity.Employee;
import com.employee.EmployeeApplication.entity.Project;
import com.employee.EmployeeApplication.entity.Spouse;
import com.employee.EmployeeApplication.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

	/*
	Printing the stuff in the console :
	@Bean
	public CommandLineRunner initialCreate(EmployeeService employeeService){
		return (args)->{
			Address address1 = new Address("Line 1","Line 2","ZipCode 2","City 1","State 1","Country 1");
			Project project1 = new Project("Name 1", "Client 1");
			Spouse spouse1 = new Spouse(1,"Name 1","Mobile 1",30);

			Employee employee = new Employee("Employee 1","City 1");
			employee.addProject(project1);
			employee.addAdresses(address1);
			employee.setSpouse(spouse1);

			employeeService.createEmployee(employee);

			System.out.println("Getting an Employee");
			Employee employee1 = employeeService.getEmployee(1);
		};
	}

	 */

}
