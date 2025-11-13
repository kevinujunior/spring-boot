package com.spring_boot.student_management_system.service;


import com.spring_boot.student_management_system.model.Student;

import java.util.List;

//using an interface is an excellent idea
//if you wish to switch implementation just use qualifier to switch implementation no need to touch
//code inside controller
// a service interface layer offers loose coupling
public interface StudentService {
    List<Student> getAllStudents();
    Student addStudent(Student student);
    Student getStudentById(Long id);
    Student updateStudent(Student student);
    void deleteStudentById(Long id);


}
