package com.qxw.eduorder.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qxw.common.core.result.Result;
import com.qxw.common.core.utils.JwtUtils;
import com.qxw.eduorder.entity.TOrder;
import com.qxw.eduorder.service.TOrderService;
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
@CrossOrigin
public class TOrderController {
    @Autowired
    private TOrderService orderService;

    @PostMapping("/create/{courseId}")
    public Result<?> createOrder(@PathVariable String courseId, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo = orderService.createOrder(courseId,memberId);
        return Result.data(orderNo);
    }

    @GetMapping("/info/{orderNo}")
    public Result<?> getOrderInfo(@PathVariable String orderNo){
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        return Result.data(orderService.getOne(wrapper));
    }


}
