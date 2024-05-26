package com.employee.EmployeeApplication.service;

import com.employee.EmployeeApplication.entity.Employee;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<Employee> getAllEmployees(){
        return employeeList;
    }

    public Employee getEmployee(int id){
        return employeeList.stream().filter(e->(
                e.getEmpId() == id)).findFirst().get();
    }

    public void createEmployee(Employee emp){
        employeeList.add(emp);
    }

    // Whatever changes we do in our application, will go away as, these changs are not persistent & are not being saved to a persistent data store such as a database.
    // We are updating the objects in-memory only
    // To ensure that the changes persist even after the application is restarted, we need to integerate our Spring Boot Application with a DB or another persistent data store like JPA
    public void updateEmployee(Employee emp){
        if(emp==null){
            System.out.println("Error : Employee object is null");
            return;
        }
        Employee e = employeeList.stream().filter(empl->(
                empl.getEmpId() == emp.getEmpId()
                )).findFirst().get();
        e.setEmpName(emp.getEmpName());
        e.setEmpCity(emp.getEmpCity());

        /*
        We can also do it as,
        List<Employee> tempList = new ArrayList<>();
        for(Employee e : employeeList){
            if(e.getEmpId() == emp.getEmpId()){
                e.setEmpName(emp.getEmpName());
                e.setEmpCity(emp.getEmpCity());
            }
            tempList.add(e);
        }
        this.employeeList = tempList;

         */

    }

    public void deleteEmployee(int id){
        Employee e = employeeList.stream().filter(emp->(
                emp.getEmpId()==id
                )).findFirst().get();
        employeeList.remove(e);
    }

}
