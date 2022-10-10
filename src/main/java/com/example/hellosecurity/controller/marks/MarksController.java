package com.example.hellosecurity.controller.marks;

import com.example.hellosecurity.model.Marks;
import com.example.hellosecurity.service.marks.MarksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("marks")
public class MarksController {

    private MarksService marksService;

    public MarksController(MarksService marksService) {
        this.marksService = marksService;
    }

    @GetMapping("student/{studentId}")
    public List<Marks> getMarksForAStudentId(@PathVariable("studentId") Long studentId){
            return marksService.getMarksByStudent(studentId);
    }


}
