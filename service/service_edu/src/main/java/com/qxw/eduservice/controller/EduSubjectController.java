package com.qxw.eduservice.controller;


import com.qxw.commonutils.R;
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
    public R addSubject(MultipartFile file) {
        boolean flag = subjectService.saveSubject(file, subjectService);
        return flag ? R.ok() : R.error();
    }

    @GetMapping("/getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

