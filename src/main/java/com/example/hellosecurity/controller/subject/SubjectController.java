package com.example.hellosecurity.controller.subject;

import com.example.hellosecurity.dto.subject.SubjectResponseDto;
import com.example.hellosecurity.model.Subject;
import com.example.hellosecurity.service.subject.SubjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("subject")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("marks/student/{studentId}")
    List<SubjectResponseDto> getSubjectsWithMarksFor(@PathVariable("studentId") Long studentId){

        List<Subject> subjectWiseMarksFor = subjectService.getSubjectWiseMarksFor(studentId);
        return subjectWiseMarksFor.stream().map(SubjectResponseDto::of).toList();
    }
}