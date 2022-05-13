package com.devmo.autogradingbe.dm;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "autograding_solution")
public class AutogradingSolutionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String studentId;

    private String studentEmail;

    private String gitUserName;

    private String repositoryUrl;

    private String branchName;

    private String pullRequestId;

    private String language;

    private LocalDateTime submittedOn;

    private LocalDateTime deadline;

    private BigDecimal numberOfPoints;

    private String testsResult;

    private Long assignmentExternalId;

    private boolean uploadToExternalSystem;

    public AutogradingSolutionEntity() {
    }

    public AutogradingSolutionEntity(String studentId,
                                     String studentEmail,
                                     String gitUserName,
                                     String repositoryUrl,
                                     String branchName,
                                     String pullRequestId,
                                     String language,
                                     LocalDateTime submittedOn,
                                     LocalDateTime deadline,
                                     BigDecimal numberOfPoints,
                                     String testsResult,
                                     Long assignmentExternalId,
                                     boolean uploadToExternalSystem) {
        this.studentId = studentId;
        this.studentEmail = studentEmail;
        this.gitUserName = gitUserName;
        this.repositoryUrl = repositoryUrl;
        this.branchName = branchName;
        this.pullRequestId = pullRequestId;
        this.language = language;
        this.submittedOn = submittedOn;
        this.deadline = deadline;
        this.numberOfPoints = numberOfPoints;
        this.testsResult = testsResult;
        this.assignmentExternalId = assignmentExternalId;
        this.uploadToExternalSystem = uploadToExternalSystem;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getSubmittedOn() {
        return submittedOn;
    }

    public void setSubmittedOn(LocalDateTime submittedOn) {
        this.submittedOn = submittedOn;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Column(precision = 4, scale = 2)
    public BigDecimal getNumberOfPoints() {
        return numberOfPoints;
    }

    public void setNumberOfPoints(BigDecimal numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    @Lob
    public String getTestsResult() {
        return testsResult;
    }

    public void setTestsResult(String testsResult) {
        this.testsResult = testsResult;
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

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Long getAssignmentExternalId() {
        return assignmentExternalId;
    }

    public void setAssignmentExternalId(Long assignmentExternalId) {
        this.assignmentExternalId = assignmentExternalId;
    }

    public boolean isUploadToExternalSystem() {
        return uploadToExternalSystem;
    }

    public void setUploadToExternalSystem(boolean uploadToExternalSystem) {
        this.uploadToExternalSystem = uploadToExternalSystem;
    }
}
