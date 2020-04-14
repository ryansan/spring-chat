package com.bachelor2020.chat.service;

import com.bachelor2020.chat.model.Student;
import com.bachelor2020.chat.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public Student getStudentByStudentID(long studentID){
        return studentRepository.getStudentByStudentID(studentID);
    }

}
