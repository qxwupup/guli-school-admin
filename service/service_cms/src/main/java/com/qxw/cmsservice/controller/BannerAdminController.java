package com.qxw.cmsservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxw.cmsservice.entity.CrmBanner;
import com.qxw.cmsservice.service.CrmBannerService;
import com.qxw.common.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author qxw
 * @since 2022-12-28
 */
@RestController
@RequestMapping("/cmsservice/banner/admin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("/pageBanner/{current}/{size}")
    public Result<?> pageBanner(@PathVariable Long current, @PathVariable Long size){

        Page<CrmBanner> pageBanner = new Page<>(current,size);
        bannerService.page(pageBanner, null);
        return Result.builder().put("items",pageBanner.getRecords()).put("total",pageBanner.getTotal()).build();
    }

    @GetMapping("/get/{bannerId}")
    public Result<?> getBanner(@PathVariable String bannerId){
        CrmBanner banner = bannerService.getById(bannerId);
        return Result.builder().put("item",banner).build();
    }

    @PostMapping("/addBanner")
    public Result<?> addBanner(@RequestBody CrmBanner crmBanner){
        return Result.status(bannerService.save(crmBanner));
    }

    @PostMapping("/updateBanner")
    public Result<?> updateBanner(@RequestBody CrmBanner crmBanner){
        return Result.status(bannerService.updateById(crmBanner));
    }

    @DeleteMapping("/remove/{bannerId}")
    public Result<?> removeBanner(@PathVariable String bannerId){
        return Result.status(bannerService.removeById(bannerId));
    }

}

