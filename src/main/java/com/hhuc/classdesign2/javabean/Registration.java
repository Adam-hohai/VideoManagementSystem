package com.hhuc.classdesign2.javabean;

public class Registration {
    private Long registrationId;
    private String email;
    private String password;

    public Long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "registrationId=" + registrationId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
