package com.employee.EmployeeApplication.entity;
import jakarta.persistence.*;
// One to One Mapping
// Each Employee will have 1 spouse name



@Entity
@Table(name = "Employee_Spouse")
// Table name will be the class name.
// If we want to change the name of the table then, we can use @Table enitity
public class Spouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String mobileNo;
    private int age;

    // mappedBy is used for bidirectional Mapping bw 2 entitites.
    // This bidirectional mapping allows navigation from an Employee to its Spouse and vice versa.
    // In this case, the spouse field in the Employee entity is mapped to the relationship.
    // This means that the Employee entity is the owner of the relationship, and the mapping information is stored in the spouse field.
    // Now, "Employee" entity has a reference to its "Spouse" entity through "spouse" field.
    @OneToOne(mappedBy = "spouse")
    private Employee employee;

    Spouse(){
    }

    public Spouse(int id, String name, String mobileNo, int age) {
        this.id = id;
        this.name = name;
        this.mobileNo = mobileNo;
        this.age = age;
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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
