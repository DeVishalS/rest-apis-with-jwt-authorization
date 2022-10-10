package com.example.hellosecurity.service.student;

import com.example.hellosecurity.dto.student.StudentDto;
import com.example.hellosecurity.model.Student;
import com.example.hellosecurity.repository.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentManagementService implements StudentService {

    private final StudentRepository studentRepo;

    public StudentManagementService(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public Student createStudent(StudentDto studentDto){
        Student student = new Student();
        BeanUtils.copyProperties(studentDto,student);
        return studentRepo.save(student);
    }

    @Override
    public Optional<Student> getStudentById(Long id){
        return studentRepo.findById(id);
    }

    @Override
    public List<Student> getAllStudents(){
        return studentRepo.findAll();
    }

    @Override
    public Student updateStudent(Student oldStudent, Student newStudent){
        BeanUtils.copyProperties(newStudent, oldStudent);
        return studentRepo.save(oldStudent);
    }

    @Override
    public void deleteStudentById(Long id){
        studentRepo.deleteById(id);
    }

    @Override
    public Long getNoOfStudentsFor(Long teacherId) {
        return studentRepo.getCountOfStudentsForTeacher(teacherId);
    }

}
