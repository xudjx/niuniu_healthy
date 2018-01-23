package com.niuniu.motion.common.pojo;

import org.springframework.data.redis.core.StringRedisTemplate;

public class NiuniuRedisTemplate {

    private StringRedisTemplate template;

    public StringRedisTemplate getTemplate() {
        return template;
    }

    public void setTemplate(StringRedisTemplate template) {
        this.template = template;
    }
}
