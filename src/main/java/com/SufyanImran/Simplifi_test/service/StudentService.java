package com.SufyanImran.Simplifi_test.service;

import com.SufyanImran.Simplifi_test.model.Student;
import com.SufyanImran.Simplifi_test.model.Teacher;
import com.SufyanImran.Simplifi_test.model.SchoolClass;
import com.SufyanImran.Simplifi_test.repository.StudentRepository;
import com.SufyanImran.Simplifi_test.dto.StudentDTO;
import com.SufyanImran.Simplifi_test.util.PaginationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentService {

    private final StudentRepository studentRepository;


    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudentFromDTO(StudentDTO dto) {
    Student student = new Student();
    student.setFirstname(dto.getFirstname());
    student.setLastname(dto.getLastname());
    student.setEmailAddress(dto.getEmailAddress());
    student.setPhoneNumber(dto.getPhoneNumber());

    return studentRepository.save(student);
}

    @Transactional(readOnly = true)
    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Teacher> getTeachersOfStudent(Long studentId, Pageable pageable) {
        return studentRepository.findById(studentId)
                .map(student -> {
                    SchoolClass schoolClass = student.getSchoolClass();
                    List<Teacher> list = schoolClass != null
                            ? new ArrayList<>(schoolClass.getTeachers())
                            : List.of();
                    return PaginationUtil.toPage(list, pageable);
                })
                .orElse(Page.empty());
    }

    @Transactional(readOnly = true)
    public Page<SchoolClass> getClassesOfStudent(Long studentId, Pageable pageable) {
        return studentRepository.findById(studentId)
                .map(student -> {
                    SchoolClass schoolClass = student.getSchoolClass();
                    List<SchoolClass> list = schoolClass != null
                            ? List.of(schoolClass)
                            : List.of();
                    return PaginationUtil.toPage(list, pageable);
                })
                .orElse(Page.empty());
    }
}
