package com.qxw.acl.controller;

import com.alibaba.fastjson.JSONObject;
import com.qxw.acl.service.IndexService;
import com.qxw.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/index")
@Tag(name = "首页用户权限相关接口", description = "首页用户权限相关接口")
public class IndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 根据token获取用户信息
     */
    @GetMapping("/info")
    @Operation(summary = "根据token获取用户信息", description = "根据token获取用户信息")
    public Result<?> info(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return Result.data(userInfo);
    }

    /**
     * 获取菜单
     * @return
     */
    @GetMapping("/menu")
    @Operation(summary = "获取用户对应权限的菜单", description = "获取用户对应权限的菜单")
    public Result<?> getMenu(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return Result.data(permissionList);
    }

    @PostMapping("/logout")
    @Operation(summary = "退出", description = "退出")
    public Result<?> logout(){
        return Result.data();
    }

}
