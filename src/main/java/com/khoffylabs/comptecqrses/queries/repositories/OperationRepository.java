package com.khoffylabs.comptecqrses.queries.repositories;

import com.khoffylabs.comptecqrses.queries.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OperationRepository extends JpaRepository<Operation, Long> {
}
