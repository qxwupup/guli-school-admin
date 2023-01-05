package com.qxw.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qxw.ucenter.entity.UcenterMember;

/**
 * 会员表 Mapper 接口
 *
 * @author qxw
 * @since 2022-12-30
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer registerCountByDay(String day);
}
