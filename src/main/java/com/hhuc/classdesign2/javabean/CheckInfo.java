package com.hhuc.classdesign2.javabean;

public class CheckInfo {
    private Long checkId;
    private Long courseId;
    private Long adminId;
    private Integer status;

    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CheckInfo{" +
                "checkId=" + checkId +
                ", courseId=" + courseId +
                ", adminId=" + adminId +
                ", status=" + status +
                '}';
    }
}
