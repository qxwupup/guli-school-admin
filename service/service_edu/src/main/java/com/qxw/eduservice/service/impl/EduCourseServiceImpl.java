package com.qxw.eduservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxw.eduservice.entity.EduCourse;
import com.qxw.eduservice.entity.EduCourseDescription;
import com.qxw.eduservice.entity.frontvo.CourseDetailVo;
import com.qxw.eduservice.entity.frontvo.CourseFrontVo;
import com.qxw.eduservice.entity.vo.CourseInfoVo;
import com.qxw.eduservice.entity.vo.CoursePublishVo;
import com.qxw.eduservice.mapper.EduCourseMapper;
import com.qxw.eduservice.service.EduChapterService;
import com.qxw.eduservice.service.EduCourseDescriptionService;
import com.qxw.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxw.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduChapterService chapterService;




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

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public boolean updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        boolean flag = courseDescriptionService.updateById(eduCourseDescription);
        return update > 0 && flag;
    }

    @Override
    public CoursePublishVo publishCourseInfo(String courseId) {
        return baseMapper.getPublishCourseInfo(courseId);
    }

    @Override
    public boolean removeCourse(String courseId) {

        boolean video = videoService.removeByCourseId(courseId);

        boolean chapter = chapterService.removeByCourseId(courseId);

        boolean description = courseDescriptionService.removeById(courseId);

        int course = baseMapper.deleteById(courseId);
        return course>0 && description && chapter && video;
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo vo) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        wrapper.eq(StrUtil.isNotEmpty(vo.getSubjectParentId()),"subject_parent_id",vo.getSubjectParentId())
                .eq(StrUtil.isNotEmpty(vo.getSubjectId()),"subject_id",vo.getSubjectId())
                .orderByDesc(StrUtil.isNotEmpty(vo.getBuyCountSort()),"buy_count")
                .orderByDesc(StrUtil.isNotEmpty(vo.getGmtCreateSort()),"gmt_create")
                .orderByDesc(StrUtil.isNotEmpty(vo.getPriceSort()),"price");

        baseMapper.selectPage(coursePage,wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("items",coursePage.getRecords());
        map.put("current",coursePage.getCurrent());
        map.put("pages",coursePage.getPages());
        map.put("size",coursePage.getSize());
        map.put("total",coursePage.getTotal());
        map.put("hasNext",coursePage.hasNext());
        map.put("hasPrevious",coursePage.hasPrevious());
        return map;
    }

    @Override
    public CourseDetailVo getCourseFrontInfo(String courseId) {
        return baseMapper.getCourseFrontInfo(courseId);
    }
}
