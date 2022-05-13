package com.devmo.autogradingbe.bm.response;

import com.google.gson.Gson;

public class TestResult {

    private String testIdentifier;

    private int numberOfPoints;

    private String expectedValue;

    private String actualValue;

    private TestResultTypeEnu testResult;

    private String details;

    public TestResult() {
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
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

    public String getExpectedValue() {
        return expectedValue;
    }

    public void setExpectedValue(String expectedValue) {
        this.expectedValue = expectedValue;
    }

    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
