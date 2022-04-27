package com.devmo.autogradingbe.service;

import com.devmo.autogradingbe.bm.request.SolutionSubmitRequest;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public interface SolutionSvc {

    Long processSolutionSubmit(SolutionSubmitRequest solutionSubmitRequest);

    BigDecimal processTestOutput(Long solutionId, MultipartFile file);
}
