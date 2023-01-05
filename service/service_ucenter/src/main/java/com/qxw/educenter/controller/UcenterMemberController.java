package com.qxw.educenter.controller;

import com.qxw.common.core.result.Result;
import com.qxw.common.core.utils.JwtUtils;
import com.qxw.common.core.vo.OrderMemberVo;
import com.qxw.educenter.entity.UcenterMember;
import com.qxw.educenter.entity.vo.LoginVo;
import com.qxw.educenter.entity.vo.RegisterVo;
import com.qxw.educenter.service.UcenterMemberService;
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


    @GetMapping("/get/info")
    public Result<?> getMemberInfo(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        UcenterMember member = memberService.getById(memberId);

        return  Result.data(member);
    }

    @GetMapping("/order/info/{memberId}")
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
