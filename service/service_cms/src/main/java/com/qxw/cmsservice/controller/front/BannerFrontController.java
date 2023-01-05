package com.qxw.cmsservice.controller.front;


import com.qxw.cmsservice.entity.CrmBanner;
import com.qxw.cmsservice.service.CrmBannerService;
import com.qxw.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author qxw
 * @since 2022-12-28
 */
@RestController
@RequestMapping("/cmsservice/banner")
@Tag(name = "前台获取Banner相关接口", description = "前台获取Banner相关接口")
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("/getAllBanner")
    @Operation(summary = "获取所有Banner信息", description = "获取所有Banner信息")
    //TODO 最好不返回实体类，还需要更细的粒度去获取Banner，不能全部获取返回
    public Result<?> getBanner(){
        List<CrmBanner> list = bannerService.selectAllBanner();
        return Result.builder().put("list",list).build();
    }

}

