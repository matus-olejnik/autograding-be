package com.devmo.autogradingbe.controller.rest;

import com.devmo.autogradingbe.bm.request.SolutionSubmitRequest;
import com.devmo.autogradingbe.config.GitConfig;
import com.devmo.autogradingbe.service.SolutionSvc;
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

    @Autowired
    public SolutionSubmitController(GitConfig gitConfig, SolutionSvc solutionSvc) {
        this.gitConfig = gitConfig;
        this.solutionSvc = solutionSvc;
    }

    @PostMapping("/code-sumbit")
    public ResponseEntity<?> processBuildOutput(@RequestBody SolutionSubmitRequest request) {

        logger.info("Processing build output: " + request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/solution")
    public ResponseEntity<?> solutionSubmit(@RequestBody SolutionSubmitRequest request) throws Exception {

        logger.info(String.format("Start processing of solution %s", request));

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

        if ("java".equalsIgnoreCase(request.getLanguage())) {
            FileUtils.copyDirectory(
                    new File(studentDir.getPath() + File.separator + "src" + File.separator + "main"),
                    new File(testDir.getPath() + File.separator + "src" + File.separator + "main")
            );
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
        FileUtils.deleteDirectory(new File(mergingDir));

        return ResponseEntity.ok().build();
    }
}
