package com.niuniu.motion.common.constant;

public enum Platform {

    APP       (1, "手机APP", "APP");

    private final int    platform;
    private final String name;
    private final String identify;

    Platform (int platform, String name, String identify) {
        this.platform    = platform;
        this.name    = name;
        this.identify = identify;
    }

    public int getPlatform() {
        return platform;
    }

    public String getName() {
        return name;
    }

    public String getIdentify() {
        return identify;
    }
}
