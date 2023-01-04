package com.qxw.eduorder.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxw.common.core.result.Result;
import com.qxw.eduorder.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.qxw.eduorder.entity.TPayLog;
import com.qxw.eduorder.service.TPayLogService;

import java.util.List;
import java.util.Map;

/**
 * 支付日志表 控制器
 *
 * @author qxw
 * @since 2023-01-03
 */
@RestController
@RequestMapping("/eduorder/pay")
@CrossOrigin
public class TPayLogController {
    @Autowired
    private TPayLogService payLogService;

    @GetMapping("/create/QRCode/{orderNo}")
    public Result<?> createRQCode(@PathVariable String orderNo){

       Map<String,String> map =  payLogService.createQRCode(orderNo);

        return Result.data(map);
    }

    @GetMapping("/query/status/{orderNo}")
    public Result<?> queryStatus(@PathVariable String orderNo){

        Map<String,String> map =  payLogService.queryPayStatus(orderNo);

        if(CollUtil.isEmpty(map)){
            return Result.error("支付失败");
        }

        if(StrUtil.equalsIgnoreCase("SUCCESS",map.get("trade_state"))){

            boolean flag = payLogService.updateOrderStatus(map);

            return  Result.status(flag);
        }

        return Result.error("支付中...");
    }

}
