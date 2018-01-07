package com.niuniu.motion.dto;

public class AccountDTO extends BaseDTO {

    private String accountId;
    private String accountName;
    private String password;
    private String authToken;
    private Long authExpire;

    public AccountDTO() {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Long getAuthExpire() {
        return authExpire;
    }

    public void setAuthExpire(Long authExpire) {
        this.authExpire = authExpire;
    }
}
