package com.example.hellosecurity.controller.teacher;

import com.example.hellosecurity.dto.teacher.TeacherDto;
import com.example.hellosecurity.model.Teacher;
import com.example.hellosecurity.service.teacher.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("teacher")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping(value = "/{id}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Teacher> getTeacherById(@PathVariable("id") Long id){
        return teacherService.getTeacherById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Teacher> getAllTeachers(){
        return teacherService.getAllTeachers();
    }

    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody TeacherDto teacherDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.createTeacher(teacherDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTeacherById(@PathVariable("id") Long id){
        try {
            teacherService.deleteTeacherById(id);
            return ResponseEntity.ok(HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacherByGivenId(@NotNull @PathVariable("id") Long id, @RequestBody TeacherDto teacherDto){
        Optional<Teacher> oldTeacherEntity = teacherService.getTeacherById(id);
        if(oldTeacherEntity.isPresent()){
            Teacher updatedTeacher = teacherService.updateTeacher(oldTeacherEntity.get(), teacherDto);
            return ResponseEntity.ok(updatedTeacher);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
