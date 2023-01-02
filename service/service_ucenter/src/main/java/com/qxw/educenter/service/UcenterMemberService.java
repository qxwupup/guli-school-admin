package com.qxw.educenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qxw.educenter.entity.UcenterMember;
import com.qxw.educenter.entity.vo.LoginVo;
import com.qxw.educenter.entity.vo.RegisterVo;

/**
 * 会员表 服务类
 *
 * @author qxw
 * @since 2022-12-30
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo member);

    boolean register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);
}
