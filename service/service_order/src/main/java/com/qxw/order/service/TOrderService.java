package com.qxw.order.service;

import com.qxw.order.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 订单 服务类
 *
 * @author qxw
 * @since 2023-01-03
 */
public interface TOrderService extends IService<TOrder> {

    String createOrder(String courseId, String memberId);
}
