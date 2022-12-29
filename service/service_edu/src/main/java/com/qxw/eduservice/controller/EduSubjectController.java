package com.qxw.eduservice.controller;


import com.qxw.common.core.result.Result;
import com.qxw.eduservice.entity.subject.OneSubject;
import com.qxw.eduservice.service.EduSubjectService;
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
@CrossOrigin
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("/addSubject")
    public Result<?> addSubject(MultipartFile file) {
        boolean flag = subjectService.saveSubject(file, subjectService);
        return Result.status(flag);
    }

    @GetMapping("/getAllSubject")
    public Result<?> getAllSubject(){
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return Result.builder().put("list",list).build();
    }
}

