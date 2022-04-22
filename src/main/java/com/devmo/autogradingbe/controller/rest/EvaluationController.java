package com.devmo.autogradingbe.controller.rest;

import com.devmo.autogradingbe.util.FileUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
public class EvaluationController {

    private static final Logger logger = LoggerFactory.getLogger(EvaluationController.class);

    @Value("${print-file-content-to-log}")
    private boolean printFileContentToLog;

    private final UsernamePasswordCredentialsProvider credentialsProvider;

    @Autowired
    public EvaluationController(UsernamePasswordCredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
    }

    @PostMapping("/{studentId}/build-outputs")
    public ResponseEntity<?> processBuildOutput(
            @PathVariable String studentId,
            @RequestParam("output-file") MultipartFile file) {

        logger.info(String.format("Processing build output: studentId=%s, fileName=%s", studentId, file.getOriginalFilename()));

        if (printFileContentToLog) {
            FileUtil.printFileContent(file);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/build-outputs")
    public String processBuildOutput() {

        return "Testing...";
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
