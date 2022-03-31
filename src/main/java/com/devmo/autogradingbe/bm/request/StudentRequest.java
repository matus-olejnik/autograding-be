package com.devmo.autogradingbe.bm.request;

public class StudentRequest {

    private String studentId;
    private String output;

    public StudentRequest(String studentId, String output) {
        this.studentId = studentId;
        this.output = output;
    }

    @Override
    public String toString() {
        return "BuildOutputRequest{" +
                "studentId='" + studentId + '\'' +
                ", output='" + output + '\'' +
                '}';
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
