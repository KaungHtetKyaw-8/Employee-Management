package com.manage.employee.Services;

import com.manage.employee.Entity.Employee;
import com.manage.employee.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServices {

    private EmployeeRepository employeeRepository;
    private ZeroEmailVerificationService emailVerificationService;

    @Autowired
    public EmployeeServices(EmployeeRepository employeeRepository,ZeroEmailVerificationService emailVerificationService) {
        this.emailVerificationService = emailVerificationService;
        this.employeeRepository = employeeRepository;
    }

//  Get All Employee List
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

//  search to DB with Employee Name And Position.
    public List<Employee> getFindByNameAndPosition(String empName,String empPosition) {
        List<Employee> result = employeeRepository.findByNameLikeIgnoreCase(empName);
        if (empPosition != null && !empPosition.isEmpty()) {
            return (List<Employee>) result.stream()
                    .filter(employee -> employee.getPosition().equals(empPosition))
                    .toList();
        }else{
            return result;
        }
    }

//  search to DB with Employee Name.
    public List<Employee> getFindByName(String empName) {
        return employeeRepository.findByNameLikeIgnoreCase(empName);
    }

//  search to DB with Employee Mail.
    public Optional<Employee> getFindByMail(String empMail) {
        return employeeRepository
                .findByMailIgnoreCase(empMail);
    }

//  search to DB with Employee Position.
    public List<Employee> getFindByPosition(String empPosition) {
        return employeeRepository
                .findByPositionIgnoreCase(empPosition)
                .stream().toList();
    }

    public void addEmployee(Employee employee){

//      check the mail its exist in DB.
        Optional<Employee> employeeOptional = getFindByMail(employee.getEmail());
        if (employeeOptional.isPresent()) {
            throw new IllegalStateException("Email Has Been Taken!");
        }

//      check the mail with Zero Bound API
        boolean emailStatus = emailVerificationService.verifyEmail(employee.getEmail()).getStatus();
        if (!emailStatus) {
            throw new IllegalStateException("Your Email doesn't exist!");
        }

//      new Employee Saved to DB.
        employeeRepository.save(employee);
    }



}
