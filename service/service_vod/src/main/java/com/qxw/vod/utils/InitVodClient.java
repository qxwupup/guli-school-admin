package com.qxw.vod.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;

public class InitVodClient {

    public static DefaultAcsClient initVodClient(String accessKeyId,String accessKeySecret){
        String regionId="cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId,accessKeyId,accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }


}
