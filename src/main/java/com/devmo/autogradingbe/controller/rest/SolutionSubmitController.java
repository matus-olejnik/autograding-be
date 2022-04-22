package com.devmo.autogradingbe.controller.rest;

import com.devmo.autogradingbe.bm.request.SolutionSubmitRequest;
import com.devmo.autogradingbe.service.SolutionSvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SolutionSubmitController {

    private static final Logger logger = LoggerFactory.getLogger(SolutionSubmitController.class);

    @Value("${print-file-content-to-log}")
    private boolean printFileContentToLog;

    private final SolutionSvc solutionSvc;

    @Autowired
    public SolutionSubmitController(SolutionSvc solutionSvc) {
        this.solutionSvc = solutionSvc;
    }

    @PostMapping("/code-sumbit")
    public ResponseEntity<?> processBuildOutput(@RequestBody SolutionSubmitRequest request) {

        logger.info("Processing build output: " + request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/solution")
    public ResponseEntity<?> solutionSubmit(@RequestBody SolutionSubmitRequest request) {

        logger.info(String.format("Processing solution submit %s", request));

        Long solutionId = solutionSvc.processSolutionSubmit(request);


        return ResponseEntity.ok().build();
    }
}
