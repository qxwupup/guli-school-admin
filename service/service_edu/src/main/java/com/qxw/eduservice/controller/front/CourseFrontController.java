package com.qxw.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxw.common.core.result.Result;
import com.qxw.common.core.utils.JwtUtils;
import com.qxw.eduservice.entity.EduCourse;
import com.qxw.eduservice.entity.chapter.ChapterVo;
import com.qxw.eduservice.entity.frontvo.CourseDetailVo;
import com.qxw.eduservice.entity.frontvo.CourseFrontVo;
import com.qxw.eduservice.feignclient.OrderClient;
import com.qxw.eduservice.service.EduChapterService;
import com.qxw.eduservice.service.EduCourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/front/course")
@Tag(name = "前端获取课程信息相关接口", description = "前端获取课程信息相关接口")
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    @PostMapping("/page/{current}/{size}")
    @Operation(summary = "带条件的分页查询课程信息", description = "带条件的分页查询课程信息")
    public Result<?> getFrontCourse(@PathVariable Long current,
                                    @PathVariable Long size,
                                    @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> coursePage = new Page<>(current,size);

        Map<String,Object> map = courseService.getCourseFrontList(coursePage,courseFrontVo);

        return Result.data(map);
    }

    @GetMapping("/info/{courseId}")
    @Operation(summary = "根据课程ID，获取课程相关信息", description = "根据课程ID，获取课程相关信息")
    public Result<?> getCourseInfo(@PathVariable String courseId, HttpServletRequest request){

        CourseDetailVo courseDetailVo = courseService.getCourseFrontInfo(courseId);

        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);


        Boolean isBuy = orderClient.isBuy(courseId, JwtUtils.getMemberIdByJwtToken(request)).getData();

        return Result.builder().put("courseDetailVo",courseDetailVo).put("chapterVideoList",chapterVideoList).put("isBuy",isBuy).build();
    }
}
