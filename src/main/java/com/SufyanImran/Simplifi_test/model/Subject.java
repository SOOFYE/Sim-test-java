package com.SufyanImran.Simplifi_test.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "subjects")
    private Set<Teacher> teachers;

    @ManyToMany(mappedBy = "subjects")
    private Set<SchoolClass> classes;


}