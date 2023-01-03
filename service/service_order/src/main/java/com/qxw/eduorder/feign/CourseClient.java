package com.qxw.eduorder.feign;

import com.qxw.common.core.result.Result;
import com.qxw.common.core.vo.OrderCourseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface CourseClient {
    @GetMapping("/eduservice/course/order/info/{courseId}")
    public Result<OrderCourseVo> getOrderCourseInfo(@PathVariable(value = "courseId") String courseId);
}
