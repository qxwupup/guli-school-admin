package com.qxw.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxw.common.core.result.Result;
import com.qxw.eduservice.entity.EduTeacher;
import com.qxw.eduservice.entity.vo.TeacherQuery;
import com.qxw.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/eduservice/teacher")
@Tag(name = "讲师相关接口", description = "讲师相关接口")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("/findAll")
    @Operation(summary = "查询讲师表所有数据", description = "查询讲师表所有数据")
    public Result<?> findAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return Result.builder().put("items", list).build();
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "按id逻辑删除讲师信息", description = "按id逻辑删除讲师信息")
    public Result<?> removeTeacher(@PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        return Result.status(flag);
    }


    @GetMapping("/pageTeacher/{current}/{size}")
    @Operation(summary = "分页查询讲师表数据", description = "分页查询讲师表数据")
    public Result<?> pageListTeacher(@PathVariable Long current, @PathVariable Long size) {
        Page<EduTeacher> pageTeacher = new Page<>(current, size);
        teacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return Result.builder().put("total", total).put("rows", records).build();
    }


    @PostMapping("/pageTeacherCondition/{current}/{size}")
    @Operation(summary = "带条件的分页查询讲师表数据", description = "带条件的分页查询讲师表数据")
    public Result<?> pageTeacherCondition(@PathVariable Long current, @PathVariable Long size, @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> pageTeacher = new Page<>(current, size);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.like(!StringUtils.isEmpty(teacherQuery.getName()), "name", teacherQuery.getName());
        wrapper.eq(!StringUtils.isEmpty(teacherQuery.getLevel()), "level", teacherQuery.getLevel());
        wrapper.ge(!StringUtils.isEmpty(teacherQuery.getBegin()), "gmt_create", teacherQuery.getBegin());
        wrapper.le(!StringUtils.isEmpty(teacherQuery.getEnd()), "gmt_create", teacherQuery.getEnd());

        teacherService.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return Result.builder().put("total", total).put("rows", records).build();
    }

    @PostMapping("/addTeacher")
    @Operation(summary = "添加讲师", description = "添加讲师")
    public Result<?> addTeacher(@RequestBody EduTeacher eduTeacher) {
        return Result.status(teacherService.save(eduTeacher));
    }


    @GetMapping("/getTeacher/{id}")
    @Operation(summary = "根据id查询讲师信息", description = "根据id查询讲师信息")
    public Result<?> getTeacher(@PathVariable Long id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return Result.builder().put("teacher",eduTeacher).build();
    }

    @PostMapping("/updateTeacher")
    @Operation(summary = "修改讲师信息", description = "修改讲师信息")
    public Result<?> updateTeacher(@RequestBody EduTeacher eduTeacher) {
        return Result.status(teacherService.updateById(eduTeacher));
    }

}

