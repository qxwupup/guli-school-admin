package com.qxw.acl.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxw.acl.entity.User;
import com.qxw.acl.service.RoleService;
import com.qxw.acl.service.UserService;
import com.qxw.common.core.result.Result;
import com.qxw.common.core.utils.MD5Utils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/admin/acl/user")
@Tag(name = "管理用户相关接口", description = "管理用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @GetMapping("/{page}/{limit}")
    @Operation(summary = "获取管理用户分页列表", description = "获取管理用户分页列表")
    public Result<?> index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    User userQueryVo) {
        Page<User> pageParam = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(userQueryVo.getUsername())) {
            wrapper.like("username",userQueryVo.getUsername());
        }

        IPage<User> pageModel = userService.page(pageParam, wrapper);
        return Result.builder().put("items", pageModel.getRecords()).put("total", pageModel.getTotal()).build();
    }

    @PostMapping("save")
    @Operation(summary = "新增管理用户", description = "新增管理用户")
    public Result<?> save(@RequestBody User user) {
        user.setPassword(MD5Utils.encrypt(user.getPassword()));
        userService.save(user);
        return Result.data();
    }

    @PutMapping("update")
    @Operation(summary = "修改管理用户", description = "修改管理用户")
    public Result<?> updateById(@RequestBody User user) {
        userService.updateById(user);
        return Result.data();
    }

    @DeleteMapping("remove/{id}")
    @Operation(summary = "根据管理用户ID，删除管理用户", description = "根据管理用户ID，删除管理用户")
    public Result<?> remove(@PathVariable String id) {
        userService.removeById(id);
        return Result.data();
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除管理用户", description = "批量删除管理用户")
    public Result<?> batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds(idList);
        return Result.data();
    }

    @GetMapping("/toAssign/{userId}")
    @Operation(summary = "根据管理用户ID，获取用户角色数据", description = "根据管理用户ID，获取用户角色数据")
    public Result<?> toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return Result.data(roleMap);
    }

    @PostMapping("/doAssign")
    @Operation(summary = "根据用户分配角色", description = "根据用户分配角色")
    public Result<?> doAssign(@RequestParam String userId,@RequestParam String[] roleId) {
        roleService.saveUserRoleRealtionShip(userId,roleId);
        return Result.data();
    }
}

