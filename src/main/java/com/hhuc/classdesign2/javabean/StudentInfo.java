package com.hhuc.classdesign2.javabean;

public class StudentInfo {
    private Long studentId;
    private Long schoolId;
    private Long registrationId;
    private String studentName;
    private String studentNo;
    private String specialty;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "studentId=" + studentId +
                ", schoolId=" + schoolId +
                ", registrationId=" + registrationId +
                ", studentName='" + studentName + '\'' +
                ", studentNo='" + studentNo + '\'' +
                ", specialty='" + specialty + '\'' +
                '}';
    }
}
