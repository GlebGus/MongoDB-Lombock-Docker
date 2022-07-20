package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.List;

@SpringBootApplication
public class demoApplication {
    public static void main(String[] args) {
        SpringApplication.run(demoApplication.class,args);
    }
    @Bean
    CommandLineRunner runner(
            StudentRepository repository, MongoTemplate mongoTemplate ){
        return args -> {
            String email = "jakejacobs@gmail.com";
            Address address = new Address("England", "London", "NE9");
          Student student = new Student(
                  "Jake",
                  "Jacobs",
                  "jakejacobs@gmail.com",
                  Gender.FEMALE,
                  address,
                  List.of("Computer Science","Maths"),
                  BigDecimal.TEN,
                  LocalDateTime.now()
          );
       //     usingMongoTemplateAndQuery(repository, mongoTemplate, email, student);
repository.findStudentByEmail(email)
        .ifPresentOrElse(student1 -> {
            System.out.println(student + "already exists");
        },()->{
            System.out.println("Inserting student " + student);
            repository.insert(student);
        });
        };
    }

    private void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate mongoTemplate, String email, Student student) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        List<Student> students = mongoTemplate.find(query, Student.class);
        if (students.size()> 1){
            throw new IllegalStateException("found many students with email " + email);
        }
        if (students.isEmpty()){
            System.out.println("Inserting student " + student);
            repository.insert(student);
        }else {
            System.out.println(student + "already exists");
        }
    }
}
