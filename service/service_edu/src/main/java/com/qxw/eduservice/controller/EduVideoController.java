package com.qxw.eduservice.controller;


import com.qxw.commonutils.R;
import com.qxw.eduservice.entity.EduVideo;
import com.qxw.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
       return videoService.save(eduVideo)?R.ok():R.error();
    }

    //todo 还要删除视频
    @DeleteMapping("/{videoId}")
    public R deleteVideo(@PathVariable String videoId){
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

