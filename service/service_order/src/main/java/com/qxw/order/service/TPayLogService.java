package com.qxw.order.service;

import com.qxw.order.entity.TPayLog;
        import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 支付日志表 服务类
 *
 * @author qxw
 * @since 2023-01-03
 */
public interface TPayLogService extends IService<TPayLog> {

    Map<String, String> createQRCode(String orderNo);

    Map<String, String> queryPayStatus(String orderNo);

    boolean updateOrderStatus(Map<String, String> map);
}
