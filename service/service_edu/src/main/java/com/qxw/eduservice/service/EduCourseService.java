package com.qxw.eduservice.service;

import com.qxw.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qxw.eduservice.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author qxw
 * @since 2022-12-25
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);
}
