package com.raposo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.raposo.model.Course;
import com.raposo.repository.CourseRepository;
import com.raposo.service.CourseService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {

   
    private final CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    //pegar todos os cursos
    @GetMapping
    public List<Course> list(){
        return courseService.list();
    }

    //obter por identificador
    @GetMapping("/{id}")
    public ResponseEntity <Course> findById(@PathVariable @NotNull @Positive Long id){
       return courseService.findById(id)
            .map(recordFound -> ResponseEntity.ok().body(recordFound))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody @Valid Course course){
       return courseService.create(course);
    }

    //atualizar
    @PutMapping("/{id}")
    public ResponseEntity <Course> update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid Course course){
        
        return courseService.update(id, course)
        .map(recordFound -> ResponseEntity.ok().body(recordFound))
        .orElse(ResponseEntity.notFound().build());
    }

    //deletar
    //verificar se o registro existe na base
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive  Long id){
        if(courseService.delete(id)){
        return ResponseEntity.noContent().<Void>build();
        }
        return ResponseEntity.notFound().build();
        
        
    }
    
}
