package com.SufyanImran.Simplifi_test.service;

import com.SufyanImran.Simplifi_test.model.Subject;
import com.SufyanImran.Simplifi_test.model.Teacher;
import com.SufyanImran.Simplifi_test.model.SchoolClass;
import com.SufyanImran.Simplifi_test.service.TeacherService;
import com.SufyanImran.Simplifi_test.repository.SubjectRepository;
import com.SufyanImran.Simplifi_test.dto.SubjectDTO;
import com.SufyanImran.Simplifi_test.util.PaginationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;



@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final TeacherService teacherService;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository,  TeacherService teacherService) {
        this.subjectRepository = subjectRepository;
         this.teacherService = teacherService;
    }

    public Subject createSubjectFromDTO(SubjectDTO dto) {
        Subject subject = new Subject();
        subject.setName(dto.getName());
        return subjectRepository.save(subject);
}

    @Transactional(readOnly = true)
    public Page<Subject> getAllSubjects(Pageable pageable) {
        return subjectRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Subject> getSubjectById(Long id) {
        return subjectRepository.findById(id);
    }

    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Transactional
    public Subject assignTeachersToSubject(Long subjectId, List<Long> teacherIds) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NoSuchElementException("Subject not found with id: " + subjectId));

        Set<Teacher> teachers = teacherIds.stream()
                .map(id -> teacherService.getTeacherById(id)
                    .orElseThrow(() -> new NoSuchElementException("Teacher not found with id: " + id)))
                .collect(Collectors.toSet());

        
        subject.getTeachers().addAll(teachers);
        for (Teacher teacher : teachers) {
            teacher.getSubjects().add(subject);
        }

        return subjectRepository.save(subject);  
    }

    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }


}
