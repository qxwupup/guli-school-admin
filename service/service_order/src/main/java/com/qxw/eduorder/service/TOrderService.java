package com.qxw.eduorder.service;

import com.qxw.eduorder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 订单 服务类
 *
 * @author qxw
 * @since 2023-01-03
 */
public interface TOrderService extends IService<TOrder> {

    String createOrder(String courseId, String memberId);
}
