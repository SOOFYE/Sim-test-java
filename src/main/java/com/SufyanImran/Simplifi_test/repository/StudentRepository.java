package com.SufyanImran.Simplifi_test.repository;

import com.SufyanImran.Simplifi_test.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
