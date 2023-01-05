package com.qxw.eduservice.controller;


import com.qxw.common.core.result.Result;
import com.qxw.eduservice.entity.EduChapter;
import com.qxw.eduservice.entity.chapter.ChapterVo;
import com.qxw.eduservice.service.EduChapterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "课程章节相关接口", description = "课程章节相关接口")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    @GetMapping("/getChapterVideo/{courseId}")
    @Operation(summary = "根据课程ID，获取对应课程所有章节信息", description = "传入课程id")
    public Result<?> getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return Result.builder().put("allChapterVideo",list).build();
    }

    @PostMapping("/addChapter")
    @Operation(summary = "添加新的章节信息", description = "添加新的章节信息")
    public Result<?> addChapter(@RequestBody EduChapter eduChapter){
        return Result.status(chapterService.save(eduChapter));
    }

    @GetMapping("/getChaperInfo/{chapterId}")
    @Operation(summary = "根据章节ID，获取章节信息", description = "根据章节ID，获取章节信息")
    public Result<?> getChaperInfo(@PathVariable String chapterId){
        EduChapter eduChapter = chapterService.getById(chapterId);
        return Result.builder().put("chapter",eduChapter).build();
    }

    @PostMapping("/updateChapter")
    @Operation(summary = "更新章节信息", description = "更新章节信息")
    public Result<?> updateChapter(@RequestBody EduChapter eduChapter){
        return Result.status(chapterService.updateById(eduChapter));
    }

    @DeleteMapping("/{chapterId}")
    @Operation(summary = "根据章节ID，删除章节信息", description = "根据章节ID，删除章节信息")
    public Result<?> deleteChapter(@PathVariable String chapterId){
        //此处为何不写删除小节视频？
        //因为现在删除规则是，章节下没有小节，才能进行章节删除
        //所以删除章节时，不需要考虑删除小节及其下视频信息
        boolean flag = chapterService.deleteChapter(chapterId);
        return Result.success(flag);
    }

}

