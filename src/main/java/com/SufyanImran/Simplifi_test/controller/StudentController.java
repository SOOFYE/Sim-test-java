package com.SufyanImran.Simplifi_test.controller;

import com.SufyanImran.Simplifi_test.dto.StudentDTO;
import com.SufyanImran.Simplifi_test.model.SchoolClass;
import com.SufyanImran.Simplifi_test.model.Student;
import com.SufyanImran.Simplifi_test.model.Teacher;
import com.SufyanImran.Simplifi_test.service.SchoolClassService;
import com.SufyanImran.Simplifi_test.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Students", description = "Operations related to students")
public class StudentController {

    private final StudentService studentService;
    private final SchoolClassService schoolClassService;

    @Autowired
    public StudentController(StudentService studentService, SchoolClassService schoolClassService) {
        this.studentService = studentService;
        this.schoolClassService = schoolClassService;
    }

    @GetMapping
    @Operation(summary = "Get all students (paginated)")
    public ResponseEntity<Page<Student>> getAllStudents(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(studentService.getAllStudents(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new student")
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentDTO dto) {
        Student saved = studentService.createStudentFromDTO(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a student by ID")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/teachers")
    @Operation(summary = "Get teachers of a student")
    public ResponseEntity<Page<Teacher>> getTeachersOfStudent(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(studentService.getTeachersOfStudent(id, pageable));
    }

    @GetMapping("/{id}/class")
    @Operation(summary = "Get the class of a student")
    public ResponseEntity<Page<SchoolClass>> getStudentClass(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(studentService.getClassesOfStudent(id, pageable));
    }
}