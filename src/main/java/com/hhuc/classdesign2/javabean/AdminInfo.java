package com.hhuc.classdesign2.javabean;

public class AdminInfo {
    private Long adminId;
    private String adminNo;
    private String password;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getAdminNo() {
        return adminNo;
    }

    public void setAdminNo(String adminNo) {
        this.adminNo = adminNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AdminInfo{" +
                "adminId=" + adminId +
                ", adminNo='" + adminNo + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
