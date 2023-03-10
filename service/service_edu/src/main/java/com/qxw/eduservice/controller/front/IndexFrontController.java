package com.qxw.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qxw.common.core.result.Result;
import com.qxw.eduservice.entity.EduCourse;
import com.qxw.eduservice.entity.EduTeacher;
import com.qxw.eduservice.service.EduCourseService;
import com.qxw.eduservice.service.EduTeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/front")
@Tag(name = "前端获取课程，讲师展示信息相关接口", description = "前端获取课程，讲师展示信息相关接口")
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("/index")
    @Operation(summary = "获取课程，讲师展示信息", description = "获取课程，讲师展示信息")
    public Result<?> indexInfo(){

        //按课程浏览数进行前8排序，并在首页展示
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("view_count");
        courseWrapper.last("limit 8");
        List<EduCourse> eduCourses = courseService.list(courseWrapper);

        //前4个讲师
        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("id");
        courseWrapper.last("limit 4");
        List<EduTeacher> eduTeachers = teacherService.list(teacherWrapper);

        return  Result.builder().put("eduList",eduCourses).put("teacherList",eduTeachers).build();
    }

}
