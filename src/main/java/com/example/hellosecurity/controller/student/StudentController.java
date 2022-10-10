package com.example.hellosecurity.controller.student;

import com.example.hellosecurity.service.student.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("student")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("teacher/{teacherId}/count")
    public Long getCountOfStudentForTeacherId(@PathVariable("teacherId") Long teacherId){
        return studentService.getNoOfStudentsFor(teacherId);
    }
}
