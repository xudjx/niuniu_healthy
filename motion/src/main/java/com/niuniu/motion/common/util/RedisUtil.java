package com.niuniu.motion.common.util;

import com.niuniu.motion.common.constant.RedisKey;
import com.niuniu.motion.common.pojo.NiuniuRedisTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisUtil {

    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    public static String getAccessToken(NiuniuRedisTemplate template, Long accountId) {
        String accessToken = template.getTemplate().opsForValue().get(RedisKey.ACCESS_TOKEN_KEY + String.valueOf(accountId));
        logger.info("token get: accoutId:{}, token: {}", accountId, accessToken);
        return accessToken;
    }

    public static void setAccessToken(NiuniuRedisTemplate template, Long accountId, String accessToken) {
        template.getTemplate().opsForValue().set(RedisKey.ACCESS_TOKEN_KEY + String.valueOf(accountId), accessToken);
        logger.info("token init: accoutId:{}, token: {}", accountId, accessToken);
        return;
    }
}
