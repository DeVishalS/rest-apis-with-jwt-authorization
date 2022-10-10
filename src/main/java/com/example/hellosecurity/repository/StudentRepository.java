package com.example.hellosecurity.repository;

import com.example.hellosecurity.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = """
            SELECT count(distinct s.id)
            FROM students s
            INNER JOIN groups g ON g.id = s.group_id
            INNER JOIN subject_teacher_mapping stm ON stm.group_id = g.id
            WHERE stm.teacher_id = :teacherId
           """
            , nativeQuery = true
    )
    public Long getCountOfStudentsForTeacher(@Param("teacherId") Long teacherId);

//    @Query("""
//            SELECT COUNT(s.id)
//            FROM Student s
//            JOIN s.group g
//            JOIN
//            WHERE stm.teacher.id = :teacherId
//            """)
//  public Long getCountOfStudentsFor(@Param("teacherId") Integer teacherId);
    //TODO JPQL Syntax
}