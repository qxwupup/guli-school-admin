package com.qxw.eduservice.controller;


import com.alibaba.excel.util.StringUtils;
import com.qxw.commonutils.R;
import com.qxw.eduservice.entity.vo.CourseInfoVo;
import com.qxw.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author qxw
 * @since 2022-12-25
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {

        String id = courseService.saveCourseInfo(courseInfoVo);

        return !StringUtils.isEmpty(id) ? R.ok().data("courseId",id) : R.error().data("courseId",id);
    }

}

