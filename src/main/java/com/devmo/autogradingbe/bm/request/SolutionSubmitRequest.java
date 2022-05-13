package com.devmo.autogradingbe.bm.request;

public class SolutionSubmitRequest {

    private String studentId;

    private String gitUserName;

    private String repositoryUrl;

    private String branchName;

    private String pullRequestId;

    private String language;

    private String studentEmail;

    private Boolean uploadToExternalSystem;

    private String deadline;

    private Long assignmentExternalId;

    private Boolean staticSolution;

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
                ", studentEmail='" + studentEmail + '\'' +
                ", uploadToExternalSystem=" + uploadToExternalSystem +
                ", deadline='" + deadline + '\'' +
                ", assignmentExternalId=" + assignmentExternalId +
                ", staticSolution=" + staticSolution +
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

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public Boolean getUploadToExternalSystem() {
        return uploadToExternalSystem;
    }

    public void setUploadToExternalSystem(Boolean uploadToExternalSystem) {
        this.uploadToExternalSystem = uploadToExternalSystem;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Long getAssignmentExternalId() {
        return assignmentExternalId;
    }

    public void setAssignmentExternalId(Long assignmentExternalId) {
        this.assignmentExternalId = assignmentExternalId;
    }

    public Boolean getStaticSolution() {
        return staticSolution;
    }

    public void setStaticSolution(Boolean staticSolution) {
        this.staticSolution = staticSolution;
    }
}
