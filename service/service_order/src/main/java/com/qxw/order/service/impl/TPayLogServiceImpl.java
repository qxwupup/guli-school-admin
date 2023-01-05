package com.qxw.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
import com.qxw.order.entity.TOrder;
import com.qxw.order.entity.TPayLog;
import com.qxw.order.mapper.TPayLogMapper;
import com.qxw.order.service.TOrderService;
import com.qxw.order.service.TPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxw.order.utils.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付日志表 服务实现类
 *
 * @author qxw
 * @since 2023-01-03
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {


    @Autowired
    private TOrderService orderService;


    //TODO  目前是用的他人的收款账户信息，待更新为自己的
    @Override
    public Map<String, String> createQRCode(String orderNo) {
        try {
            //1 根据订单号查询订单信息
//                        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
//                        wrapper.eq("order_no",orderNo);
//                        TOrder order = orderService.getOne(wrapper);
            TOrder order = orderService.lambdaQuery().eq(TOrder::getOrderNo, orderNo).one();

            //2 使用map设置生成二维码需要参数
            Map m = new HashMap();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle()); //课程标题
            m.put("out_trade_no", orderNo); //订单号
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            m.put("trade_type", "NATIVE");

            //3 发送httpclient请求，传递参数xml格式，微信支付提供的固定的地址
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置xml格式的参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            //执行post请求发送
            client.post();

            //4 得到发送请求返回结果
            //返回内容，是使用xml格式返回
            String xml = client.getContent();

            //把xml格式转换map集合，把map集合返回
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            //最终返回数据 的封装
            Map map = new HashMap();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));  //返回二维码操作状态码
            map.put("code_url", resultMap.get("code_url"));        //二维码地址

            return map;
        } catch (Exception e) {
            throw new RuntimeException("生成二维码失败");
        }
    }

    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2 发送httpclient
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //3 得到请求返回内容
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map再返回
            return resultMap;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateOrderStatus(Map<String, String> map) {
        //从map获取订单号
        String orderNo = map.get("out_trade_no");
        //根据订单号查询订单信息
//        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
//        wrapper.eq("order_no",orderNo);
//        TOrder order = orderService.getOne(wrapper);
        TOrder order = orderService.lambdaQuery().eq(TOrder::getOrderNo, orderNo).one();

        if(order!=null && order.getStatus().intValue() != 1){
            order.setStatus(1);//1代表已经支付
            orderService.updateById(order);

            //向支付表添加支付记录
            TPayLog payLog = new TPayLog();
            payLog.setOrderNo(orderNo);  //订单号
            payLog.setPayTime(new Date()); //订单完成时间
            payLog.setPayType(1);//支付类型 1微信
            payLog.setTotalFee(order.getTotalFee());//总金额(分)

            payLog.setTradeState(map.get("trade_state"));//支付状态
            payLog.setTransactionId(map.get("transaction_id")); //流水号
            payLog.setAttr(JSONObject.toJSONString(map));

            return this.save(payLog);
        }else{
            return false;
        }
    }
}
