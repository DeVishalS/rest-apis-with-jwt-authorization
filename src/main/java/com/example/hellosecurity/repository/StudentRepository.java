package com.example.hellosecurity.repository;

import com.example.hellosecurity.model.Student;
import com.example.hellosecurity.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long> {

//    @Query(value = """
//            SELECT count(s.id)
//            FROM students s
//            INNER JOIN group g ON g.id = s.group_id
//            INNER JOIN subject_teacher_mapping stm ON stm.group_id = g.id
//            WHERE stm.teacher_id = :teacherId
//           """
//            , nativeQuery = true
//    )
//    public Long getCountOfStudentsFor(@Param("teacherId") Integer teacherId);

    @Query("""
            SELECT s
            FROM Student s
            JOIN Group g
            JOIN SubjectTeacherMapping stm
            WHERE stm.teacherId = :teacherId
            """)
    public Long getCountOfStudentsForTeacher(@Param("teacherId") Integer teacherId);
}