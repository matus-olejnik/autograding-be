package com.devmo.autogradingbe.service;

import com.devmo.autogradingbe.bm.request.SolutionSubmitRequest;
import com.devmo.autogradingbe.bm.response.TestResult;
import com.devmo.autogradingbe.bm.response.TestResultTypeEnu;
import com.devmo.autogradingbe.dm.AutogradingSolutionEntity;
import com.devmo.autogradingbe.repository.SolutionRepository;
import com.devmo.autogradingbe.util.DateTimeUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class SolutionService implements SolutionSvc {

    @Value("${start-tests-identifier}")
    private String START_TESTS_IDENTIFIER;

    @Value("${end-tests-identifier}")
    private String END_TESTS_IDENTIFIER;

    @Value("${start-test-result-identifier}")
    private String START_TEST_RESULT_IDENTIFIER;

    @Value("${end-test-result-identifier}")
    private String END_TEST_RESULT_IDENTIFIER;

    private final SolutionRepository solutionRepository;

    @Autowired
    public SolutionService(SolutionRepository solutionRepository) {
        this.solutionRepository = solutionRepository;
    }

    @Override
    public Long processSolutionSubmit(SolutionSubmitRequest solutionSubmitRequest) {
        AutogradingSolutionEntity autogradingSolutionEntity = new AutogradingSolutionEntity();

        autogradingSolutionEntity.setSubmittedOn(DateTimeUtil.ldtNow());

        autogradingSolutionEntity.setStudentId(solutionSubmitRequest.getStudentId());
        autogradingSolutionEntity.setGitUserName(solutionSubmitRequest.getGitUserName());
        autogradingSolutionEntity.setRepositoryUrl(solutionSubmitRequest.getRepositoryUrl());
        autogradingSolutionEntity.setBranchName(solutionSubmitRequest.getBranchName());
        autogradingSolutionEntity.setPullRequestId(solutionSubmitRequest.getPullRequestId());
        autogradingSolutionEntity.setLanguage(solutionSubmitRequest.getLanguage());

        AutogradingSolutionEntity savedAutogradingSolutionEntity = solutionRepository.save(autogradingSolutionEntity);

        return savedAutogradingSolutionEntity.getId();
    }

    @Override
    public AutogradingSolutionEntity processTestOutput(Long solutionId, MultipartFile file) {
        AutogradingSolutionEntity autogradingSolutionEntity = solutionRepository.findById(solutionId)
                .orElseThrow(() -> new IllegalStateException("Solution was not found, id=" + solutionId));

        String testsResultPart = findTestsResultPart(file);
        BigDecimal numberOfPoints = calculatePointsFromTestsResult(testsResultPart);

        autogradingSolutionEntity.setNumberOfPoints(numberOfPoints);
        autogradingSolutionEntity.setTestsResult(testsResultPart);

        return solutionRepository.save(autogradingSolutionEntity);
    }

    private String findTestsResultPart(MultipartFile file) {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));

            String line;
            boolean startTestIdentifierFound = false;

            while ((line = bufferedReader.readLine()) != null) {
                if (START_TESTS_IDENTIFIER.equals(line)) {
                    startTestIdentifierFound = true;
                    continue;
                }
                if (END_TESTS_IDENTIFIER.equals(line)) {
                    break;
                }

                if (startTestIdentifierFound) {
                    sb.append(line);
                    sb.append("\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    private BigDecimal calculatePointsFromTestsResult(String testsResult) {
        List<TestResult> testResults = buildTestResultsFromTestResultString(testsResult);

        BigDecimal numberOfPoints = BigDecimal.ZERO;

        for (TestResult testResult : testResults) {
            if (TestResultTypeEnu.CORRECT.equals(testResult.getTestResult())) {
                numberOfPoints = numberOfPoints.add(BigDecimal.valueOf(testResult.getNumberOfPoints()));
            }
        }

        return numberOfPoints;
    }

    private List<TestResult> buildTestResultsFromTestResultString(String testsResult) {
        List<TestResult> testResults = new ArrayList<>();

        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();

        String[] lines = testsResult.split("\n");

        boolean startTestResultIdentifierFound = false;
        for (String line : lines) {
            if (START_TEST_RESULT_IDENTIFIER.equals(line)) {
                startTestResultIdentifierFound = true;
                continue;
            }

            if (END_TEST_RESULT_IDENTIFIER.equals(line)) {
                startTestResultIdentifierFound = false;
                testResults.add(gson.fromJson(sb.toString(), TestResult.class));
                sb = new StringBuilder();

                continue;
            }

            if (startTestResultIdentifierFound) {
                sb.append(line);
            }
        }

        return testResults;
    }
}
