package com.qxw.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.qxw.commonutils.result.R;
import com.qxw.vod.service.VodService;
import com.qxw.vod.utils.AliyunConstantPropertiesUtils;
import com.qxw.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("/uploadVideo")
    public R uploadVideo(MultipartFile file){

        String videoId = vodService.uploadVideo(file);

        return R.ok().data("videoId",videoId);
    }

    @DeleteMapping("/remove/{videoId}")
    public R removeVideo(@PathVariable String videoId){
       try {
           DefaultAcsClient client = InitVodClient.initVodClient(AliyunConstantPropertiesUtils.ACCESS_KEY_ID, AliyunConstantPropertiesUtils.ACCESS_KEY_SECRET);
           DeleteVideoRequest request= new DeleteVideoRequest();
           request.setVideoIds(videoId);
           client.getAcsResponse(request);
           return R.ok();
       }catch (Exception e){
           e.printStackTrace();
           return R.error();
       }
    }
}
