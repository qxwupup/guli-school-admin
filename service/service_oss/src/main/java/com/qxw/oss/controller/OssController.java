package com.qxw.oss.controller;

import com.qxw.commonutils.R;
import com.qxw.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    public R uploadOssFile(MultipartFile file){

        String url =  ossService.uploadFileAvatar(file);

        return StringUtils.isEmpty(url)?R.error().data("url",url):R.ok().data("url",url);
    }

}
