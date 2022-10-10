package com.example.hellosecurity.controller.subject;

import com.example.hellosecurity.dto.marks.MarksDto;
import com.example.hellosecurity.model.Marks;
import com.example.hellosecurity.model.Subject;
import com.example.hellosecurity.service.subject.SubjectService;
import lombok.Builder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("subject")
public class SubjectController {

    private SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("marks/student/{studentId}")
    List<SubjectResponseDto> getSubjectsWithMarksFor(@PathVariable("studentId") Long studentId){

        List<Subject> subjectWiseMarksFor = subjectService.getSubjectWiseMarksFor(studentId);
        return subjectWiseMarksFor.stream().map(SubjectResponseDto::of).toList();
    }
}


@Builder
record MarksResponseDto(LocalDateTime dateTime, Integer marks){
    public static MarksResponseDto of(Marks m){
        return builder().marks(m.getMarks()).dateTime(m.getDate()).build();
    }
}
@Builder
record SubjectResponseDto(String title, List<MarksResponseDto> marks){
    public static SubjectResponseDto of(Subject s){
        return builder().title(s.getTitle()).marks(s.getMarks().stream().map(MarksResponseDto::of).toList()).build();
    }

}
