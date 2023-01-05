package com.qxw.acl.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxw.acl.entity.Role;
import com.qxw.acl.service.RoleService;
import com.qxw.common.core.result.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *

 */
@RestController
@RequestMapping("/admin/acl/role")
@Tag(name = "用户角色管理相关接口", description = "用户角色管理相关接口")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @GetMapping("/{page}/{limit}")
    @Operation(summary = "获取角色分页列表", description = "获取角色分页列表")
    public Result<?> index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            Role role) {
        Page<Role> pageParam = new Page<>(page, limit);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(role.getRoleName())) {
            wrapper.like("role_name",role.getRoleName());
        }
        roleService.page(pageParam,wrapper);
        return Result.builder().put("items", pageParam.getRecords()).put("total", pageParam.getTotal()).build();
    }


    @GetMapping("get/{id}")
    @Operation(summary = "通过角色ID，获取角色", description = "通过角色ID，获取角色")
    public Result<?> get(@PathVariable String id) {
        Role role = roleService.getById(id);
        return Result.data( role);
    }

    @PostMapping("save")
    @Operation(summary = "新增角色", description = "新增角色")
    public Result<?> save(@RequestBody Role role) {
        roleService.save(role);
        return Result.data();
    }

    @PutMapping("update")
    @Operation(summary = "修改角色", description = "修改角色")
    public Result<?> updateById(@RequestBody Role role) {
        roleService.updateById(role);
        return Result.data();
    }

    @DeleteMapping("remove/{id}")
    @Operation(summary = "删除角色", description = "删除角色")
    public Result<?> remove(@PathVariable String id) {
        roleService.removeById(id);
        return Result.data();
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除角色", description = "批量删除角色")
    public Result batchRemove(@RequestBody List<String> idList) {
        roleService.removeByIds(idList);
        return Result.data();
    }
}

