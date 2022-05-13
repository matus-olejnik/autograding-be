package com.devmo.autogradingbe.controller.rest;

import com.devmo.autogradingbe.dm.AutogradingSolutionEntity;
import com.devmo.autogradingbe.service.EvaluationSvc;
import com.devmo.autogradingbe.service.ExternalSvc;
import com.devmo.autogradingbe.service.SolutionSvc;
import com.devmo.autogradingbe.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class EvaluationController {

    private static final Logger logger = LoggerFactory.getLogger(EvaluationController.class);

    @Value("${print-file-content-to-log}")
    private boolean printFileContentToLog;

    private final SolutionSvc solutionSvc;

    private final EvaluationSvc evaluationSvc;

    private final ExternalSvc externalSvc;

    @Autowired
    public EvaluationController(SolutionSvc solutionSvc, EvaluationSvc evaluationSvc, ExternalSvc externalSvc) {
        this.solutionSvc = solutionSvc;
        this.evaluationSvc = evaluationSvc;
        this.externalSvc = externalSvc;
    }

    @PostMapping("/test-output")
    public ResponseEntity<?> processTestOutput(
            @RequestParam("test-branch-name") String testBranchName,
            @RequestParam("output-test-file") MultipartFile outputTestFile) throws Exception {

        logger.info(String.format("Processing test output: testBranchName=%s, fileName=%s",
                testBranchName,
                outputTestFile.getOriginalFilename()));

        if (printFileContentToLog) {
            FileUtil.printFileContent(outputTestFile);
        }

        testBranchName = testBranchName.replace("refs/heads/", "");
        Long solutionId = Long.valueOf(testBranchName.substring(0, testBranchName.indexOf("/")));
        AutogradingSolutionEntity autogradingSolutionEntity = solutionSvc.processTestOutput(solutionId, outputTestFile);

        String commentMessage = String.format("Z automatického hodnotenia bolo získaných %s bodov.\nVýsledky testov:\n\n%s",
                autogradingSolutionEntity.getNumberOfPoints(), autogradingSolutionEntity.getTestsResult());

        evaluationSvc.sendMessageToStudent(
                autogradingSolutionEntity.getPullRequestId(),
                autogradingSolutionEntity.getRepositoryUrl(),
                commentMessage
        );

        if (autogradingSolutionEntity.isUploadToExternalSystem()) {
            externalSvc.updateEvaluation(
                    autogradingSolutionEntity.getNumberOfPoints(),
                    autogradingSolutionEntity.getTestsResult(),
                    autogradingSolutionEntity.getAssignmentExternalId(),
                    autogradingSolutionEntity.getStudentEmail()
            );
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/start-heroku")
    public String startHeroku() {
        return "started";
    }
}
