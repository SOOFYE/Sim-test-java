package com.SufyanImran.Simplifi_test.service;

import com.SufyanImran.Simplifi_test.model.Teacher;
import com.SufyanImran.Simplifi_test.model.Student;
import com.SufyanImran.Simplifi_test.model.SchoolClass;
import com.SufyanImran.Simplifi_test.model.Subject;
import com.SufyanImran.Simplifi_test.repository.TeacherRepository;
import com.SufyanImran.Simplifi_test.dto.TeacherDTO;
import com.SufyanImran.Simplifi_test.util.PaginationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherService {

  private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public Teacher createTeacherFromDTO(TeacherDTO dto) {
        Teacher teacher = new Teacher();
        teacher.setFirstname(dto.getFirstname());
        teacher.setLastname(dto.getLastname());
        teacher.setEmailAddress(dto.getEmailAddress());
        teacher.setPhoneNumber(dto.getPhoneNumber());

        return teacherRepository.save(teacher);
    }

    @Transactional(readOnly = true)
    public Page<Teacher> getAllTeachers(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Student> getStudentsTaughtByTeacher(Long teacherId, Pageable pageable) {
        return teacherRepository.findById(teacherId)
                .map(teacher -> teacher.getSchoolClasses().stream()
                        .flatMap(c -> c.getStudents().stream())
                        .distinct()
                        .collect(Collectors.toList()))
                .map(students -> PaginationUtil.toPage(students, pageable))
                .orElse(Page.empty());
    }

    @Transactional(readOnly = true)
    public Page<SchoolClass> getClassesTaughtByTeacher(Long teacherId, Pageable pageable) {
        return teacherRepository.findById(teacherId)
                .map(Teacher::getSchoolClasses)
                .map(classes -> PaginationUtil.toPage(new ArrayList<>(classes), pageable))
                .orElse(Page.empty());
    }

    @Transactional(readOnly = true)
    public Page<Subject> getSubjectsTaughtByTeacher(Long teacherId, Pageable pageable) {
        return teacherRepository.findById(teacherId)
                .map(Teacher::getSubjects)
                .map(subjects -> PaginationUtil.toPage(new ArrayList<>(subjects), pageable))
                .orElse(Page.empty());
    }
}
