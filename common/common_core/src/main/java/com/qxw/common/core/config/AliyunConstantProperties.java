package com.qxw.common.core.config;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource(value = "classpath:config/aliyun.properties")
@ConfigurationProperties(prefix = "aliyun.oss.file")
@Data
public class AliyunConstantProperties implements InitializingBean {

    private String keyId;
    private String keySecret;
    private String signName;
    private String templateCode;

    private String endPoint;
    private String bucketName;
    private String filePath;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String SIGN_NAME;
    public static String TEMPLATE_CODE;

    public static String END_POINT;
    public static String BUCKET_NAME;
    public static String FILE_PATH;


    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        SIGN_NAME = signName;
        TEMPLATE_CODE = templateCode;

        END_POINT = endPoint;
        BUCKET_NAME = bucketName;
        FILE_PATH = filePath;
    }
}
