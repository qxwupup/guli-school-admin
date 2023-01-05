package com.qxw.educenter.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxw.common.core.utils.JwtUtils;
import com.qxw.common.core.utils.MD5;
import com.qxw.educenter.entity.UcenterMember;
import com.qxw.educenter.entity.vo.RegisterVo;
import com.qxw.educenter.mapper.UcenterMemberMapper;
import com.qxw.educenter.service.UcenterMemberService;
import com.qxw.educenter.entity.vo.LoginVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 会员表 服务实现类
 *
 * @author qxw
 * @since 2022-12-30
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(LoginVo member) {

        String phone = member.getMobile();
        String password = member.getPassword();

        if(StrUtil.isEmpty(phone) || StrUtil.isEmpty(password)){
            throw new RuntimeException("手机号或密码为空");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",phone);
        UcenterMember memberByPhone = baseMapper.selectOne(wrapper);

        if(memberByPhone==null){
            throw new RuntimeException("不存在该手机号的用户");
        }

        if(!StrUtil.equals(MD5.encrypt(password),memberByPhone.getPassword())){
            throw new RuntimeException("密码错误");
        }

        if(memberByPhone.getIsDisabled()){
            throw new RuntimeException("用户已被禁用");
        }

        String token = JwtUtils.getJwtToken(memberByPhone.getId(), memberByPhone.getNickname());

        return token;
    }

    @Override
    public boolean register(RegisterVo registerVo) {

        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        if(StrUtil.isEmpty(code) || StrUtil.isEmpty(password) || StrUtil.isEmpty(mobile)|| StrUtil.isEmpty(nickname) ){
            throw new RuntimeException("输入的用户信息有误");
        }

        String verificationCode = redisTemplate.opsForValue().get(mobile);
        if(StrUtil.isEmpty(verificationCode) || !StrUtil.equals(verificationCode,code)){
            throw new RuntimeException("输入的验证码有误或失效");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        long count = baseMapper.selectCount(wrapper);
        if(count>0){
            throw new RuntimeException("已存在该手机号的用户");
        }
        UcenterMember member = new UcenterMember();
        registerVo.setPassword(MD5.encrypt(password));
        BeanUtils.copyProperties(registerVo,member);

        return this.save(member);
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Integer registerCountByDay(String day) {
        return baseMapper.registerCountByDay(day);
    }
}
