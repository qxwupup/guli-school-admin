package com.qxw.cmsservice.controller;


import com.qxw.cmsservice.entity.CrmBanner;
import com.qxw.cmsservice.service.CrmBannerService;
import com.qxw.commonutils.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("/getAllBanner")
    public R getBanner(){
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }

}

