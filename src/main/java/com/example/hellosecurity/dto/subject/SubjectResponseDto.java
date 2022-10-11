package com.example.hellosecurity.dto.subject;

import com.example.hellosecurity.dto.marks.MarksResponseDto;
import com.example.hellosecurity.model.Subject;
import lombok.Builder;

import java.util.List;

@Builder
public record SubjectResponseDto(String title, List<MarksResponseDto> marks) {
    public static SubjectResponseDto of(Subject s) {
        return builder().title(s.getTitle()).marks(s.getMarks().stream().map(MarksResponseDto::of).toList()).build();
    }

}
