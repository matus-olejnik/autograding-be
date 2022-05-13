package com.devmo.autogradingbe.controller.rest;

import com.devmo.autogradingbe.bm.request.SolutionSubmitRequest;
import com.devmo.autogradingbe.config.GitConfig;
import com.devmo.autogradingbe.service.EvaluationSvc;
import com.devmo.autogradingbe.service.ExternalSvc;
import com.devmo.autogradingbe.service.SolutionSvc;
import com.devmo.autogradingbe.util.DateTimeUtil;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.RefSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
public class SolutionSubmitController {

    private static final Logger logger = LoggerFactory.getLogger(SolutionSubmitController.class);

    private static final String LOCAL_TEST_BRANCH_NAME_FORMAT = "%s/%s/%s";
    private static final String REMOTE_TEST_BRANCH_NAME_FORMAT = "%s:%s";

    @Value("${print-file-content-to-log}")
    private boolean printFileContentToLog;

    private final GitConfig gitConfig;

    private final SolutionSvc solutionSvc;

    private final EvaluationSvc evaluationSvc;

    private final ExternalSvc externalSvc;

    @Autowired
    public SolutionSubmitController(GitConfig gitConfig, SolutionSvc solutionSvc, EvaluationSvc evaluationSvc, ExternalSvc externalSvc) {
        this.gitConfig = gitConfig;
        this.solutionSvc = solutionSvc;
        this.evaluationSvc = evaluationSvc;
        this.externalSvc = externalSvc;
    }

    @PostMapping("/solution")
    public ResponseEntity<?> solutionSubmit(@RequestBody SolutionSubmitRequest request) throws Exception {

        logger.info(String.format("Start processing of solution %s", request));

        if (solutionSvc.existsByBranchNameAndStudentId(request.getBranchName(), request.getStudentId())) {
            logger.info(String.format("Solution already exists branchName=%s, studentId=%s",
                    request.getBranchName(), request.getStudentId())
            );

            evaluationSvc.sendMessageToStudent(
                    request.getPullRequestId(),
                    request.getRepositoryUrl(),
                    "Riešenie zadania už bolo raz odovzdané."
            );

            return ResponseEntity.ok().build();

        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime deadline = LocalDateTime.parse(request.getDeadline(), formatter);

            if (DateTimeUtil.ldtNow().isAfter(deadline)) {
                logger.info(String.format("Solution is submitted after deadline, branchName=%s, studentId=%s",
                        request.getBranchName(), request.getStudentId())
                );

                evaluationSvc.sendMessageToStudent(
                        request.getPullRequestId(),
                        request.getRepositoryUrl(),
                        "Riešenie bolo odovzdané po termíne. Výsledný počet bodov je 0."
                );

            }
        }

        Long solutionId = solutionSvc.processSolutionSubmit(request);

        logger.info(String.format("Finished processing of solution, solutionId=%s", solutionId));

        String mergingDir = "merging" + File.separator + UUID.randomUUID();
        File studentDir = new File(mergingDir + File.separator + "student-solution");
        if (!studentDir.exists()) {
            studentDir.mkdirs();
        }

        String branchToClone = "refs/heads/" + request.getBranchName();

        Git.cloneRepository()
                .setURI(request.getRepositoryUrl().replace("git://", "https://"))
                .setBranchesToClone(List.of(branchToClone))
                .setBranch(branchToClone)
                .setDirectory(studentDir)
                .setCredentialsProvider(gitConfig.getCredentialsProvider())
                .call()
                .close();

        if (Boolean.TRUE.equals(request.getUploadToExternalSystem())) {
            externalSvc.backupFile(studentDir, request.getAssignmentExternalId(), request.getStudentEmail());
        }

        File testDir = new File(mergingDir + File.separator + "test");
        if (!testDir.exists()) {
            testDir.mkdirs();
        }

        Git testGit = Git.cloneRepository()
                .setURI(gitConfig.getTestingGithubRepositoryUrl())
                .setBranchesToClone(List.of(branchToClone))
                .setBranch(branchToClone)
                .setDirectory(testDir)
                .setCredentialsProvider(gitConfig.getCredentialsProvider())
                .call();

        if (Boolean.TRUE.equals(request.getStaticSolution())) {
            FileUtils.copyDirectory(
                    new File(studentDir.getPath() + File.separator + "src"),
                    new File(testDir.getPath() + File.separator + "src")
            );

        } else {
            if ("java".equalsIgnoreCase(request.getLanguage())) {
                FileUtils.copyDirectory(
                        new File(studentDir.getPath() + File.separator + "src" + File.separator + "main"),
                        new File(testDir.getPath() + File.separator + "src" + File.separator + "main")
                );
            }
        }

        String testBranchName = String.format(
                LOCAL_TEST_BRANCH_NAME_FORMAT, solutionId, request.getStudentId(), request.getBranchName());

        testGit.branchCreate().setName(testBranchName).call();
        testGit.checkout().setName(testBranchName).call();
        testGit.add()
                .addFilepattern(".")
                .call();

        testGit.commit().setMessage("add solution of student").call();

        testGit.push()
                .setRemote("origin")
                .setRefSpecs(new RefSpec(String.format(REMOTE_TEST_BRANCH_NAME_FORMAT, testBranchName, testBranchName)))
                .setCredentialsProvider(gitConfig.getCredentialsProvider())
                .call();

        testGit.close();
        try {
            FileUtils.deleteDirectory(new File(mergingDir));

        } catch (Exception e) {
            //silent
        }

        return ResponseEntity.ok().build();
    }
}
