package com.qxw.cmsservice.service.impl;

import com.qxw.cmsservice.entity.CrmBanner;
import com.qxw.cmsservice.mapper.CrmBannerMapper;
import com.qxw.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author qxw
 * @since 2022-12-28
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public List<CrmBanner> selectAllBanner() {
        return baseMapper.selectList(null);
    }
}
