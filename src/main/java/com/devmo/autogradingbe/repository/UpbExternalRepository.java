package com.devmo.autogradingbe.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;

@Repository
public class UpbExternalRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveSolution(String fileUrl, Long assigmentIdentifier, String studentIdentifier) {
        entityManager.createNativeQuery(
                        "INSERT INTO solution (assign_id, student_id, solution_url, teacher_id) VALUES (?,?,?,?)")
                .setParameter(1, assigmentIdentifier)
                .setParameter(2, studentIdentifier)
                .setParameter(3, fileUrl)
                .setParameter(4, "system@system")
                .executeUpdate();
    }

    @Transactional
    public void updateEvaluation(BigDecimal numberOfPoints, String message, Long assigmentIdentifier, String studentIdentifier) {
        entityManager.createNativeQuery("UPDATE solution SET points_ta = ?, ta_comment = ? " +
                        "WHERE student_id = ? AND assign_id = ?")
                .setParameter(1, numberOfPoints)
                .setParameter(2, message)
                .setParameter(3, studentIdentifier)
                .setParameter(4, assigmentIdentifier)
                .executeUpdate();
    }

}
