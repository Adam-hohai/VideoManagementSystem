package com.hhuc.classdesign2.javabean;

import org.springframework.stereotype.Component;

//@Component
public class TeacherInfo {
    private Long teacherId;
    private Long schoolId;
    private Long registrationId;
    private String teacherNo;
    private String teacherName;

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

    public Long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }

    public String getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(String teacherNo) {
        this.teacherNo = teacherNo;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public String toString() {
        return "TeacherInfo{" +
                "teacherId=" + teacherId +
                ", schoolId=" + schoolId +
                ", registrationId=" + registrationId +
                ", teacherNo='" + teacherNo + '\'' +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }
}
