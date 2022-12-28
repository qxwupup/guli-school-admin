package com.qxw.vod.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource(value = "classpath:aliyunconfig/aliyun.properties")
@ConfigurationProperties(prefix = "aliyun.oss.file")
@Data
public class AliyunConstantPropertiesUtils implements InitializingBean {

    private String keyId;

    private String keySecret;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;



    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
    }
}
