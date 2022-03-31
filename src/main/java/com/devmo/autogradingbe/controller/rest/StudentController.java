package com.devmo.autogradingbe.controller.rest;

import com.devmo.autogradingbe.bm.request.StudentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @PostMapping("/code-sumbit")
    public ResponseEntity<?> processBuildOutput(@RequestBody StudentRequest request) {

        logger.info("Processing build output: " + request);

        return ResponseEntity.ok().build();
    }
}
