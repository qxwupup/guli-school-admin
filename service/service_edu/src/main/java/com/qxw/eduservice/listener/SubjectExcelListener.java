package com.qxw.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qxw.eduservice.entity.EduSubject;
import com.qxw.eduservice.entity.excel.SubjectData;
import com.qxw.eduservice.service.EduSubjectService;


public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    private EduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new RuntimeException("文件数据为空");
        }

        EduSubject one = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (one == null) {
            one = new EduSubject();
            one.setParentId("0");
            one.setTitle(subjectData.getOneSubjectName());
            subjectService.save(one);
        }

        String pid = one.getId();
        EduSubject two = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if (two == null) {
            two = new EduSubject();
            two.setParentId(pid);
            two.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(two);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    private EduSubject existOneSubject(EduSubjectService subjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name).eq("parent_id", "0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    private EduSubject existTwoSubject(EduSubjectService subjectService, String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name).eq("parent_id", pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }
}
