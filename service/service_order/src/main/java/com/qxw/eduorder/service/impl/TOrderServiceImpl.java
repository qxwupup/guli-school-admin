package com.qxw.eduorder.service.impl;

import cn.hutool.core.util.StrUtil;
import com.qxw.common.core.vo.OrderCourseVo;
import com.qxw.common.core.vo.OrderMemberVo;
import com.qxw.eduorder.entity.TOrder;
import com.qxw.eduorder.feign.CourseClient;
import com.qxw.eduorder.feign.MemberClient;
import com.qxw.eduorder.mapper.TOrderMapper;
import com.qxw.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxw.eduorder.utils.OrderNoUtil;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 订单 服务实现类
 *
 * @author qxw
 * @since 2023-01-03
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private MemberClient memberClient;

    @Override
    public String createOrder(String courseId, String memberId) {
        if(StrUtil.isEmpty(memberId)){
            throw new RuntimeException("请登录");
        }

        if(StrUtil.isEmpty(courseId)){
            throw new RuntimeException("请选择课程");
        }

        OrderMemberVo member = memberClient.getMemberInfo(memberId).getData();
        OrderCourseVo course = courseClient.getOrderCourseInfo(courseId).getData();

        TOrder order = new TOrder();
        if(member!=null && course!=null){
            order.setOrderNo(OrderNoUtil.getOrderNo());
            order.setCourseId(courseId);
            order.setCourseTitle(course.getTitle());
            order.setCourseCover(course.getCover());
            order.setTeacherName(course.getTeacherName());
            order.setTotalFee(course.getPrice());
            order.setMemberId(memberId);
            order.setMobile(member.getMobile());
            order.setNickname(member.getNickname());
            order.setStatus(0);
            order.setPayType(1);
            baseMapper.insert(order);
        }
        //返回订单号
        return order.getOrderNo();
    }
}
