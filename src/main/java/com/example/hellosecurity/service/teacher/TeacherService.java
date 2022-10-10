package com.example.hellosecurity.service.teacher;

import com.example.hellosecurity.dto.teacher.TeacherDto;
import com.example.hellosecurity.model.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {
    Teacher createTeacher(TeacherDto teacherDto);

    Optional<Teacher> getTeacherById(Long id);

    List<Teacher> getAllTeachers();

    Teacher updateTeacher(Teacher oldTeacher, Teacher newTeacher);

    void deleteTeacherById(Long id);
}
