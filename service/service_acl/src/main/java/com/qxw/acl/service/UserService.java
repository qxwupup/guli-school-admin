package com.qxw.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qxw.acl.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 */
public interface UserService extends IService<User> {

    // 从数据库中取出用户信息
    User selectByUsername(String username);
}
