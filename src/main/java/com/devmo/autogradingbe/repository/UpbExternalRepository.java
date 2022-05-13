package com.devmo.autogradingbe.repository;

import com.devmo.autogradingbe.dm.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UpbExternalRepository extends JpaRepository<BaseEntity, Long> {

    @Query(nativeQuery = true, value = "insert into tmp(local_date) values(null);")
    void uploadSolution(String fileUrl, Long assigmentIdentifier, String studentIdentifier);

}
