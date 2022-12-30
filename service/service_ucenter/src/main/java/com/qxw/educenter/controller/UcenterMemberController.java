package com.qxw.educenter.controller;

import com.qxw.common.core.result.Result;
import com.qxw.educenter.entity.vo.RegisterVo;
import com.qxw.educenter.service.UcenterMemberService;
import com.qxw.educenter.entity.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 会员表 控制器
 *
 * @author qxw
 * @since 2022-12-30
 */
@RestController
@CrossOrigin
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    /**
     * 登录
     */

    @PostMapping("/login")
    public Result<?> loginUser(@RequestBody LoginVo member){

        String token = memberService.login(member);

        return Result.data(token);
    }


    /**
     * 注册
     */

    @PostMapping("/register")
    public Result<?> registerUser(@RequestBody RegisterVo registerVo){

        boolean flag = memberService.register(registerVo);
        return flag?Result.success("注册成功"):Result.error("注册失败");
    }




}
