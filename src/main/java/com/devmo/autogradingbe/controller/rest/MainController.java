package com.devmo.autogradingbe.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@RestController
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Value("${print-file-content-to-log}")
    private boolean printFileContentToLog;

    @PostMapping("/{studentId}/build-outputs")
    public ResponseEntity<?> processBuildOutput(
            @PathVariable String studentId,
            @RequestParam("output-file") MultipartFile file) {

        logger.info(String.format("Processing build output: studentId=%s, fileName=%s", studentId, file.getName()));

        if (printFileContentToLog) {
            BufferedReader bufferedReader;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
                bufferedReader.lines().forEach(System.out::println);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/build-outputs")
    public String processBuildOutput() {

        return "Testing...";
    }
}
