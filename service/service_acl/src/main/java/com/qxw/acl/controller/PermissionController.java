package com.qxw.acl.controller;


import com.qxw.acl.entity.Permission;
import com.qxw.acl.service.PermissionService;
import com.qxw.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>

 */
@RestController
@RequestMapping("/admin/acl/permission")
@Tag(name = "用户权限管理相关接口", description = "用户权限管理相关接口")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    @GetMapping
    @Operation(summary = "查询所有菜单", description = "查询所有菜单")
    public Result<?> indexAllPermission() {
        List<Permission> list =  permissionService.queryAllMenuGuli();
        return Result.data(list);
    }

    @DeleteMapping("/remove/{id}")
    @Operation(summary = "递归删除菜单", description = "递归删除菜单")
    public Result<?> remove(@PathVariable String id) {
        permissionService.removeChildByIdGuli(id);
        return Result.data();
    }

    @PostMapping("/doAssign")
    @Operation(summary = "给角色分配权限", description = "给角色分配权限")
    public Result<?> doAssign(String roleId,String[] permissionId) {
        permissionService.saveRolePermissionRealtionShipGuli(roleId,permissionId);
        return Result.data();
    }


    @GetMapping("/toAssign/{roleId}")
    @Operation(summary = "根据角色获取菜单", description = "根据角色获取菜单")
    public Result<?> toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return Result.data(list);
    }


    @PostMapping("/save")
    @Operation(summary = "新增菜单", description = "新增菜单")
    public Result<?> save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.data();
    }


    @PutMapping("/update")
    @Operation(summary = "修改菜单", description = "修改菜单")
    public Result<?> updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.data();
    }

}

