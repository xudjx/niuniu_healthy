package com.niuniu.motion.dto;

public class ProfileDTO extends BaseDTO {

    private String account;
    private String userName;
    private Integer gender;
    private Integer age;
    private String avatar;

    public String getAccount() {
        return account;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setAccount(String account) {
        this.account = account;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
