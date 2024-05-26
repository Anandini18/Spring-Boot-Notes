package com.employee.EmployeeApplication.repository;

/*

Repository Layer :

- Repository Layer is responsible for interacting with the DB.
- It provides CRUD operations & query methods to manage data.
- It provides data access services to the service layer.
- This layer is implemented using Spring Data JPA.

Spring Data JPA :

- It is a framework, that helps us to manage DB interactions in Spring application.
- Becoz of this, we don't have to write SQL queries for common DB operations.
- SDJPA provides direct methods for performing operations like save,update,delete & find.
- We can define simple interfaces that extend Spring Data JPA's repository, & SDJPA will automatically provies implementations for standard CRUD operations.

 */

import com.employee.EmployeeApplication.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// EmployeeRepository interface extends JpaRepository which is a Spring Data JPA interface.
// JpaRepository takes 2 parameters :
// Employee : Type of the entity that repository will manage. Employee class must be an entity class with @Entity , representing a table in DB.
// Integer : Type of primary key (ID) of the Employee entity.
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {


}
