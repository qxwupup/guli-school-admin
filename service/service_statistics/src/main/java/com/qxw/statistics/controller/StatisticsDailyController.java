package com.qxw.statistics.controller;

import com.qxw.common.core.result.Result;
import com.qxw.statistics.feignclient.MemberStatisticsClient;
import com.qxw.statistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 网站统计日数据 控制器
 *
 * @author qxw
 * @since 2023-01-05
 */
@RestController
@RequestMapping("/staservice/statistics")
@CrossOrigin
public class StatisticsDailyController {

    //TODO  统计分析需换种方式重做，不使用@Scheduled
    @Autowired
    private MemberStatisticsClient memberStatisticsClient;

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @GetMapping("/member/register/{day}")
    public Result<?> registerCountByDay(@PathVariable String day){
        Integer registerCount = memberStatisticsClient.registerCount(day).getData();
        return Result.data(registerCount);
    }

}
