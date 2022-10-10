package com.example.hellosecurity.dto.marks;

import java.time.LocalDateTime;

public record MarksDto(Long studentId, Long subjectId, LocalDateTime date, Integer marks) {
}
