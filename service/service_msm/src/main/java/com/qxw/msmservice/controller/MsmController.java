package com.qxw.msmservice.controller;

import cn.hutool.core.util.StrUtil;
import com.qxw.common.core.result.Result;
import com.qxw.msmservice.service.MsmService;
import com.qxw.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/send/{phone}")
    public Result<?> sendMsm(@PathVariable String phone){

        String code = redisTemplate.opsForValue().get(phone);
        if(StrUtil.isNotEmpty(code)){
            return Result.error("已有验证码，请仔细查看短信，或5分钟过后,请再次尝试");
        }

        code = RandomUtil.getSixBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        boolean flag = msmService.sendCode(param,phone);

        if(flag){
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return Result.success("短信发送成功");
        }else{
            return Result.error("短信发送失败");
        }


    }
}
