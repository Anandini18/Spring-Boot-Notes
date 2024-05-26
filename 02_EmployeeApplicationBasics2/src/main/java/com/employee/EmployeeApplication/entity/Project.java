package com.employee.EmployeeApplication.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

// Many to Many

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String clientName;

    // This anno. tells Spring Boot to ignore that particular field when converting an obj to JSON or when creating an obj from JSON.
    // This is for preventing never ending loop
    // Most of the times, it is useful with Many-to-One & Many-to-Many
    @JsonIgnore
    // This "projects" represents the Employee Project list "projects"
    @ManyToMany(mappedBy = "projects")
    private List<Employee> employees;

    public Project(){}

    public Project(String name,String clientName){
        this.name = name;
        this.clientName = clientName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
