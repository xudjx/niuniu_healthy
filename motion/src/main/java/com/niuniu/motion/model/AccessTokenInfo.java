package com.niuniu.motion.model;

public class AccessTokenInfo {
    private Long tokenId;
    private Integer role;
    private Long expireTime;

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String toString()
    {
        StringBuilder log = new StringBuilder();
        log.append("tokenId:").append(tokenId);
        log.append(",role:").append(role);

        return log.toString();
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }
}
