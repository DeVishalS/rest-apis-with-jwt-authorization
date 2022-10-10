package com.example.hellosecurity.service.teacher;

import com.example.hellosecurity.dto.teacher.TeacherDto;
import com.example.hellosecurity.model.Teacher;
import com.example.hellosecurity.repository.TeacherRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherManagementService implements TeacherService {

    private final TeacherRepository teacherRepo;

    public TeacherManagementService(TeacherRepository teacherRepo) {
        this.teacherRepo = teacherRepo;
    }

    @Override
    public Teacher createTeacher(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherDto, teacher);
        return teacherRepo.save(teacher);
    }

    @Override
    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepo.findById(id);
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepo.findAll();
    }

    @Override
    public Teacher updateTeacher(Teacher oldTeacher, Teacher newTeacher) {
        BeanUtils.copyProperties(newTeacher, oldTeacher);
        return teacherRepo.save(oldTeacher);
    }

    @Override
    public void deleteTeacherById(Long id) {
        teacherRepo.deleteById(id);
    }
}
