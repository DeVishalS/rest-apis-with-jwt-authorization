package com.example.hellosecurity.service.marks;

import com.example.hellosecurity.dto.marks.MarksDto;
import com.example.hellosecurity.model.Marks;

import java.util.List;
import java.util.Optional;

public interface MarksService {
    Marks createMarks(MarksDto marksDto);

    Optional<Marks> getMarksById(Long id);

    List<Marks> getAllMarsk();

    Marks updateMarks(Marks oldMarks, Marks newMarks);

    void deleteMarks(Long id);

    List<Marks> getMarksByStudent(Long studentId);
}
