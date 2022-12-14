package com.example.hellosecurity.service.marks;

import com.example.hellosecurity.dto.marks.MarksDto;
import com.example.hellosecurity.model.Marks;

import java.util.List;
import java.util.Optional;

public interface MarksService {
    Marks createMarks(MarksDto marksDto);

    Optional<Marks> getMarksById(Long id);

    List<Marks> getAllMarks();

    Marks updateMarks(Marks oldMarks, MarksDto newMarks);

    void deleteMarksById(Long id);

    List<Marks> getMarksByStudent(Long studentId);
}
