package com.taskmanagement.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.taskmanagement.backend.model.DummyEntity;

@Repository
public interface DatabaseHealthRepository extends CrudRepository<DummyEntity, Long> {
    @Query(value = "SELECT 1", nativeQuery = true)
    Integer checkDatabaseConnection();
}
