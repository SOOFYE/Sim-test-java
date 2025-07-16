package com.SufyanImran.Simplifi_test.service;

import com.SufyanImran.Simplifi_test.model.Subject;
import com.SufyanImran.Simplifi_test.model.Teacher;
import com.SufyanImran.Simplifi_test.model.SchoolClass;
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

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
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

    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public Page<SchoolClass> getClassesWithSubject(Long subjectId, Pageable pageable) {
        return subjectRepository.findById(subjectId)
                .map(Subject::getSchoolClasses)
                .map(classes -> PaginationUtil.toPage(new ArrayList<>(classes), pageable))
                .orElse(Page.empty());
    }
}
