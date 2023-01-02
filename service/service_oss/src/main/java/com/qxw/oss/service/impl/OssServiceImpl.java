package com.qxw.oss.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.qxw.common.core.config.AliyunConstantProperties;
import com.qxw.oss.service.OssService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {


    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = AliyunConstantProperties.END_POINT;
        String accessKeyId = AliyunConstantProperties.ACCESS_KEY_ID;
        String accessKeySecret = AliyunConstantProperties.ACCESS_KEY_SECRET;
        String bucketName = AliyunConstantProperties.BUCKET_NAME;
        String filePath = AliyunConstantProperties.FILE_PATH;

        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        String datePath = new DateTime().toString("yyyy-MM-dd");
        String fileName = file.getOriginalFilename();
        fileName = filePath+datePath+"/"+uuid+fileName.substring(fileName.lastIndexOf("."));


        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String url = "";
        try {
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(bucketName, fileName, inputStream);
            url = "https://"+bucketName+"."+endpoint+"/"+fileName;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        }  catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
}
