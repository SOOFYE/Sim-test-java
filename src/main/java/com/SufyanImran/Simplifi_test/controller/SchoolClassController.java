package com.SufyanImran.Simplifi_test.controller;

import com.SufyanImran.Simplifi_test.dto.SchoolClassDTO;
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
@RequestMapping("/api/classes")
@Tag(name = "School Classes", description = "Manage school classes")
public class SchoolClassController {

    private final SchoolClassService classService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final SubjectService subjectService;

    @Autowired
    public SchoolClassController(
            SchoolClassService classService,
            StudentService studentService,
            TeacherService teacherService,
            SubjectService subjectService
    ) {
        this.classService = classService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.subjectService = subjectService;
    }

    @GetMapping
    @Operation(summary = "Get all classes (paginated)")
    public ResponseEntity<Page<SchoolClass>> getAllClasses(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(classService.getAllClasses(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get class by ID")
    public ResponseEntity<SchoolClass> getClassById(@PathVariable Long id) {
        return classService.getClassById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new class")
    public ResponseEntity<SchoolClass> createClass(@Valid @RequestBody SchoolClassDTO dto) {
        SchoolClass created = classService.createClassFromDTO(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/students")
    @Operation(summary = "Add students to a class")
    public ResponseEntity<SchoolClass> addStudentsToClass(@PathVariable Long id, @RequestBody List<Long> studentIds) {
        SchoolClass updatedClass = classService.addStudentsToClass(id, studentIds);
        return ResponseEntity.ok(updatedClass);
    }

    @PostMapping("/{id}/teachers")
    @Operation(summary = "Add teachers to a class")
    public ResponseEntity<SchoolClass> addTeachersToClass(@PathVariable Long id, @RequestBody List<Long> teacherIds) {
        SchoolClass updatedClass = classService.addTeachersToClass(id, teacherIds);
        return ResponseEntity.ok(updatedClass);
    }

    @PostMapping("/{id}/subjects")
    @Operation(summary = "Add subjects to a class")
    public ResponseEntity<SchoolClass> addSubjectsToClass(@PathVariable Long id, @RequestBody List<Long> subjectIds) {
        SchoolClass updatedClass = classService.addSubjectsToClass(id, subjectIds);
        return ResponseEntity.ok(updatedClass);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete class by ID")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/students")
    @Operation(summary = "Get students in a class")
    public ResponseEntity<Page<Student>> getStudentsInClass(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(classService.getStudentsInClass(id, pageable));
    }

    @GetMapping("/{id}/teachers")
    @Operation(summary = "Get teachers in a class")
    public ResponseEntity<Page<Teacher>> getTeachersInClass(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(classService.getTeachersInClass(id, pageable));
    }
}
