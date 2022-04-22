package com.devmo.autogradingbe.service;

import com.devmo.autogradingbe.bm.request.SolutionSubmitRequest;
import com.devmo.autogradingbe.dm.SolutionEntity;
import com.devmo.autogradingbe.repository.SolutionRepository;
import com.devmo.autogradingbe.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolutionService implements SolutionSvc {

    private final SolutionRepository solutionRepository;

    @Autowired
    public SolutionService(SolutionRepository solutionRepository) {
        this.solutionRepository = solutionRepository;
    }

    @Override
    public Long processSolutionSubmit(SolutionSubmitRequest solutionSubmitRequest) {
        SolutionEntity solutionEntity = new SolutionEntity();

        solutionEntity.setSubmittedOn(DateTimeUtil.ldtNow());

        solutionEntity.setStudentId(solutionSubmitRequest.getStudentId());
        solutionEntity.setRepositoryUrl(solutionSubmitRequest.getRepositoryUrl());
        solutionEntity.setBranchName(solutionSubmitRequest.getBranchName());
        solutionEntity.setPullRequestId(solutionSubmitRequest.getPullRequestId());

        SolutionEntity savedSolutionEntity = solutionRepository.save(solutionEntity);

        return savedSolutionEntity.getId();
    }
}
