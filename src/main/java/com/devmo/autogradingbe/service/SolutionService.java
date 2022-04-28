package com.devmo.autogradingbe.service;

import com.devmo.autogradingbe.bm.request.SolutionSubmitRequest;
import com.devmo.autogradingbe.dm.SolutionEntity;
import com.devmo.autogradingbe.repository.SolutionRepository;
import com.devmo.autogradingbe.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

@Service
public class SolutionService implements SolutionSvc {

    @Value("${start-tests-identifier}")
    private String START_TESTS_IDENTIFIER;

    @Value("${end-tests-identifier}")
    private String END_TESTS_IDENTIFIER;

    @Value("${start-test-result-identifier}")
    private String START_TEST_RESULT_IDENTIFIER;

    @Value("${end-test-result-identifier}")
    private String END_TEST_RESULT_IDENTIFIE;

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
        solutionEntity.setGitUserName(solutionSubmitRequest.getGitUserName());
        solutionEntity.setRepositoryUrl(solutionSubmitRequest.getRepositoryUrl());
        solutionEntity.setBranchName(solutionSubmitRequest.getBranchName());
        solutionEntity.setPullRequestId(solutionSubmitRequest.getPullRequestId());
        solutionEntity.setLanguage(solutionSubmitRequest.getLanguage());

        SolutionEntity savedSolutionEntity = solutionRepository.save(solutionEntity);

        return savedSolutionEntity.getId();
    }

    @Override
    public SolutionEntity processTestOutput(Long solutionId, MultipartFile file) {
        SolutionEntity solutionEntity = solutionRepository.findById(solutionId)
                .orElseThrow(() -> new IllegalStateException("Solution was not found, id=" + solutionId));

        String testsResultPart = findTestsResultPart(file);
        BigDecimal numberOfPoints = calculatePointsFromTestsResult(testsResultPart);

        solutionEntity.setNumberOfPoints(numberOfPoints);
        solutionEntity.setTestsResult(testsResultPart);

        return solutionEntity;
    }

    private String findTestsResultPart(MultipartFile file) {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            bufferedReader.lines().forEach(System.out::println);

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
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    private BigDecimal calculatePointsFromTestsResult(String testsResult) {

        return BigDecimal.ONE;
    }
}
