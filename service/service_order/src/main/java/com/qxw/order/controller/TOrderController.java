package com.qxw.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qxw.common.core.result.Result;
import com.qxw.common.core.utils.JwtUtils;
import com.qxw.order.entity.TOrder;
import com.qxw.order.service.TOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单 控制器
 *
 * @author qxw
 * @since 2023-01-03
 */
@RestController
@RequestMapping("/eduorder/order")
@Tag(name = "订单相关接口", description = "订单相关接口")
public class TOrderController {
    @Autowired
    private TOrderService orderService;

    @PostMapping("/create/{courseId}")
    @Operation(summary = "通过课程ID，创建订单", description = "通过课程ID，创建订单")
    public Result<?> createOrder(@PathVariable String courseId, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo = orderService.createOrder(courseId,memberId);
        return Result.data(orderNo);
    }

    @GetMapping("/info/{orderNo}")
    @Operation(summary = "通过订单号，获取订单信息", description = "通过订单号，获取订单信息")
    public Result<?> getOrderInfo(@PathVariable String orderNo){
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        return Result.data(orderService.getOne(wrapper));
    }

    //TODO  查询用户是否购买过某个课程
    @GetMapping("/isbuy/{courseId}/{memberId}")
    @Operation(summary = "通过课程ID、用户ID，查询用户是否购买了该课程", description = "通过课程ID、用户ID，查询用户是否购买了该课程")
    public  Result<Boolean> isBuy(@PathVariable String courseId,@PathVariable String memberId){
        Long count = orderService.lambdaQuery().eq(TOrder::getCourseId, courseId).eq(TOrder::getMemberId, memberId).eq(TOrder::getStatus, 1).count();
        return Result.data(count>0);
    }

}
