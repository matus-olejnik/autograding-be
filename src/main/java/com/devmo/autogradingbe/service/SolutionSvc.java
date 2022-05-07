package com.devmo.autogradingbe.service;

import com.devmo.autogradingbe.bm.request.SolutionSubmitRequest;
import com.devmo.autogradingbe.dm.AutogradingSolutionEntity;
import org.springframework.web.multipart.MultipartFile;

public interface SolutionSvc {

    Long processSolutionSubmit(SolutionSubmitRequest solutionSubmitRequest);

    AutogradingSolutionEntity processTestOutput(Long solutionId, MultipartFile file);
}
