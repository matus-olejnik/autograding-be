package com.devmo.autogradingbe.repository;

import com.devmo.autogradingbe.dm.AutogradingSolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepository extends JpaRepository<AutogradingSolutionEntity, Long> {

}
