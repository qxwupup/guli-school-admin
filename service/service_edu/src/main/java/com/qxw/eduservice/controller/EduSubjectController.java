package com.qxw.eduservice.controller;


import com.qxw.common.core.result.Result;
import com.qxw.eduservice.entity.subject.OneSubject;
import com.qxw.eduservice.service.EduSubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author qxw
 * @since 2022-12-25
 */
@RestController
@RequestMapping("/eduservice/subject")
@Tag(name = "课目相关接口", description = "课目相关接口")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("/addSubject")
    @Operation(summary = "通过上传的excel文档，生成课目信息", description = "通过上传的excel文档，生成课目信息")
    public Result<?> addSubject(MultipartFile file) {
        boolean flag = subjectService.saveSubject(file, subjectService);
        return Result.status(flag);
    }

    @GetMapping("/getAllSubject")
    @Operation(summary = "获取所有课目信息", description = "获取所有课目信息")
    public Result<?> getAllSubject(){
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return Result.builder().put("list",list).build();
    }
}

