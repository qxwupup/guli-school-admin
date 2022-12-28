package com.qxw.eduservice.feignclient;

import com.qxw.commonutils.result.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient{

    @Override
    public R removeVideo(String videoId) {
        return R.error().message("视频删除出错");
    }

    @Override
    public R removeBatchVideo(List<String> videoIdList) {
        return R.error().message("视频批量删除出错");
    }
}
