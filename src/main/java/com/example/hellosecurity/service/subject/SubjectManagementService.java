package com.example.hellosecurity.service.subject;

import com.example.hellosecurity.dto.subject.SubjectDto;
import com.example.hellosecurity.model.Subject;
import com.example.hellosecurity.repository.SubjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectManagementService implements SubjectService {

    private final SubjectRepository subjectRepo;

    public SubjectManagementService(SubjectRepository subjectRepo) {
        this.subjectRepo = subjectRepo;
    }

    @Override
    public Subject createSubject(SubjectDto subjectDto){
        Subject subject = new Subject();
        BeanUtils.copyProperties(subjectDto,subject);
        return subjectRepo.save(subject);
    }

    @Override
    public Optional<Subject> getSubjectById(Long id){
        return subjectRepo.findById(id);
    }

    @Override
    public List<Subject> getAllSubjects(){
        return subjectRepo.findAll();
    }

    @Override
    public Subject updateSubject(Subject oldSubject, Subject newSubject){
        BeanUtils.copyProperties(newSubject, oldSubject);
        return subjectRepo.save(oldSubject);
    }

    @Override
    public void deleteSubjectById(Long id){
        subjectRepo.deleteById(id);
    }

    @Override
    public List<Subject> getSubjectWiseMarksFor(Long studentId) {
        return this.subjectRepo.findMarksForEachSubjectFor(studentId);
    }
}
