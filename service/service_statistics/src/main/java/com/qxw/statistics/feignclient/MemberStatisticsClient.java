package com.qxw.statistics.feignclient;

import com.qxw.common.core.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface MemberStatisticsClient {
    @GetMapping("/educenter/member/register/count/{day}")
    public Result<Integer> registerCount(@PathVariable(value = "day") String day);
}
