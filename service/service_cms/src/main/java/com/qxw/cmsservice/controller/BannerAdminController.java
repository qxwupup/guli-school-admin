package com.qxw.cmsservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxw.cmsservice.entity.CrmBanner;
import com.qxw.cmsservice.service.CrmBannerService;
import com.qxw.commonutils.result.R;
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
    public R pageBanner(@PathVariable Long current,@PathVariable Long size){

        Page<CrmBanner> pageBanner = new Page<>(current,size);
        bannerService.page(pageBanner, null);
        return R.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    @GetMapping("/get/{bannerId}")
    public R getBanner(@PathVariable String bannerId){
        CrmBanner banner = bannerService.getById(bannerId);
        return R.ok().data("item",banner);
    }

    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        return bannerService.save(crmBanner)?R.ok():R.error();
    }

    @PostMapping("/updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        return bannerService.updateById(crmBanner)?R.ok():R.error();
    }

    @DeleteMapping("/remove/{bannerId}")
    public R removeBanner(@PathVariable String bannerId){
        return bannerService.removeById(bannerId)?R.ok():R.error();
    }

}

