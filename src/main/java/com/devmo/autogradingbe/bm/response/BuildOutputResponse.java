package com.devmo.autogradingbe.bm.response;

import java.math.BigDecimal;

public class BuildOutputResponse {

    private BigDecimal numberOfPoints;

    private String testsResult;

    public BuildOutputResponse() {
    }

    public BuildOutputResponse(BigDecimal numberOfPoints, String testsResult) {
        this.numberOfPoints = numberOfPoints;
        this.testsResult = testsResult;
    }

    public BigDecimal getNumberOfPoints() {
        return numberOfPoints;
    }

    public void setNumberOfPoints(BigDecimal numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    public String getTestsResult() {
        return testsResult;
    }

    public void setTestsResult(String testsResult) {
        this.testsResult = testsResult;
    }
}
