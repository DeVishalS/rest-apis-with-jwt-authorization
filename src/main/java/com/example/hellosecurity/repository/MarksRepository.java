package com.example.hellosecurity.repository;

import com.example.hellosecurity.model.Marks;
import com.example.hellosecurity.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarksRepository extends JpaRepository<Marks, Long> {

//    @Query("select m from Marks m where m.studentId.id = :studentId")
    public List<Marks> findByStudentId(@Param("studentId") Long studentId);
}