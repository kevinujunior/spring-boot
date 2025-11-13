package com.spring_boot.student_management_system.service;

import com.spring_boot.student_management_system.model.Student;
import com.spring_boot.student_management_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("dbService")
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepository studentRepository;


    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student updateStudent(Student student) { // edit and update student if exist otherwise add new
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudentById(Long id) {
        if(studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        }
        else{
            System.out.println("Value is not present in db");
        }
    }
}
