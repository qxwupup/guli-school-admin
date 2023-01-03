package com.qxw.common.core.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员表实体类
 *
 * @author qxw
 * @since 2022-12-30
 */
@Data
public class OrderMemberVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会员id
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;
    /**
     * 微信openid
     */

    private String openid;
    /**
     * 手机号
     */

    private String mobile;
    /**
     * 密码
     */

    private String password;
    /**
     * 昵称
     */

    private String nickname;
    /**
     * 性别 1 女，2 男
     */

    private Integer sex;
    /**
     * 年龄
     */

    private Integer age;
    /**
     * 用户头像
     */

    private String avatar;
    /**
     * 用户签名
     */

    private String sign;
    /**
     * 是否禁用 1（true）已禁用，  0（false）未禁用
     */

    private Boolean isDisabled;
    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
