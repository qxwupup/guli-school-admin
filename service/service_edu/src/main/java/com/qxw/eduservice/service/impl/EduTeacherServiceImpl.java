package com.qxw.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxw.eduservice.entity.EduTeacher;
import com.qxw.eduservice.mapper.EduTeacherMapper;
import com.qxw.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author qxw
 * @since 2022-12-23
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(teacherPage,wrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("items",teacherPage.getRecords());
        map.put("current",teacherPage.getCurrent());
        map.put("pages",teacherPage.getPages());
        map.put("size",teacherPage.getSize());
        map.put("total",teacherPage.getTotal());
        map.put("hasNext",teacherPage.hasNext());
        map.put("hasPrevious",teacherPage.hasPrevious());
        return map;
    }
}
