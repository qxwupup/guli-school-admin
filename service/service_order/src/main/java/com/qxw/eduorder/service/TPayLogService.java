package com.qxw.eduorder.service;

import com.qxw.eduorder.entity.TPayLog;
        import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

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
