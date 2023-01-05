package com.qxw.cmsservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxw.cmsservice.entity.CrmBanner;
import com.qxw.cmsservice.service.CrmBannerService;
import com.qxw.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "后台管理Banner相关接口", description = "后台管理Banner相关接口")
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("/pageBanner/{current}/{size}")
    @Operation(summary = "分页查询Banner表数据", description = "分页查询Banner表数据")
    public Result<?> pageBanner(@PathVariable Long current, @PathVariable Long size){

        Page<CrmBanner> pageBanner = new Page<>(current,size);
        bannerService.page(pageBanner, null);
        return Result.builder().put("items",pageBanner.getRecords()).put("total",pageBanner.getTotal()).build();
    }

    @GetMapping("/get/{bannerId}")
    @Operation(summary = "根据bannerID，获取banner信息", description = "根据bannerID，获取banner信息")
    public Result<?> getBanner(@PathVariable String bannerId){
        CrmBanner banner = bannerService.getById(bannerId);
        return Result.builder().put("item",banner).build();
    }

    @PostMapping("/addBanner")
    @Operation(summary = "新增banner信息", description = "新增banner信息")
    public Result<?> addBanner(@RequestBody CrmBanner crmBanner){
        return Result.status(bannerService.save(crmBanner));
    }

    @PostMapping("/updateBanner")
    @Operation(summary = "更新banner信息", description = "更新banner信息")
    public Result<?> updateBanner(@RequestBody CrmBanner crmBanner){
        return Result.status(bannerService.updateById(crmBanner));
    }

    @DeleteMapping("/remove/{bannerId}")
    @Operation(summary = "根据bannerID，删除banner信息", description = "根据bannerID，删除banner信息")
    public Result<?> removeBanner(@PathVariable String bannerId){
        return Result.status(bannerService.removeById(bannerId));
    }

}

