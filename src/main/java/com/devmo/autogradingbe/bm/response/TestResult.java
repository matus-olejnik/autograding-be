package com.devmo.autogradingbe.bm.response;

import com.google.gson.Gson;

public class TestResult {

    private String studentIdentifier;
    private String testIdentifier;
    private int numberOfPoints;
    private TestResultTypeEnu testResult;

    public TestResult(String studentIdentifier, String testIdentifier, int numberOfPoints, TestResultTypeEnu testResult) {
        this.studentIdentifier = studentIdentifier;
        this.testIdentifier = testIdentifier;
        this.numberOfPoints = numberOfPoints;
        this.testResult = testResult;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getStudentIdentifier() {
        return studentIdentifier;
    }

    public void setStudentIdentifier(String studentIdentifier) {
        this.studentIdentifier = studentIdentifier;
    }

    public String getTestIdentifier() {
        return testIdentifier;
    }

    public void setTestIdentifier(String testIdentifier) {
        this.testIdentifier = testIdentifier;
    }

    public int getNumberOfPoints() {
        return numberOfPoints;
    }

    public void setNumberOfPoints(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    public TestResultTypeEnu getTestResult() {
        return testResult;
    }

    public void setTestResult(TestResultTypeEnu testResult) {
        this.testResult = testResult;
    }
}
