package com.qxw.eduservice.service.impl;

import com.qxw.eduservice.entity.EduCourse;
import com.qxw.eduservice.entity.EduCourseDescription;
import com.qxw.eduservice.entity.vo.CourseInfoVo;
import com.qxw.eduservice.mapper.EduCourseMapper;
import com.qxw.eduservice.service.EduCourseDescriptionService;
import com.qxw.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author qxw
 * @since 2022-12-25
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new RuntimeException("EduCourseDescriptionService.saveCourseInfo 添加失败");
        }
        String cid = eduCourse.getId();
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);

        return cid;
    }
}
