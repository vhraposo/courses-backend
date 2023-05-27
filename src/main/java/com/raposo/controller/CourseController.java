package com.raposo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raposo.model.Course;
import com.raposo.repository.CourseRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {

   
    private final CourseRepository courseRepository;
   


    @GetMapping
    public List<Course> list(){
        return courseRepository.findAll();
    }
}
