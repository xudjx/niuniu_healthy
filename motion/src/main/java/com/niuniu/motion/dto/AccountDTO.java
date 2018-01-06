package com.niuniu.motion.dto;

public class AccountDTO extends BaseDTO {

    private int id;
    private String account;
    private String password;

    public AccountDTO() {
    }
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
