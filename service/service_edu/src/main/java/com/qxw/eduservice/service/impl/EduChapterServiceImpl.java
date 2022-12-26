package com.qxw.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qxw.eduservice.entity.EduChapter;
import com.qxw.eduservice.entity.EduVideo;
import com.qxw.eduservice.entity.chapter.ChapterVo;
import com.qxw.eduservice.entity.chapter.VideoVo;
import com.qxw.eduservice.mapper.EduChapterMapper;
import com.qxw.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxw.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author qxw
 * @since 2022-12-25
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {


    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);


        List<ChapterVo> finallList = new ArrayList<>();
        for (EduChapter eduChapter : eduChapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            List<VideoVo> children = new ArrayList<>();
            for (EduVideo eduVideo : eduVideoList) {
                if(eduChapter.getId().equals(eduVideo.getChapterId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVo.setFree(eduVideo.getIsFree());
                    children.add(videoVo);
                }
            }
            chapterVo.setChildren(children);
            finallList.add(chapterVo);
        }
        return finallList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        if(count==0){
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }
        return false;
    }

    @Override
    public boolean removeByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        return baseMapper.delete(wrapper)>0;
    }
}
