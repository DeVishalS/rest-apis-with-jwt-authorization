package com.example.hellosecurity.service.student;

import com.example.hellosecurity.dto.student.StudentDto;
import com.example.hellosecurity.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student createStudent(StudentDto studentDto);

    Optional<Student> getStudentById(Long id);

    List<Student> getAllStudents();

    Student updateStudent(Student oldStudent, Student newStudent);

    void deleteStudentById(Long id);

    Long getNoOfStudentsFor(Long teacherId);
}
