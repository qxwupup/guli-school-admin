package com.qxw.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxw.common.core.result.Result;
import com.qxw.eduservice.entity.EduCourse;
import com.qxw.eduservice.entity.EduTeacher;
import com.qxw.eduservice.service.EduCourseService;
import com.qxw.eduservice.service.EduTeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/front/teacher")
@Tag(name = "前台讲师信息展示获取相关接口", description = "前台讲师信息展示获取相关接口")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService  courseService;

    @PostMapping("/page/{current}/{size}")
    @Operation(summary = "讲师信息分页查询", description = "讲师信息分页查询")
    //TODO  最好不返回实体类，待改善
    public Result<?> teacherFrontPage(@PathVariable Long current,@PathVariable Long size){
        Page<EduTeacher> teacherPage = new Page<>(current,size);

        Map<String,Object> map = teacherService.getTeacherFrontList(teacherPage);

        return Result.data(map);
    }

    @GetMapping("/info/{teacherId}")
    @Operation(summary = "根据讲师ID，获取讲师信息", description = "根据讲师ID，获取讲师信息")
    //TODO  最好不返回实体类，待改善
    public Result<?> getTeacherInfo(@PathVariable String teacherId){

        EduTeacher teacher = teacherService.getById(teacherId);

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);

        return  Result.builder().put("teacher",teacher).put("courseList",courseList).build();
    }
}
