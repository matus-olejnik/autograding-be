package com.devmo.autogradingbe.controller.rest;

import com.devmo.autogradingbe.bm.BuildOutputRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @PostMapping("/build-outputs")
    public ResponseEntity<?> processBuildOutput(@RequestBody BuildOutputRequest request) {

        logger.info("Processing build output: " + request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/build-outputs")
    public String processBuildOutput() {

        return "Testing...";
    }
}
