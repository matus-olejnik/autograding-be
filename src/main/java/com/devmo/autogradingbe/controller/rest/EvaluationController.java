package com.devmo.autogradingbe.controller.rest;

import com.devmo.autogradingbe.config.GitConfig;
import com.devmo.autogradingbe.dm.SolutionEntity;
import com.devmo.autogradingbe.service.SolutionSvc;
import com.devmo.autogradingbe.util.FileUtil;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
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

    private final GitConfig gitConfig;

    private final SolutionSvc solutionSvc;

    @Autowired
    public EvaluationController(GitConfig gitConfig, SolutionSvc solutionSvc) {
        this.gitConfig = gitConfig;
        this.solutionSvc = solutionSvc;
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

        Long solutionId = Long.valueOf(testBranchName.substring(0, testBranchName.indexOf("/")));
        SolutionEntity solutionEntity = solutionSvc.processTestOutput(solutionId, outputTestFile);

        GitHub github = new GitHubBuilder()
                .withOAuthToken(gitConfig.getGitAccessToken(), gitConfig.getGitUserName())
                .build();

        int pullRequestId = Integer.parseInt(
                solutionEntity.getPullRequestId().replaceAll("refs/pull/|/merge", ""));

        GHRepository repository = github.getRepository(
                solutionEntity.getRepositoryUrl().replaceAll("git://github\\.com/|https://github\\.com/|\\.git", ""));

        repository.getPullRequest(pullRequestId)
                .comment(solutionEntity.getNumberOfPoints() + "b\n\n" + solutionEntity.getTestsResult());
        repository.getPullRequest(pullRequestId).close();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/start-heroku")
    public String startHeroku() {
        return "started";
    }
}
