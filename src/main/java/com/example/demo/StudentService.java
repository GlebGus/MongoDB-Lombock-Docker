package com.example.demo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class StudentService {
private final StudentRepository repository;
    public List<Student> getAllStudents() {
        return repository.findAll();
    }
}
