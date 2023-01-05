package com.qxw.ucenter.entity.vo;

import lombok.Data;

@Data
public class LoginVo {
    private String mobile;
    /**
     * 密码
     */

    private String password;
    /**
     * 昵称
     */

    private String nickname;
}
