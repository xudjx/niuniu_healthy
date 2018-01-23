package com.niuniu.motion.config;

import com.niuniu.motion.common.pojo.NiuniuRedisTemplate;
import com.niuniu.motion.server.controller.MyRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.redis", ignoreUnknownFields = true)
public class RedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    String host;
    Integer port;
    String password;

    public JedisConnectionFactory jedisConnectionFactory() {
        logger.info("RedisConfig: host={}, port={}, password={}", host, port, password);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPassword(password);
        jedisConnectionFactory.setTimeout(0);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setDatabase(0);
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }

    @Bean
    public NiuniuRedisTemplate redisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory());
        template.afterPropertiesSet();
        NiuniuRedisTemplate niuRedisTemplate = new NiuniuRedisTemplate();
        niuRedisTemplate.setTemplate(template);
        return niuRedisTemplate;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
