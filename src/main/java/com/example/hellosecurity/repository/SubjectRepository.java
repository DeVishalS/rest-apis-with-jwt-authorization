package com.example.hellosecurity.repository;

import com.example.hellosecurity.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query(
            value = """
                SELECT s,m
                FROM Subject s
                JOIN Marks m
                WHERE m.subjectId = s.id AND m.studentId = :studentId
            """, nativeQuery = false
    )
    public List<Subject> findMarksForEachSubjectFor(@Param("studentId") Integer studentId);

//    public List<Subject> findByMarks();
}
