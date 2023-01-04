package com.qxw.eduservice.feignclient;

import com.qxw.common.core.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-order")
public interface OrderClient {
    @GetMapping("/eduorder/order/isbuy/{courseId}/{memberId}")
    public Result<Boolean> isBuy(@PathVariable(value = "courseId") String courseId, @PathVariable(value = "memberId") String memberId);
}
