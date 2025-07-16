package com.SufyanImran.Simplifi_test.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "subjects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"teachers", "schoolClasses"})
@EqualsAndHashCode(exclude = {"teachers", "schoolClasses"})
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "id"
)
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "subjects")
    private Set<Teacher> teachers;

    @ManyToMany(mappedBy = "subjects")
    private Set<SchoolClass> schoolClasses;
}
