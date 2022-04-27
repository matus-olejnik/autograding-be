package com.devmo.autogradingbe.controller.rest;

import com.devmo.autogradingbe.service.SolutionSvc;
import com.devmo.autogradingbe.util.FileUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
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

@RestController
public class EvaluationController {

    private static final Logger logger = LoggerFactory.getLogger(EvaluationController.class);

    @Value("${print-file-content-to-log}")
    private boolean printFileContentToLog;

    private final UsernamePasswordCredentialsProvider credentialsProvider;

    private final SolutionSvc solutionSvc;

    @Autowired
    public EvaluationController(UsernamePasswordCredentialsProvider credentialsProvider, SolutionSvc solutionSvc) {
        this.credentialsProvider = credentialsProvider;
        this.solutionSvc = solutionSvc;
    }

    @PostMapping("/test-output")
    public ResponseEntity<?> processTestOutput(
            @RequestParam String testBranchName,
            @RequestParam("outputTestFile") MultipartFile outputTestFile) {

        logger.info(String.format("Processing test output: testBranchName=%s, fileName=%s",
                testBranchName,
                outputTestFile.getOriginalFilename()));

        if (printFileContentToLog) {
            FileUtil.printFileContent(outputTestFile);
        }


        return ResponseEntity.ok().build();
    }

    @GetMapping("/build-outputs")
    public String processBuildOutput(@RequestParam String test) {


        return test;
    }

    @GetMapping("/test")
    public String test() throws Exception {


//        Git git = Git.cloneRepository()
//                .setURI("https://github.com/matus-olejnik/autograding.git")
////                .setURI("https://github.com/matus39/autograding-test.git")
//                .setBranchesToClone(Arrays.asList("refs/heads/zad2"))
////                .setBranchesToClone(Arrays.asList("refs/heads/master"))
//                .setBranch("refs/heads/zad2")
//                .setCredentialsProvider(credentialsProvider)
//                .call();
//
//

        //                .setDirectory(new File("/path/to/targetdirectory"))


//        Git git = Git.open(new File("C:\\Users\\Matus\\IdeaProjects\\autograding-be\\autograding-test\\.git"));
        Git git = Git.open(new File("C:\\Users\\Matus\\IdeaProjects\\autograding-be\\autograding\\.git"));

//        git.checkout();
//        Repository repository = git.getRepository();
//        git.remoteSetUrl().setRemoteUri(new URIish("https://github.com/matus39/autograding-test.git")).call();
//        StoredConfig config = git.getRepository().getConfig();
//        config.setString("remote", "origin-test", "url", "https://github.com/matus39/autograding-test.git");
//        config.save();
//        git.rebase().setUpstream("origin-test").call();

//        git.remoteAdd()
//                .setName("origin-test")
//                .setUri(new URIish("https://github.com/matus39/autograding-test.git"))
//                .call();


//        git.branchCreate().setName("new-origin-test").call();

        git.push()
                .setRemote("origin-test")
                .setRefSpecs(new RefSpec("new-origin-test:new-origin-test"))
                .setCredentialsProvider(credentialsProvider)
                .call();

        git.close();
        return "Testing...";
    }
}
