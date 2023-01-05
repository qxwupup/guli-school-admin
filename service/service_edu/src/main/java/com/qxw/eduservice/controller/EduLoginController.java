package com.qxw.eduservice.controller;

import com.qxw.common.core.result.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {

    //login
    @PostMapping("/login")
    public Result<?> login(){

        return Result.builder().put("token","admin").build();
    }

    //info
    @GetMapping("/info")
    public Result<?> info(){

        return Result.builder().put("roles","[admin]").put("name","admin").put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif").build();
    }
}
