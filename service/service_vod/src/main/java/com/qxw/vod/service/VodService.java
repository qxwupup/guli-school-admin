package com.qxw.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideo(MultipartFile file);

    boolean removeVideoBatch(List<String> videoIdList);

}
