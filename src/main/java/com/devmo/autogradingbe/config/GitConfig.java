package com.devmo.autogradingbe.config;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class GitConfig {

    private String gitUserName;

    private String gitAccessToken;

    private String testingGithubRepositoryUrl;

    private UsernamePasswordCredentialsProvider credentialsProvider;

    public GitConfig() {
    }

    public GitConfig(String gitUserName,
                     String gitAccessToken,
                     String testingGithubRepositoryUrl,
                     UsernamePasswordCredentialsProvider credentialsProvider) {
        this.gitUserName = gitUserName;
        this.gitAccessToken = gitAccessToken;
        this.testingGithubRepositoryUrl = testingGithubRepositoryUrl;
        this.credentialsProvider = credentialsProvider;
    }

    public String getGitUserName() {
        return gitUserName;
    }

    public void setGitUserName(String gitUserName) {
        this.gitUserName = gitUserName;
    }

    public String getGitAccessToken() {
        return gitAccessToken;
    }

    public void setGitAccessToken(String gitAccessToken) {
        this.gitAccessToken = gitAccessToken;
    }

    public String getTestingGithubRepositoryUrl() {
        return testingGithubRepositoryUrl;
    }

    public void setTestingGithubRepositoryUrl(String testingGithubRepositoryUrl) {
        this.testingGithubRepositoryUrl = testingGithubRepositoryUrl;
    }

    public UsernamePasswordCredentialsProvider getCredentialsProvider() {
        return credentialsProvider;
    }

    public void setCredentialsProvider(UsernamePasswordCredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
    }
}
