package com.hhuc.classdesign2.javabean;

public class SubscriptionInfo {
    private Long subscriptionId;
    private Long studentId;
    private Long courseId;
    private String subscriptionTime;

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
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

    public String getSubscriptionTime() {
        return subscriptionTime;
    }

    public void setSubscriptionTime(String subscriptionTime) {
        this.subscriptionTime = subscriptionTime;
    }

    @Override
    public String toString() {
        return "SubscriptionInfo{" +
                "subscriptionId=" + subscriptionId +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", subscriptionTime='" + subscriptionTime + '\'' +
                '}';
    }
}
