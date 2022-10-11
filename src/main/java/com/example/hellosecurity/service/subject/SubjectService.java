package com.example.hellosecurity.service.subject;

import com.example.hellosecurity.dto.subject.SubjectDto;
import com.example.hellosecurity.model.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectService {
    Subject createSubject(SubjectDto subjectDto);

    Optional<Subject> getSubjectById(Long id);

    List<Subject> getAllSubjects();

    Subject updateSubject(Subject oldSubject, SubjectDto newSubject);

    void deleteSubjectById(Long id);

    List<Subject> getSubjectWiseMarksFor(Long studentId);
}
