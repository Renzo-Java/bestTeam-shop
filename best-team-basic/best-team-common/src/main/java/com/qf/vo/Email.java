package com.qf.vo;

import java.io.Serializable;

public class Email implements Serializable {
    private String emailname;
    private String password;
    private String uuid;
    private Boolean flag;

    public Email(String emailname, String password) {
        this.emailname = emailname;
        this.password = password;
    }

    public String getEmailname() {
        return emailname;
    }

    public void setEmailname(String emailname) {
        this.emailname = emailname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Email{" +
                "emailname='" + emailname + '\'' +
                ", password='" + password + '\'' +
                ", uuid='" + uuid + '\'' +
                ", flag=" + flag +
                '}';
    }
}
