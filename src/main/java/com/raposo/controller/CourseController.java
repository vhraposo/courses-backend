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

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {

    private final CourseRepository courseRepository;

    //pegar todos os cursos
    @GetMapping
    public List<Course> list(){
        return courseRepository.findAll();
    }

    //obter por identificador
    @GetMapping("/{id}")
    public ResponseEntity <Course> findById(@PathVariable @NotNull @Positive Long id){
       return courseRepository.findById(id)
            .map(recordFound -> ResponseEntity.ok().body(recordFound))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody @Valid Course course){
       return courseRepository.save(course);
    }

    //atualizar
    @PutMapping("/{id}")
    public ResponseEntity <Course> update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid Course course){
        
        return courseRepository.findById(id)
        .map(recordFound ->{
            recordFound.setName(course.getName());
            recordFound.setCategory(course.getCategory());;
            Course updated = courseRepository.save(recordFound);
            return ResponseEntity.ok().body(updated);
        })
        .orElse(ResponseEntity.notFound().build());
    }

    //deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive  Long id){
        return courseRepository.findById(id)
        //verificar se o registro existe na base
        .map(recordFound ->{
            courseRepository.deleteById(id);
            return ResponseEntity.noContent().<Void>build();
        })
        .orElse(ResponseEntity.notFound().build());
    }
    
}
