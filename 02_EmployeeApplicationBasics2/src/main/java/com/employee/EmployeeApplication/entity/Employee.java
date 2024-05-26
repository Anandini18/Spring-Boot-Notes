package com.employee.EmployeeApplication.entity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/*
Entity Layer :

- An entity class represents a table in the DB.
- Each instance of the entity class corresponds to a row in the table.
- It defines the structure of the data & the relationships b/w tables.
- It is used by Spring Data JPA to map bw the DB & the Java application.

 */

@Entity
public class Employee {

    // Columns of the entity table will be these properties
    // @Id anno. will be given to the primary key of the table, so that it can refer to the primary key type in JpaRepository<EntityType,PKType>
    @Id
    // This anno. will autogenerate the id , as when Ist employee is created , it will set id = 1, then 2,3 & so on.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int empId;
    String empName;
    String empCity;

    // This anno. will provide one to one mapping b/w Employee & Spouse
    // It will generate a column spouse, for each row of the Employee table.
    /*
    Cascading :
    - If we delete "Employee" then, existence of Employee_Spouse & Employee_Addresses in DB wil make no sense
    - That's where we use Cascading, which tells that whatever operations we perform on "Employee", must be performed also with their related classes (Spouse & Addresses)
    - 3 types :
      - All : Applies all cascade operations to the associated entities.
      - Persist : When parent entity (Employee) is persisted (saved), associated child entities (Spouse & Addresses) are also persisted.
      - Remove : When the parent entity is removed, child associated entities are also removed.
     */
    @OneToOne(cascade = CascadeType.ALL)
    // This anno. is used to specify the foreign key col in the "Employee table" that references to the "Spouse" table.
    @JoinColumn(name = "fk_column")
    private Spouse spouse;

    /*
    Fetch : Fetch referes to how related data is loaded from the DB.
    2 types :
      - Eager Fetching : Means when we load a parent Entity (Employee) from the data , then all of its related data (address, project etc) is also loaded at the same time.
      - Lazy Fetching : Means that related data is not needed untill we actually need it. When we first load an entity , only that entity (Employee) is loaded & the related data is fetched later, when we try to acess it.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Address> addresses;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_project",
            // joinColumns attribute defines the foreign key column(s) for the owning side of the relationship, which is the Employee entity in this case. Here, @JoinColumn(name = "fk_employee") indicates that the foreign key column in the join table referring to the Employee entity will be named fk_employee.
            joinColumns = @JoinColumn(name = "fk_employee"),
            // inverseJoinColumns attribute specifies which column in the join table represents projects. Here, it's named "fk_project".
            inverseJoinColumns = @JoinColumn(name = "fk_project"))
    private List<Project> projects;

    // It's necessary to create a default constructor , w/o this the JPA retreiving methods will not work properly as,
    // In SDJPA, when we retrieve entities frm the db, it instantiates objects using reflection & the default constructor.
    // If default constructor is missing, it won't be able tto instantiate objs of our "Employee" class, leading to errors.
    Employee(){
    }

    public Employee(String empName,String empCity){
        this.empName = empName;
        this.empCity = empCity;
    }

    public Employee(int empId, String empName, String empCity) {
        this.empId = empId;
        this.empName = empName;
        this.empCity = empCity;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpCity() {
        return empCity;
    }

    public void setEmpCity(String empCity) {
        this.empCity = empCity;
    }

    public Spouse getSpouse() {
        return spouse;
    }

    public void setSpouse(Spouse spouse) {
        this.spouse = spouse;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void removeProject(Project project){
        this.projects.remove(project);
        project.getEmployees().remove(project);
    }

    public void addProject(Project project){
        this.projects.add(project);
        project.getEmployees().add(this);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void addAdresses(Address add){
        this.addresses = new ArrayList<>();
        this.addresses.add(add);
        add.setEmployee(this);
    }

    public void removeAddress(Address add){
        this.addresses.remove(add);
        add.setEmployee(null);
    }
}
