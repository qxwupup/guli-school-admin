package com.qxw.eduservice.feignclient;

import com.qxw.common.core.result.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient{

    @Override
    public Result<?> removeVideo(String videoId) {
        return Result.error("视频删除出错");
    }

    @Override
    public Result<?> removeBatchVideo(List<String> videoIdList) {
        return Result.error("视频批量删除出错");
    }
}
