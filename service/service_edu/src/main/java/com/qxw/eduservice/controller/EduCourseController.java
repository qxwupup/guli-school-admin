package com.qxw.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxw.common.core.result.Result;
import com.qxw.eduservice.constants.CourseStatus;
import com.qxw.eduservice.entity.EduCourse;
import com.qxw.eduservice.entity.vo.CourseInfoVo;
import com.qxw.eduservice.entity.vo.CoursePublishVo;
import com.qxw.eduservice.entity.vo.CourseQuery;
import com.qxw.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //课程列表
    @ApiOperation(value = "带条件的分页查询课程表数据")
    @PostMapping("/pageCourseCondition/{current}/{size}")
    public Result<?> pageTeacherCondition(@PathVariable Long current, @PathVariable Long size, @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> pageCourse = new Page<>(current, size);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.like(!org.springframework.util.StringUtils.isEmpty(courseQuery.getTitle()), "title", courseQuery.getTitle());
        wrapper.eq(!org.springframework.util.StringUtils.isEmpty(courseQuery.getStatus()), "status", courseQuery.getStatus());
        courseService.page(pageCourse, wrapper);
        long total = pageCourse.getTotal();
        List<EduCourse> records = pageCourse.getRecords();
        return Result.builder().put("total", total).put("rows", records).build();
    }

    @PostMapping("/addCourseInfo")
    public Result<?> addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {

        String id = courseService.saveCourseInfo(courseInfoVo);

        return Result.builder().put("courseId", id).build() ;
    }

    @GetMapping("/getCourseInfo/{courseId}")
    public Result<?> getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return Result.builder().put("courseInfoVo", courseInfoVo).build();
    }

    @PostMapping("/updateCourseInfo")
    public Result<?> updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        boolean flag = courseService.updateCourseInfo(courseInfoVo);
        return Result.status(flag);
    }

    @GetMapping("/getPublishCourseInfo/{courseId}")
    public Result<?> getPublishCourseInfo(@PathVariable String courseId) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(courseId);
        return Result.builder().put("publishCourse", coursePublishVo).build();
    }

    @PostMapping("/publishCourse/{courseId}")
    public Result<?> publishCourse(@PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus(CourseStatus.COURSE_STATUS_NORMAL);
        return Result.status(courseService.updateById(eduCourse));
    }

    @DeleteMapping("/{courseId}")
    public Result<?> deleteCourse(@PathVariable String courseId) {
        boolean flag = courseService.removeCourse(courseId);
        return Result.status(flag);
    }
}

