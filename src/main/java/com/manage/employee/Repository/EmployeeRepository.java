package com.manage.employee.Repository;

import com.manage.employee.Entity.Employee;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository @Profile("Employee")
public interface EmployeeRepository
        extends JpaRepository<Employee,Long> {

//    @Query(value = "SELECT e FROM employee e WHERE e.name = ?1")
//    List<Employee> findByNameIgnoreCase(String empname);

    @Query(value = "SELECT * FROM employee WHERE empname like ?1%", nativeQuery = true)
    List<Employee> findByNameLikeIgnoreCase(String empname);

    @Query(value = "SELECT * FROM employee WHERE empemail = ?1", nativeQuery = true)
    Optional<Employee> findByMailIgnoreCase(String emp_email);

    @Query(value = "SELECT * FROM employee WHERE empposition = ?1", nativeQuery = true)
    List<Employee> findByPositionIgnoreCase(String emp_position);

}
