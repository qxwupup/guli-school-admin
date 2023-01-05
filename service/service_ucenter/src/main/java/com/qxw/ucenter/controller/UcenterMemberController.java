package com.qxw.ucenter.controller;

import com.qxw.common.core.result.Result;
import com.qxw.common.core.utils.JwtUtils;
import com.qxw.common.core.vo.OrderMemberVo;
import com.qxw.ucenter.entity.UcenterMember;
import com.qxw.ucenter.entity.vo.LoginVo;
import com.qxw.ucenter.entity.vo.RegisterVo;
import com.qxw.ucenter.service.UcenterMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 会员表 控制器
 *
 * @author qxw
 * @since 2022-12-30
 */
@RestController
@RequestMapping("/educenter/member")
@Tag(name = "用户登录注册相关接口", description = "用户登录注册相关接口")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    /**
     * 登录
     */

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录")
    public Result<?> loginUser(@RequestBody LoginVo member){

        String token = memberService.login(member);

        return Result.data(token);
    }


    /**
     * 注册
     */

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册")
    public Result<?> registerUser(@RequestBody RegisterVo registerVo){

        boolean flag = memberService.register(registerVo);
        return flag?Result.success("注册成功"):Result.error("注册失败");
    }


    @GetMapping("/get/info")
    @Operation(summary = "根据token获取用户信息", description = "根据token获取用户信息")
    public Result<?> getMemberInfo(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        UcenterMember member = memberService.getById(memberId);

        return  Result.data(member);
    }

    @GetMapping("/order/info/{memberId}")
    @Operation(summary = "根据用户ID，获取订单用户信息", description = "根据用户ID，获取订单用户信息")
    public Result<OrderMemberVo> getMemberInfo(@PathVariable String memberId){
        UcenterMember member = memberService.getById(memberId);
        OrderMemberVo res = new OrderMemberVo();
        BeanUtils.copyProperties(member,res);
        return  Result.data(res);
    }


    @GetMapping("/register/count/{day}")
    public Result<Integer> registerCount(@PathVariable String day){

        Integer count =  memberService.registerCountByDay(day);

        return Result.data(count);
    }

}
