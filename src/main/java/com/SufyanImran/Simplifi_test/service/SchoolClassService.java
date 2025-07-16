package com.SufyanImran.Simplifi_test.service;

import com.SufyanImran.Simplifi_test.model.SchoolClass;
import com.SufyanImran.Simplifi_test.model.Student;
import com.SufyanImran.Simplifi_test.model.Teacher;
import com.SufyanImran.Simplifi_test.model.Subject;
import com.SufyanImran.Simplifi_test.service.SubjectService;
import com.SufyanImran.Simplifi_test.service.TeacherService;
import com.SufyanImran.Simplifi_test.service.StudentService;
import com.SufyanImran.Simplifi_test.repository.SchoolClassRepository;
import com.SufyanImran.Simplifi_test.dto.SchoolClassDTO;
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
import java.util.stream.Collectors;
import java.util.NoSuchElementException;
import java.util.HashSet;


@Service
public class SchoolClassService {

    private final SchoolClassRepository classRepository;
    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final StudentService studentService;

   @Autowired
    public SchoolClassService(SchoolClassRepository classRepository,SubjectService subjectService,TeacherService teacherService, StudentService studentService) {
        this.classRepository = classRepository;
        this.subjectService = subjectService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }
    
    
    public SchoolClass createClassFromDTO(SchoolClassDTO dto) {
    SchoolClass schoolClass = new SchoolClass();
    schoolClass.setName(dto.getName());

    
    return classRepository.save(schoolClass);
}

    @Transactional
    public SchoolClass addStudentsToClass(Long classId, List<Long> studentIds) {
        SchoolClass schoolClass = classRepository.findById(classId)
                .orElseThrow(() -> new NoSuchElementException("Class not found"));

        Set<Student> newStudents = studentIds.stream()
                .map(studentService::getStudentById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .peek(s -> s.setSchoolClass(schoolClass))
                .collect(Collectors.toSet());

        schoolClass.getStudents().addAll(newStudents);
        return classRepository.save(schoolClass);
    }

@Transactional
public SchoolClass addTeachersToClass(Long classId, List<Long> teacherIds) {
    SchoolClass schoolClass = classRepository.findById(classId)
            .orElseThrow(() -> new NoSuchElementException("Class not found"));

    Set<Teacher> teachers = teacherIds.stream()
            .map(teacherService::getTeacherById)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet());

   
    schoolClass.getTeachers().addAll(teachers);
    for (Teacher teacher : teachers) {
        if (teacher.getSchoolClasses() == null) {
            teacher.setSchoolClasses(new HashSet<>());
        }
        teacher.getSchoolClasses().add(schoolClass);
    }

    return classRepository.save(schoolClass);
}

@Transactional
public SchoolClass addSubjectsToClass(Long classId, List<Long> subjectIds) {
    SchoolClass schoolClass = classRepository.findById(classId)
            .orElseThrow(() -> new NoSuchElementException("Class not found with id: " + classId));

    Set<Subject> subjects = subjectIds.stream()
            .map(subjectService::getSubjectById)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet());

    if (subjects.isEmpty()) {
        throw new IllegalArgumentException("No valid subjects found for IDs: " + subjectIds);
    }


    if (schoolClass.getSubjects() == null) {
        schoolClass.setSubjects(new HashSet<>());
    }

    schoolClass.getSubjects().addAll(subjects);
    return classRepository.save(schoolClass);
}


    @Transactional(readOnly = true)
    public Page<SchoolClass> getAllClasses(Pageable pageable) {
        return classRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<SchoolClass> getClassById(Long id) {
        return classRepository.findById(id);
    }

    public SchoolClass saveSchoolClass(SchoolClass schoolClass) {
        return classRepository.save(schoolClass);
    }

    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Student> getStudentsInClass(Long classId, Pageable pageable) {
        return classRepository.findById(classId)
                .map(SchoolClass::getStudents)
                .map(students -> PaginationUtil.toPage(new ArrayList<>(students), pageable))
                .orElse(Page.empty());
    }

    @Transactional(readOnly = true)
    public Page<Teacher> getTeachersInClass(Long classId, Pageable pageable) {
        return classRepository.findById(classId)
                .map(SchoolClass::getTeachers)
                .map(teachers -> PaginationUtil.toPage(new ArrayList<>(teachers), pageable))
                .orElse(Page.empty());
    }
}