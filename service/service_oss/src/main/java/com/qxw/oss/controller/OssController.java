package com.qxw.oss.controller;

import com.qxw.common.core.result.Result;
import com.qxw.oss.service.OssService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@Tag(name = "OSS相关接口", description = "OSS相关接口")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping
    @Operation(summary = "上传用户头像到OSS", description = "上传用户头像到OSS")
    public Result<?> uploadOssFile(MultipartFile file){

        String url =  ossService.uploadFileAvatar(file);

        return Result.builder().put("url",url).build();
    }

}
