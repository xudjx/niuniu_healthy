package com.niuniu.motion.common.constant;

public enum Role {

    USER        (0, "用户", "user"),
    ADMIN       (1, "管理员", "admin");

    private final int    role;
    private final String name;
    private final String identify;

    Role (int role, String name, String identify) {
        this.role    = role;
        this.name    = name;
        this.identify = identify;
    }

    public int getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getIdentify() {
        return identify;
    }
}
