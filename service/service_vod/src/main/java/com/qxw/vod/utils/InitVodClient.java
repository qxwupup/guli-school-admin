package com.qxw.vod.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.qxw.common.core.config.AliyunConstantProperties;

public class InitVodClient {

    public static DefaultAcsClient initVodClient(){
        String regionId="cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, AliyunConstantProperties.ACCESS_KEY_ID, AliyunConstantProperties.ACCESS_KEY_SECRET);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }


}
