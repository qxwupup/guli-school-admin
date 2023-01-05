package com.qxw.order.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.qxw.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.qxw.order.service.TPayLogService;

import java.util.Map;

/**
 * 支付日志表 控制器
 *
 * @author qxw
 * @since 2023-01-03
 */
@RestController
@RequestMapping("/eduorder/pay")
@Tag(name = "支付相关接口", description = "支付相关接口")
public class TPayLogController {
    @Autowired
    private TPayLogService payLogService;

    @GetMapping("/create/QRCode/{orderNo}")
    @Operation(summary = "根据订单号，生成微信付款码", description = "根据订单号，生成微信付款码")
    public Result<?> createRQCode(@PathVariable String orderNo){

       Map<String,String> map =  payLogService.createQRCode(orderNo);

        return Result.data(map);
    }

    @GetMapping("/query/status/{orderNo}")
    @Operation(summary = "根据订单号，查询付款状态", description = "根据订单号，查询付款状态")
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
