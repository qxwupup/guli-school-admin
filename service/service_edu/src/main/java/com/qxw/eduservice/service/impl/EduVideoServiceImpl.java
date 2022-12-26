package com.qxw.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qxw.eduservice.entity.EduVideo;
import com.qxw.eduservice.mapper.EduVideoMapper;
import com.qxw.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author qxw
 * @since 2022-12-25
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    //todo   还需删除视频信息
    @Override
    public boolean removeByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        return baseMapper.delete(wrapper)>0;
    }
}
