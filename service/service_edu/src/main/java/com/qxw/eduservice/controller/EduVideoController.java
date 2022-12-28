package com.qxw.eduservice.controller;


import com.qxw.commonutils.result.R;
import com.qxw.eduservice.entity.EduVideo;
import com.qxw.eduservice.feignclient.VodClient;
import com.qxw.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author qxw
 * @since 2022-12-25
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
       return videoService.save(eduVideo)?R.ok():R.error();
    }


    @DeleteMapping("/{videoId}")
    public R deleteVideo(@PathVariable String videoId){

        String videoSourceId = videoService.getById(videoId).getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            vodClient.removeVideo(videoSourceId);
        }
        return videoService.removeById(videoId)?R.ok():R.error();
    }

    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        return videoService.updateById(eduVideo)?R.ok():R.error();
    }

    @GetMapping("/getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable String videoId){
        EduVideo eduVideo = videoService.getById(videoId);
        return R.ok().data("video",eduVideo);
    }


}

