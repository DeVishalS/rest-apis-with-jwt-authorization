package com.example.hellosecurity.dto.marks;

import com.example.hellosecurity.model.Marks;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MarksResponseDto(LocalDateTime dateTime, Integer marks) {
    public static MarksResponseDto of(Marks m) {
        return builder().marks(m.getMarks()).dateTime(m.getDate()).build();
    }
}
