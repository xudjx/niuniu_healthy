package com.niuniu.motion.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "auth", ignoreUnknownFields = false)
public class AuthConfig {

    String authCodeSecret;

    String mobAppKey;

    public String getAuthCodeSecret() {
        return authCodeSecret;
    }

    public void setAuthCodeSecret(String authCodeSecret) {
        this.authCodeSecret = authCodeSecret;
    }

    public String getMobAppKey() {
        return mobAppKey;
    }

    public void setMobAppKey(String mobAppKey) {
        this.mobAppKey = mobAppKey;
    }
}
