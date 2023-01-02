package com.qxw.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxw.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author qxw
 * @since 2022-12-23
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage);
}
