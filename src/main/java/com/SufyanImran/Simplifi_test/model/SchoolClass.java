package com.SufyanImran.Simplifi_test.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class SchoolClass {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "schoolClass")
    private Set<Student> students;

    @ManyToMany(mappedBy = "classes")
    private Set<Teacher> teachers;

    @ManyToMany
    @JoinTable(
        name = "class_subject",
        joinColumns = @JoinColumn(name = "class_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects;

}