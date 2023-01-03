package com.qxw.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxw.common.core.result.Result;
import com.qxw.eduservice.entity.EduCourse;
import com.qxw.eduservice.entity.chapter.ChapterVo;
import com.qxw.eduservice.entity.frontvo.CourseDetailVo;
import com.qxw.eduservice.entity.frontvo.CourseFrontVo;
import com.qxw.eduservice.service.EduChapterService;
import com.qxw.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/front/course")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;


    @PostMapping("/page/{current}/{size}")
    public Result<?> getFrontCourse(@PathVariable Long current,
                                    @PathVariable Long size,
                                    @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> coursePage = new Page<>(current,size);

        Map<String,Object> map = courseService.getCourseFrontList(coursePage,courseFrontVo);

        return Result.data(map);
    }

    @GetMapping("/info/{courseId}")
    public Result<?> getCourseInfo(@PathVariable String courseId){

        CourseDetailVo courseDetailVo = courseService.getCourseFrontInfo(courseId);

        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        return Result.builder().put("courseDetailVo",courseDetailVo).put("chapterVideoList",chapterVideoList).build();
    }
}
