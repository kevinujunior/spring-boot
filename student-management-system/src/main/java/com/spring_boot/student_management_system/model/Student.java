package com.spring_boot.student_management_system.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity //Marks a class as a JPA entity — meaning it maps to a database table.
@Table(name = "student") //Provides additional details about the database table that @Entity maps to like name etc.
//@Data// getter setter equals hashcode toString
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String firstName;
    private String lastName;
    private String email;

    public Student(){

    }

    public Student(Long id, String email, String lastName, String firstName) {
        Id = id;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
