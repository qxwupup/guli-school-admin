package com.qxw.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qxw.eduservice.entity.EduVideo;
import com.qxw.eduservice.feignclient.VodClient;
import com.qxw.eduservice.mapper.EduVideoMapper;
import com.qxw.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

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


    @Autowired
    private VodClient vodClient;

    @Override
    public boolean removeByCourseId(String courseId) {

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapper);
        List<String> videoIds = eduVideoList.stream()
                .filter(e->!StringUtils.isEmpty(e.getVideoSourceId()))
                .map(EduVideo::getVideoSourceId)
                .collect(Collectors.toList());
        if(videoIds.size()>0){
            vodClient.removeBatchVideo(videoIds);
        }
        return baseMapper.delete(wrapper)>0;
    }
}
