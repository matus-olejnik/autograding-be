package com.devmo.autogradingbe.service;

import com.devmo.autogradingbe.bm.request.SolutionSubmitRequest;

public interface SolutionSvc {

    Long processSolutionSubmit(SolutionSubmitRequest solutionSubmitRequest);

}
