package com.SufyanImran.Simplifi_test.repository;

import com.SufyanImran.Simplifi_test.model.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
}
