package com.example.hellosecurity.dto.marks;

import com.example.hellosecurity.dto.student.StudentDto;
import com.example.hellosecurity.dto.subject.SubjectDto;
import com.example.hellosecurity.model.Marks;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MarksDto(StudentDto student, SubjectDto subject, LocalDateTime date, Integer marks) {

    public static MarksDto of(Marks marks){
        return builder()
                .marks(marks.getMarks())
                .date(marks.getDate())
                .subject(SubjectDto.of(marks.getSubject()))
                .student(StudentDto.of(marks.getStudent()))
                .build();
    }
}
