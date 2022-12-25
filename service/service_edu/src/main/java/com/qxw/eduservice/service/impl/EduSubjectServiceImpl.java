package com.qxw.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qxw.eduservice.entity.EduSubject;
import com.qxw.eduservice.entity.excel.SubjectData;
import com.qxw.eduservice.entity.subject.OneSubject;
import com.qxw.eduservice.entity.subject.TwoSubject;
import com.qxw.eduservice.listener.SubjectExcelListener;
import com.qxw.eduservice.mapper.EduSubjectMapper;
import com.qxw.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author qxw
 * @since 2022-12-25
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public boolean saveSubject(MultipartFile file, EduSubjectService subjectService) {
        boolean flag = true;
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjects = baseMapper.selectList(wrapperOne);

        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubjects = baseMapper.selectList(wrapperTwo);

        List<OneSubject> fianlSunjectList = new ArrayList<>();

        for (EduSubject one : oneSubjects) {
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(one, oneSubject);
            List<TwoSubject> children = new ArrayList<>();
            for (EduSubject two : twoSubjects) {
                if (two.getParentId().equals(one.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(two, twoSubject);
                    children.add(twoSubject);
                }
            }
            oneSubject.setChildren(children);
            fianlSunjectList.add(oneSubject);
        }

        return fianlSunjectList;
    }
}
