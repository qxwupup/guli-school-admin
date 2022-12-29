package com.qxw.oss.controller;

import com.qxw.common.core.result.Result;
import com.qxw.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping
    public Result<?> uploadOssFile(MultipartFile file){

        String url =  ossService.uploadFileAvatar(file);

        return Result.builder().put("url",url).build();
    }

}
