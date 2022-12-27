package com.qxw.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxw.commonutils.result.R;
import com.qxw.eduservice.entity.EduTeacher;
import com.qxw.eduservice.entity.vo.TeacherQuery;
import com.qxw.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author qxw
 * @since 2022-12-23
 */
@Api(description = "讲师管理")
@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "查询讲师表所有数据")
    @GetMapping("/findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "按id逻辑删除讲师信息")
    @DeleteMapping("/{id}")
    public R removeTeacher(@PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        return flag ? R.ok() : R.error();
    }

    @ApiOperation(value = "分页查询讲师表数据")
    @GetMapping("/pageTeacher/{current}/{size}")
    public R pageListTeacher(@PathVariable Long current, @PathVariable Long size) {
        Page<EduTeacher> pageTeacher = new Page<>(current, size);
        teacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "带条件的分页查询讲师表数据")
    @PostMapping("/pageTeacherCondition/{current}/{size}")
    public R pageTeacherCondition(@PathVariable Long current, @PathVariable Long size, @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> pageTeacher = new Page<>(current, size);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.like(!StringUtils.isEmpty(teacherQuery.getName()), "name", teacherQuery.getName());
        wrapper.eq(!StringUtils.isEmpty(teacherQuery.getLevel()), "level", teacherQuery.getLevel());
        wrapper.ge(!StringUtils.isEmpty(teacherQuery.getBegin()), "gmt_create", teacherQuery.getBegin());
        wrapper.le(!StringUtils.isEmpty(teacherQuery.getEnd()), "gmt_create", teacherQuery.getEnd());

        teacherService.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }


    @ApiOperation(value = "添加讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        return teacherService.save(eduTeacher) ? R.ok() : R.error();
    }

    @ApiOperation(value = "根据id查询讲师信息")
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable Long id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    @ApiOperation(value = "修改讲师信息")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        return teacherService.updateById(eduTeacher) ? R.ok() : R.error();
    }

}

