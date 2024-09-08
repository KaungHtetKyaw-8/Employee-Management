package com.manage.employee.Entity;

import jakarta.persistence.*;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "employee")
@Profile("Employee")
public class Employee {

    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_sequence"
    )
    @Column(name = "empid")
    private int id;

    @Column(name = "empname")
    private String name;

    @Column(name = "empemail")
    private String email;

    @Column(name = "empposition")
    private String position;

    @Column(name = "empgrade")
    private String grade;

    @Column(name = "empdob")
    private LocalDate dob;

    @Transient
    private int age;

    public Employee() {
    }

    public Employee(int id, String name, String email, String position, String grade, LocalDate dob) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.position = position;
        this.grade = grade;
        this.dob = dob;
    }

    public Employee(String name, String email, String position, String grade, LocalDate dob) {
        this.name = name;
        this.email = email;
        this.position = position;
        this.grade = grade;
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public int getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

}
