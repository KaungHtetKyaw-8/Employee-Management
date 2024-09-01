package com.manage.employee.Controller;


import com.manage.employee.Entity.Employee;
import com.manage.employee.Services.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/employee")
public class EmployeeController {

    private EmployeeServices employeeServices;

    @Autowired
    public EmployeeController(EmployeeServices employeeServices) {
        this.employeeServices = employeeServices;
    }

    @GetMapping()
    public List<Employee> getEmployee(){
        return employeeServices.getAllEmployee();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> getEmployeeByName(
            @RequestParam String name,
            @RequestParam(required = false) String position){
        return ResponseEntity.ok(employeeServices.getFindByNameAndPosition(name,position));
    }



//    @GetMapping("/search/email")
//    public Employee getEmployeeByEmail(@RequestParam String email){
//        return employeeServices.getFindByMail(email);
//    }
//
//    @GetMapping("/search/position")
//    public List<Employee> getEmployeeByPosition(@RequestParam String position){
//        return employeeServices.getFindByPosition(position);
//    }


}
