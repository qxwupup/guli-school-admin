package com.qxw.eduservice.service;

import com.qxw.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qxw.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author qxw
 * @since 2022-12-25
 */
public interface EduSubjectService extends IService<EduSubject> {

    boolean saveSubject(MultipartFile file,EduSubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
