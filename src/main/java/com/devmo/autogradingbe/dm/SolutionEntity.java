package com.devmo.autogradingbe.dm;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "solution")
public class SolutionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String studentId;

    private String repositoryUrl;

    private String branchName;

    private String pullRequestId;

    private String language;

    private LocalDateTime submittedOn;

    public SolutionEntity() {
    }

    public SolutionEntity(Long id,
                          String studentId,
                          String repositoryUrl,
                          String branchName,
                          String pullRequestId,
                          LocalDateTime submittedOn,
                          String language) {
        this.id = id;
        this.studentId = studentId;
        this.repositoryUrl = repositoryUrl;
        this.branchName = branchName;
        this.pullRequestId = pullRequestId;
        this.submittedOn = submittedOn;
        this.language = language;
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
}