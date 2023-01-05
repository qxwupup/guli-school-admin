package com.qxw.order.feign;

import com.qxw.common.core.result.Result;
import com.qxw.common.core.vo.OrderMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface MemberClient {
    @GetMapping("/educenter/member/order/info/{memberId}")
    public Result<OrderMemberVo> getMemberInfo(@PathVariable(value = "memberId") String memberId);
}
