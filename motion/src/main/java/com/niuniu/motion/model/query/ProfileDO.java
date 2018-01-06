package com.niuniu.motion.model.query;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "t_profile")
public class ProfileDO {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "account_id")
    private String account;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "gender")
    private Integer gender;
    @Column(name = "age")
    private Integer age;
    @Column(name = "avatar", length = 1000)
    private String avatar;

    public String getAccount() {
        return account;
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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
