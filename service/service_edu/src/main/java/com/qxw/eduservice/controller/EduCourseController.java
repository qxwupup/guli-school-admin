package com.qxw.eduservice.controller;


import com.alibaba.excel.util.StringUtils;
import com.qxw.commonutils.R;
import com.qxw.eduservice.entity.EduCourse;
import com.qxw.eduservice.entity.vo.CourseInfoVo;
import com.qxw.eduservice.entity.vo.CoursePublishVo;
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

    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo =  courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        boolean flag = courseService.updateCourseInfo(courseInfoVo);
        return flag?R.ok():R.error();
    }

    @GetMapping("/getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId){
        CoursePublishVo coursePublishVo =  courseService.publishCourseInfo(courseId);
        return R.ok().data("publishCourse",coursePublishVo);
    }

}

