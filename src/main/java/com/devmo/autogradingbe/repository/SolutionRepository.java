package com.devmo.autogradingbe.repository;

import com.devmo.autogradingbe.dm.SolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepository extends JpaRepository<SolutionEntity, Long> {

}
