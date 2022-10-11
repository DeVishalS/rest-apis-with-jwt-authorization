package com.example.hellosecurity.dto.subject;

import com.example.hellosecurity.model.Subject;
import lombok.Builder;

@Builder
public record SubjectDto(String title) {

    public static SubjectDto of(Subject subject){
        return builder().title(subject.getTitle()).build();
    }
}
