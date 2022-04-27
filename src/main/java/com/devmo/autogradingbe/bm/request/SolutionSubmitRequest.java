package com.devmo.autogradingbe.bm.request;

public class SolutionSubmitRequest {

    private String studentId;

    private String gitUserName;

    private String repositoryUrl;

    private String branchName;

    private String pullRequestId;

    private String language;

    public SolutionSubmitRequest() {
    }

    @Override
    public String toString() {
        return "SolutionSubmitRequest{" +
                "studentId='" + studentId + '\'' +
                ", gitUserName='" + gitUserName + '\'' +
                ", repositoryUrl='" + repositoryUrl + '\'' +
                ", branchName='" + branchName + '\'' +
                ", pullRequestId='" + pullRequestId + '\'' +
                ", language='" + language + '\'' +
                '}';
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getPullRequestId() {
        return pullRequestId;
    }

    public void setPullRequestId(String pullRequestId) {
        this.pullRequestId = pullRequestId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGitUserName() {
        return gitUserName;
    }

    public void setGitUserName(String gitUserName) {
        this.gitUserName = gitUserName;
    }
}
