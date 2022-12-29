package com.qxw.eduservice.feignclient;

import com.qxw.common.core.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    /** 使用@PathVariable时，必须指定路径参数名称
     * */
    @DeleteMapping("/eduvod/video/remove/{videoId}")
    public Result<?> removeVideo(@PathVariable("videoId") String videoId);

    @DeleteMapping("/eduvod/video/remove/batch")
    public Result<?> removeBatchVideo(@RequestParam("videoIdList") List<String> videoIdList);
}
