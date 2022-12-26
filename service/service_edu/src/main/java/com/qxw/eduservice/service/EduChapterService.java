package com.qxw.eduservice.service;

import com.qxw.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qxw.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author qxw
 * @since 2022-12-25
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);
}
