package com.devmo.autogradingbe.service;

import com.devmo.autogradingbe.config.GitConfig;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService implements EvaluationSvc {

    private final GitConfig gitConfig;

    @Autowired
    public EvaluationService(GitConfig gitConfig) {
        this.gitConfig = gitConfig;
    }


    @Override
    public void sendMessageToStudent(String pullRequestIdentifier,
                                     String repositoryUrl,
                                     String commentMessage) throws Exception {

        GitHub github = new GitHubBuilder()
                .withOAuthToken(gitConfig.getGitAccessToken(), gitConfig.getGitUserName())
                .build();

        int pullRequestId = Integer.parseInt(pullRequestIdentifier.replaceAll("refs/pull/|/merge", ""));

        GHRepository repository = github.getRepository(
                repositoryUrl.replaceAll("git://github\\.com/|https://github\\.com/|\\.git", ""));

        repository.getPullRequest(pullRequestId).comment(commentMessage);
        repository.getPullRequest(pullRequestId).close();
    }
}
