package com.qxw.eduservice.controller;


import com.qxw.common.core.result.Result;
import com.qxw.eduservice.entity.EduVideo;
import com.qxw.eduservice.feignclient.VodClient;
import com.qxw.eduservice.service.EduVideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
@Tag(name = "章节小节相关接口", description = "章节小节相关接口")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    @PostMapping("/addVideo")
    @Operation(summary = "添加小节信息", description = "添加小节信息")
    public Result<?> addVideo(@RequestBody EduVideo eduVideo){
       return Result.status(videoService.save(eduVideo));
    }


    @DeleteMapping("/{videoId}")
    @Operation(summary = "根据小节ID，删除小节信息", description = "根据小节ID，删除小节信息")
    public Result<?> deleteVideo(@PathVariable String videoId){

        String videoSourceId = videoService.getById(videoId).getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            vodClient.removeVideo(videoSourceId);
        }
        return Result.status(videoService.removeById(videoId));
    }

    @PostMapping("/updateVideo")
    @Operation(summary = "更新小节信息", description = "更新小节信息")
    public Result<?> updateVideo(@RequestBody EduVideo eduVideo){
        return Result.status(videoService.updateById(eduVideo));
    }

    @GetMapping("/getVideoInfo/{videoId}")
    @Operation(summary = "根据小节ID，获取小节信息", description = "根据小节ID，获取小节信息")
    public Result<?> getVideoInfo(@PathVariable String videoId){
        EduVideo eduVideo = videoService.getById(videoId);
        return Result.builder().put("video",eduVideo).build();
    }


}

