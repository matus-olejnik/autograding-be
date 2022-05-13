package com.devmo.autogradingbe.service;

public interface EvaluationSvc {

    void sendMessageToStudent(String pullRequestIdentifier, String repositoryUrl, String commentMessage) throws Exception;
}
