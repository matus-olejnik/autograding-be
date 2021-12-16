package com.devmo.autogradingbe.bm;

public class BuildOutputResponse {

    private String studentId;
    private String output;

    public BuildOutputResponse(String studentId, String output) {
        this.studentId = studentId;
        this.output = output;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
