package com.devmo.autogradingbe.controller.rest;

import com.devmo.autogradingbe.config.GitConfig;
import com.devmo.autogradingbe.dm.SolutionEntity;
import com.devmo.autogradingbe.service.SolutionSvc;
import com.devmo.autogradingbe.util.FileUtil;
import org.eclipse.jgit.api.Git;
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

import java.io.File;
import java.util.Arrays;
import java.util.UUID;

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
            @RequestParam String testBranchName,
            @RequestParam("outputTestFile") MultipartFile outputTestFile) throws Exception {

        logger.info(String.format("Processing test output: testBranchName=%s, fileName=%s",
                testBranchName,
                outputTestFile.getOriginalFilename()));

        if (printFileContentToLog) {
            FileUtil.printFileContent(outputTestFile);
        }

        Long solutionId = Long.valueOf(testBranchName.substring(testBranchName.indexOf("/")));
        SolutionEntity solutionEntity = solutionSvc.processTestOutput(solutionId, outputTestFile);

        GitHub github = new GitHubBuilder()
                .withOAuthToken(gitConfig.getGitAccessToken(), gitConfig.getGitUserName())
                .build();

        GHRepository repository = github.getRepository("matus-olejnik/autograding");
        repository.getPullRequest(4).comment("15b");
        repository.getPullRequest(4).close();


        return ResponseEntity.ok().build();
    }

    @GetMapping("/build-outputs")
    public String processBuildOutput(@RequestParam String test) {


        return test;
    }

    @GetMapping("/test2")
    public String test2() throws Exception {

        GitHub github = new GitHubBuilder()
                .withOAuthToken(gitConfig.getGitAccessToken(), gitConfig.getGitUserName())
                .build();

        GHRepository repository = github.getRepository("matus-olejnik/autograding");
        repository.getPullRequest(4).comment("15b");
        repository.getPullRequest(4).close();


        System.out.println();

//        GHRepository ghRepository =

        return "test";
    }

    @GetMapping("/test")
    public String test() throws Exception {

        String mergingDir = "merging" + File.separator + UUID.randomUUID();
        File studentDir = new File(mergingDir + File.separator + "student-solution");
        if (!studentDir.exists()) {
            studentDir.mkdirs();
        }

        Git git = Git.cloneRepository()
                .setURI("https://github.com/matus-olejnik/autograding.git")
                .setBranchesToClone(Arrays.asList("refs/heads/zad2/java"))
                .setBranch("refs/heads/zad2/java")
                .setDirectory(studentDir)
                .setCredentialsProvider(gitConfig.getCredentialsProvider())
                .call();

        File testDir = new File(mergingDir + File.separator + "test");
        if (!testDir.exists()) {
            testDir.mkdirs();
        }

        Git.cloneRepository()
                .setURI(gitConfig.getTestingGithubRepositoryUrl())
                .setBranchesToClone(Arrays.asList("refs/heads/zad2/java"))
                .setBranch("refs/heads/zad2/java")
                .setDirectory(testDir)
                .setCredentialsProvider(gitConfig.getCredentialsProvider())
                .call();


//        Git git = Git.open(new File("C:\\Users\\Matus\\IdeaProjects\\autograding-be\\autograding-test\\.git"));
//        Git git = Git.open(new File("C:\\Users\\Matus\\IdeaProjects\\autograding-be\\autograding\\.git"));

//        git.checkout();
//        Repository repository = git.getRepository();
//        git.remoteSetUrl().setRemoteUri(new URIish("https://github.com/matus39/autograding-test.git")).call();
//        StoredConfig config = git.getRepository().getConfig();
//        config.setString("remote", "origin-test", "url", "https://github.com/matus39/autograding-test.git");
//        config.save();
//        git.rebase().setUpstream("origin-test").call();

//        git.remoteAdd()
//                .setName("origin-test")
//                .setUri(new URIish(testingGithubRepositoryUrl))
//                .call();
//
//        git.rebase().setUpstream("origin-test/zad2/java").setOperation(RebaseCommand.Operation.BEGIN).call();


//        git.branchCreate().setName("new-origin-test").call();
//        git.push()
//                .setRemote("origin-test")
//                .setRefSpecs(new RefSpec("new-origin-test:new-origin-test"))
//                .setCredentialsProvider(credentialsProvider)
//                .call();


        git.close();
//        FileUtils.deleteDirectory(directory);
        return "Testing...";
    }
}
