package com.example.hellosecurity.controller.student;

import com.example.hellosecurity.dto.student.StudentDto;
import com.example.hellosecurity.model.Student;
import com.example.hellosecurity.service.student.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "teacher/{teacherId}/count", produces = APPLICATION_JSON_VALUE)
    public Long getCountOfStudentForTeacherId(@PathVariable("teacherId") Long teacherId) {
        return studentService.getNoOfStudentsFor(teacherId);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody StudentDto studentDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(studentDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStudentById(@PathVariable("id") Long id) {
        try {
            studentService.deleteStudentById(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Student> updateStudentByGivenId(@NotNull @PathVariable("id") Long id, @RequestBody StudentDto student) {
        Optional<Student> oldStudentEntity = studentService.getStudentById(id);
        if (oldStudentEntity.isPresent()) {
            Student updatedStudent = studentService.updateStudent(oldStudentEntity.get(), student);
            return ResponseEntity.ok(updatedStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
