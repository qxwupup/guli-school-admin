package com.qxw.msmservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@PropertySource(value = "classpath:config/redis.properties")
@Data
public class RedisProperties {
    /**
     * Database index used by the connection factory.
     */
    private int database = 0;
    /**
     * Redis server host.
     */
    private String host;

    /**
     * Login password of the redis server.
     */
    private String password;

    /**
     * Redis server port.
     */
    private int port;
}
