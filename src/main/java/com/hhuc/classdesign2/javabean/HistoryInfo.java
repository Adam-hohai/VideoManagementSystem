package com.hhuc.classdesign2.javabean;

public class HistoryInfo {
    private Long historyId;
    private Long studentId;
    private Long courseId;
    private String clickTime;

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getClickTime() {
        return clickTime;
    }

    public void setClickTime(String clickTime) {
        this.clickTime = clickTime;
    }

    @Override
    public String toString() {
        return "HistoryInfo{" +
                "historyId=" + historyId +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", clickTime='" + clickTime + '\'' +
                '}';
    }
}
