package com.SufyanImran.Simplifi_test.controller;

import com.SufyanImran.Simplifi_test.dto.SubjectDTO;
import com.SufyanImran.Simplifi_test.model.SchoolClass;
import com.SufyanImran.Simplifi_test.model.Subject;
import com.SufyanImran.Simplifi_test.model.Teacher;
import com.SufyanImran.Simplifi_test.service.SubjectService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subjects")
@Tag(name = "Subjects", description = "Operations related to subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    @Operation(summary = "Get all subjects (paginated)")
    public ResponseEntity<Page<Subject>> getAllSubjects(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(subjectService.getAllSubjects(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get subject by ID")
    public ResponseEntity<Subject> getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new subject")
    public ResponseEntity<Subject> createSubject(@RequestBody @Valid SubjectDTO subjectDTO) {
        Subject created = subjectService.createSubjectFromDTO(subjectDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a subject")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/classes")
    @Operation(summary = "Get classes with a subject")
    public ResponseEntity<Page<SchoolClass>> getClassesWithSubject(@PathVariable Long id, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(subjectService.getClassesWithSubject(id, pageable));
    }
}