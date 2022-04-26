package com.devmo.autogradingbe.bm.request;

public class SolutionSubmitRequest {

    private String studentId;

    private String repositoryUrl;

    private String branchName;

    private String pullRequestId;

    private String language;

    public SolutionSubmitRequest(String studentId,
                                 String repositoryUrl,
                                 String branchName,
                                 String pullRequestId,
                                 String language) {
        this.studentId = studentId;
        this.repositoryUrl = repositoryUrl;
        this.branchName = branchName;
        this.pullRequestId = pullRequestId;
        this.language = language;
    }

    @Override
    public String toString() {
        return "SolutionSubmitRequest{" +
                "studentId='" + studentId + '\'' +
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
}
