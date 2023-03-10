package com.qxw.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.qxw.common.core.result.Result;
import com.qxw.vod.service.VodService;
import com.qxw.vod.utils.InitVodClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@Tag(name = "视频点播相关接口", description = "视频点播相关接口")
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("/uploadVideo")
    @Operation(summary = "上传视频到阿里云视频点播", description = "上传视频到阿里云视频点播")
    public Result<?> uploadVideo(MultipartFile file) {

        String videoId = vodService.uploadVideo(file);

        return Result.builder().put("videoId", videoId).build();
    }

    @DeleteMapping("/remove/{videoId}")
    @Operation(summary = "根据视频ID，删除视频", description = "根据小节ID，删除视频")
    public Result<?> removeVideo(@PathVariable String videoId) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient();
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            client.getAcsResponse(request);
            return Result.success("刪除视频成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("刪除视频失败");
        }
    }

    @DeleteMapping("/remove/batch")
    @Operation(summary = "批量删除视频", description = "批量删除视频")
    public Result<?> removeBatchVideo(@RequestParam("videoIdList") List<String> videoIdList) {
        boolean flag = vodService.removeVideoBatch(videoIdList);
        return Result.status(flag);
    }

    @GetMapping("/getPlayAuth/{videoId}")
    @Operation(summary = "根据视频ID，获取视频凭证", description = "根据视频ID，获取视频凭证")
    public Result<?> getPlayAuth(@PathVariable String videoId){
        try {
            DefaultAcsClient client = InitVodClient.initVodClient();
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            return Result.data(response.getPlayAuth());
        }catch (Exception e){
            throw  new RuntimeException("获取视频凭证出错");
        }
    }
}
