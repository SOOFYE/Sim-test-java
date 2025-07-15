package com.SufyanImran.Simplifi_test.model;

import jakarta.persistence.*;

@Entity
public class Student{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String emailAddress;
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

}