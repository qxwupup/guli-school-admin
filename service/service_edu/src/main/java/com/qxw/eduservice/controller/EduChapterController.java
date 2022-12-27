package com.qxw.eduservice.controller;


import com.qxw.commonutils.result.R;
import com.qxw.eduservice.entity.EduChapter;
import com.qxw.eduservice.entity.chapter.ChapterVo;
import com.qxw.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author qxw
 * @since 2022-12-25
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        return chapterService.save(eduChapter)? R.ok():R.error();
    }

    @GetMapping("/getChaperInfo/{chapterId}")
    public R getChaperInfo(@PathVariable String chapterId){
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }

    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        return chapterService.updateById(eduChapter)? R.ok():R.error();
    }

    @DeleteMapping("/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean flag = chapterService.deleteChapter(chapterId);
        return flag?R.ok():R.error();
    }

}

