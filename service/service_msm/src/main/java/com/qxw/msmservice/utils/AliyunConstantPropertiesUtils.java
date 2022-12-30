package com.qxw.msmservice.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource(value = "classpath:config/aliyun.properties")
@ConfigurationProperties(prefix = "aliyun.oss.file")
@Data
public class AliyunConstantPropertiesUtils implements InitializingBean {

    private String keyId;
    private String keySecret;
    private String signName;
    private String templateCode;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String SIGN_NAME;
    public static String TEMPLATE_CODE;



    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        SIGN_NAME = signName;
        TEMPLATE_CODE = templateCode;
    }
}
