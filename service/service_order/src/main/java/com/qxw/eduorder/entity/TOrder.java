package com.qxw.eduorder.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单实体类
 *
 * @author qxw
 * @since 2023-01-03
 */
@Data
public class TOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 课程id
     */
    private String courseId;
    /**
     * 课程名称
     */
    private String courseTitle;
    /**
     * 课程封面
     */
    private String courseCover;
    /**
     * 讲师名称
     */
    private String teacherName;
    /**
     * 会员id
     */
    private String memberId;
    /**
     * 会员昵称
     */
    private String nickname;
    /**
     * 会员手机
     */
    private String mobile;
    /**
     * 订单金额（分）
     */
    private BigDecimal totalFee;
    /**
     * 支付类型（1：微信 2：支付宝）
     */
    private Integer payType;
    /**
     * 订单状态（0：未支付 1：已支付）
     */
    private Integer status;
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
