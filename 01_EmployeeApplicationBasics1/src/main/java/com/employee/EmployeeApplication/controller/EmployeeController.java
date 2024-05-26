package com.employee.EmployeeApplication.controller;

import com.employee.EmployeeApplication.entity.Employee;
import com.employee.EmployeeApplication.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

// What does Controller layer do?
// Controller layer is responsible for handling incoming HTTP requests, processing them & returning appropriate HTTP responses.
// It acts as a bridge b/w client (like web browser & mobile app) & the rest of the application


// This @Controller Annotation will convert this class into Controller
@Controller
// We have to use @RespondBody anno. else we will get error,
// This anno. is to indicate that the return value of a method should be written directly to the HTTP response body.
// When a method/class is annotated with this anno. , Spring converts the return value to a format suitable for the client (JSON,XML) , using mssg converters.
@ResponseBody

// We can also write @RestController, which is combination of @Controller + @RespondBody
// @RestController
public class EmployeeController {

    /*
    Autowired anno. is used when we are creating any instance of service layer
    It checks if there are interdependencies of this particular file & it injects that particular dependency over here, which means it helps in Dependency Injection.

    EmployeeController class, depends on EmployeeService, so , it needs the obj of EmployeeService also,
    So, as we have learnt in core java, we have to do as,
    EmployeeService es = new EmployeeService(), but now, we don't have to do it.
    Coz, bcz of this Autowired annotation, it automatically provides the obj of EmployeeService class to this (EmployeeController) class.
    And, this is what dependency injection is! , which is facilitated by @Autowired annotation.
    For this, we just have to declare the obj of EmployeeService class & mark it with Autowired annotation.

     */

    @Autowired
    // Instance of Service Layer
    EmployeeService employeeService;

    // We want this method to be invoked when we hit URL : "http://localhost:8080/empoyees"
    // For this, we have to map this method to that URL, & for that RequestMapping is used.
    // It's a get request, that's why we don't have to mention it, as default get request is considered.
    // But, if we have to use the post mapping , then, we have to explicitly define it.
    @RequestMapping("/employees")
    public List<Employee> findAllEmployees(){
        // Accessing the service class in controller?
        return employeeService.getAllEmployees();
    }

    // @GetMapping( "/employees/{id}")
    @RequestMapping("/employees/{id}")
    // @PathVariable anno. declares that id (in args) = {id} (in path)
     public Employee getEmployee(@PathVariable int id){
         return employeeService.getEmployee(id);
     }

    // @PostMapping( "/employees")
     @RequestMapping(value = "/employees", method = RequestMethod.POST)
     // We can also do this as, @PostMapping("/employees")
     // @RequestBody anno. binds the incoming request body to a method parameter in controller
     // It's the oppo. of @ResponseBody as, it converts body of HTTP request (JSON) into a Java obj.
    public void createEmployee(@RequestBody Employee emp){
         employeeService.createEmployee(emp);
     }

    // @PutMapping( "/employees/{id}")
     @RequestMapping(value = "/employees/{id}",method = RequestMethod.PUT)
    public void updateEmployee(@PathVariable int id,  @RequestBody Employee emp){
        employeeService.updateEmployee(emp);
     }

     @RequestMapping(value = "/employees/{id}", method = RequestMethod.DELETE)
     // @DeleteMapping( "/employees/{id}")
     public List<Employee> deleteEmployee(@PathVariable int id){
        employeeService.deleteEmployee(id);
        return employeeService.getAllEmployees();
     }
}
