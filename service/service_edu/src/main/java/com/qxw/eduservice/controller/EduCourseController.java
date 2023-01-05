package com.qxw.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxw.common.core.result.Result;
import com.qxw.common.core.vo.OrderCourseVo;
import com.qxw.eduservice.constants.CourseStatus;
import com.qxw.eduservice.entity.EduCourse;
import com.qxw.eduservice.entity.frontvo.CourseDetailVo;
import com.qxw.eduservice.entity.vo.CourseInfoVo;
import com.qxw.eduservice.entity.vo.CoursePublishVo;
import com.qxw.eduservice.entity.vo.CourseQuery;
import com.qxw.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
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
@Tag(name = "课程相关接口", description = "课程相关接口")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;


    @PostMapping("/pageCourseCondition/{current}/{size}")
    @Operation(summary = "带条件的分页查询课程表数据", description = "带条件的分页查询课程表数据")
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
    @Operation(summary = "添加课程信息", description = "添加课程信息")
    public Result<?> addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {

        String id = courseService.saveCourseInfo(courseInfoVo);

        return Result.builder().put("courseId", id).build() ;
    }

    @GetMapping("/getCourseInfo/{courseId}")
    @Operation(summary = "根据课程ID，获取课程信息", description = "根据课程ID，获取课程信息")
    public Result<?> getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return Result.builder().put("courseInfoVo", courseInfoVo).build();
    }

    @PostMapping("/updateCourseInfo")
    @Operation(summary = "更新课程信息", description = "更新课程信息")
    public Result<?> updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        boolean flag = courseService.updateCourseInfo(courseInfoVo);
        return Result.status(flag);
    }

    @GetMapping("/getPublishCourseInfo/{courseId}")
    @Operation(summary = "获取发布课程时，所需的课程所有信息", description = "获取发布课程时，所需的课程所有信息")
    public Result<?> getPublishCourseInfo(@PathVariable String courseId) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(courseId);
        return Result.builder().put("publishCourse", coursePublishVo).build();
    }

    @PostMapping("/publishCourse/{courseId}")
    @Operation(summary = "进行课程发布", description = "进行课程发布")
    public Result<?> publishCourse(@PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus(CourseStatus.COURSE_STATUS_NORMAL);
        return Result.status(courseService.updateById(eduCourse));
    }

    @DeleteMapping("/{courseId}")
    @Operation(summary = "根据课程ID，删除课程信息", description = "根据课程ID，删除课程信息")
    public Result<?> deleteCourse(@PathVariable String courseId) {
        boolean flag = courseService.removeCourse(courseId);
        return Result.status(flag);
    }

    @GetMapping("/order/info/{courseId}")
    @Operation(summary = "根据课程ID，获取订单课程信息", description = "根据课程ID，获取订单课程信息")
    public Result<OrderCourseVo> getOrderCourseInfo(@PathVariable String courseId){
        CourseDetailVo course = courseService.getCourseFrontInfo(courseId);
        OrderCourseVo res = new OrderCourseVo();
        BeanUtils.copyProperties(course,res);
        return  Result.data(res);
    }
}

