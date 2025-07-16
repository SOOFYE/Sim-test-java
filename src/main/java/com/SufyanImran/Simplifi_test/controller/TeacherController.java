package com.SufyanImran.Simplifi_test.controller;

import com.SufyanImran.Simplifi_test.dto.TeacherDTO;
import com.SufyanImran.Simplifi_test.model.*;
import com.SufyanImran.Simplifi_test.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teachers")
@Tag(name = "Teachers", description = "Operations related to teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final SchoolClassService classService;
    private final SubjectService subjectService;

    @Autowired
    public TeacherController(
            TeacherService teacherService,
            SchoolClassService classService,
            SubjectService subjectService) {
        this.teacherService = teacherService;
        this.classService = classService;
        this.subjectService = subjectService;
    }

    @GetMapping
    @Operation(summary = "Get all teachers (paginated)")
    public ResponseEntity<Page<Teacher>> getAllTeachers(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(teacherService.getAllTeachers(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get teacher by ID")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new teacher")
    public ResponseEntity<Teacher> createTeacher(@Valid @RequestBody TeacherDTO dto) {
        Teacher saved = teacherService.createTeacherFromDTO(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete teacher by ID")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/students")
    @Operation(summary = "Get students taught by the teacher")
    public ResponseEntity<Page<Student>> getStudentsTaughtByTeacher(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(teacherService.getStudentsTaughtByTeacher(id, pageable));
    }

    @GetMapping("/{id}/classes")
    @Operation(summary = "Get classes taught by the teacher")
    public ResponseEntity<Page<SchoolClass>> getClassesTaughtByTeacher(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(teacherService.getClassesTaughtByTeacher(id, pageable));
    }

    @GetMapping("/{id}/subjects")
    @Operation(summary = "Get subjects taught by the teacher")
    public ResponseEntity<Page<Subject>> getSubjectsTaughtByTeacher(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(teacherService.getSubjectsTaughtByTeacher(id, pageable));
    }
}
