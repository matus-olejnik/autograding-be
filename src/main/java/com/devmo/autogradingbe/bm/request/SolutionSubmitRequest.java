package com.devmo.autogradingbe.bm.request;

public class SolutionSubmitRequest {

    private String studentId;

    private String repositoryUrl;

    private String branchName;

    private String pullRequestId;

    public SolutionSubmitRequest(String studentId, String repositoryUrl, String branchName, String pullRequestId) {
        this.studentId = studentId;
        this.repositoryUrl = repositoryUrl;
        this.branchName = branchName;
        this.pullRequestId = pullRequestId;
    }

    @Override
    public String toString() {
        return "SolutionSubmitRequest{" +
                "studentId='" + studentId + '\'' +
                ", repositoryUrl='" + repositoryUrl + '\'' +
                ", branchName='" + branchName + '\'' +
                ", pullRequestId='" + pullRequestId + '\'' +
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
}
