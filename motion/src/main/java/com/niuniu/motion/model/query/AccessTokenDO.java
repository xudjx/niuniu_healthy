package com.niuniu.motion.model.query;

import javax.persistence.*;

@Entity
@Table(name = "t_access_token")
public class AccessTokenDO {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "account_id")
    private Long accountId;
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "platform")
    private int platform;
    @Column(name = "expire_time")
    private long expireTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
