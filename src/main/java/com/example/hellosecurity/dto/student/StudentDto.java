package com.example.hellosecurity.dto.student;

import com.example.hellosecurity.model.Student;
import lombok.Builder;

@Builder
public record StudentDto(String firstName, String lastName, Long groupId) {

    public static StudentDto of(Student student){
        return builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .groupId(student.getGroup().getId())
                .build();
    }
}
