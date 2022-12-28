package com.qxw.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qxw.commonutils.result.R;
import com.qxw.eduservice.entity.EduCourse;
import com.qxw.eduservice.entity.EduTeacher;
import com.qxw.eduservice.service.EduCourseService;
import com.qxw.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/front")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("/index")
    public R indexInfo(){

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

        return R.ok().data("eduList",eduCourses).data("teacherList",eduTeachers);
    }

}
