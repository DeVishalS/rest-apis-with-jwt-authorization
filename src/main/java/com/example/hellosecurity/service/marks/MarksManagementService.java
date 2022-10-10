package com.example.hellosecurity.service.marks;

import com.example.hellosecurity.dto.marks.MarksDto;
import com.example.hellosecurity.model.Marks;
import com.example.hellosecurity.repository.MarksRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarksManagementService implements MarksService {

    private final MarksRepository marksRepo;

    public MarksManagementService(MarksRepository marksRepo) {
        this.marksRepo = marksRepo;
    }

    @Override
    public Marks createMarks(MarksDto marksDto){
        Marks marks = new Marks();
        BeanUtils.copyProperties(marksDto,marks);
        return marksRepo.save(marks);
    }

    @Override
    public Optional<Marks> getMarksById(Long id){
        return marksRepo.findById(id);
    }

    @Override
    public List<Marks> getAllMarsk(){
        return marksRepo.findAll();
    }

    @Override
    public Marks updateMarks(Marks oldMarks, Marks newMarks){
        BeanUtils.copyProperties(newMarks, oldMarks);
        return marksRepo.save(oldMarks);
    }

    @Override
    public void deleteMarks(Long id){
        marksRepo.deleteById(id);
    }

    @Override
    public List<Marks> getMarksByStudent(Long studentId) {
        return marksRepo.findByStudentId(studentId);
    }
}
