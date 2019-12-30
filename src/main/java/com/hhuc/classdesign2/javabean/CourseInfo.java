package com.hhuc.classdesign2.javabean;

public class CourseInfo {
    private Long courseId;
    private Long teacherId;
    private Long schoolId;
    private String courseName;
    private String courseNo;
    private String specialty;
    private Integer clickCount;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    @Override
    public String toString() {
        return "CourseInfo{" +
                "courseId=" + courseId +
                ", teacherId=" + teacherId +
                ", schoolId=" + schoolId +
                ", courseName='" + courseName + '\'' +
                ", courseNo='" + courseNo + '\'' +
                ", specialty='" + specialty + '\'' +
                ", clickCount=" + clickCount +
                '}';
    }
}
