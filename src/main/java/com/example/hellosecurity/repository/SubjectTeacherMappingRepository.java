package com.example.hellosecurity.repository;

import com.example.hellosecurity.model.SubjectTeacherMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectTeacherMappingRepository extends JpaRepository<SubjectTeacherMapping, Long> {
}