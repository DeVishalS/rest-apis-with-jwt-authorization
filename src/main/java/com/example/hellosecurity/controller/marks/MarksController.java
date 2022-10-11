package com.example.hellosecurity.controller.marks;

import com.example.hellosecurity.dto.marks.MarksDto;
import com.example.hellosecurity.model.Marks;
import com.example.hellosecurity.service.marks.MarksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("marks")
public class MarksController {

    private final MarksService marksService;

    public MarksController(MarksService marksService) {
        this.marksService = marksService;
    }

    @GetMapping("/student/{studentId}")
    public List<MarksDto> getMarksForAStudentId(@PathVariable("studentId") Long studentId){
            return marksService.getMarksByStudent(studentId).stream().map(MarksDto::of).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Marks> getMarksById(@PathVariable("id") Long id) {
        return marksService.getMarksById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Marks> getAllMarks() {
        return marksService.getAllMarks();
    }

    @PostMapping
    public ResponseEntity<Marks> createMarks(@RequestBody MarksDto marksDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(marksService.createMarks(marksDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMarksById(@PathVariable("id") Long id) {
        try {
            marksService.deleteMarksById(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Marks> updateMarksByGivenId(@NotNull @PathVariable("id") Long id, @RequestBody MarksDto marks) {
        Optional<Marks> oldMarksEntity = marksService.getMarksById(id);
        if (oldMarksEntity.isPresent()) {
            Marks updatedMarks = marksService.updateMarks(oldMarksEntity.get(), marks);
            return ResponseEntity.ok(updatedMarks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
