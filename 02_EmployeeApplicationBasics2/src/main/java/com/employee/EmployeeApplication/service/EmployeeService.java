package com.employee.EmployeeApplication.service;

import com.employee.EmployeeApplication.entity.Address;
import com.employee.EmployeeApplication.entity.Employee;
import com.employee.EmployeeApplication.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*
 - Entity Class: Represents the data structure and table in the database.
 - Repository Class: Is the DAO layer, providing methods for data access.
 - Service Class: Contains business logic and uses repository classes to perform operations.
 */

// What's the role of Service Layer?
// Service Layer contains the buisness logic of the application.
// If performs operations such as fetching data from the DB, applying buisness rules, & processing the data before returning it to the controller.
// It's like the chef (which cooks food), send controller (waiter) , & then controller sends data to the user (waiter serves food to the customer).

// This anno. will register this class as the service layer of the project
@Service
public class EmployeeService {

    public List<Employee> employeeList = new ArrayList<>(Arrays.asList(
            new Employee(1,"Emp1","City1"),
            new Employee(2,"Emp2","City2")
    ));

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployee(int id){
        return employeeRepository.findById(id).orElseThrow(()->new RuntimeException("Not Found"));
    }

    public void createEmployee(Employee emp){

        ArrayList<Address> addressArrayList = new ArrayList<>();
        for(Address add : emp.getAddresses()){
            addressArrayList.add(new Address(add.getLine1(),add.getLine2(),add.getZipCode(),add.getCity(),add.getState(),add.getCountry(),emp));
        }
        emp.setAddresses(addressArrayList);

        // Instead of : employeeList.add(emp);
        // we will use, save method, that comes from a repository interface provided by Spring Data JPA.
        // So, instead of manually adding 'emp' obj to some list (employeelist), it uses a repository (SDJPA) to save the employee to the DB.
        employeeRepository.save(emp);
    }

    // Whatever changes we do in our application, will go away as, these changs are not persistent & are not being saved to a persistent data store such as a database.
    // We are updating the objects in-memory only
    // To ensure that the changes persist even after the application is restarted, we need to integerate our Spring Boot Application with a DB or another persistent data store like JPA
    public void updateEmployee(Employee emp){
        employeeRepository.save(emp);

    }

    public void deleteEmployee(int id){
        Employee emp = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        employeeRepository.delete(emp);
    }

}
