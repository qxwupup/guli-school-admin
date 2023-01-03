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
 * 支付日志表实体类
 *
 * @author qxw
 * @since 2023-01-03
 */
@Data
public class TPayLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 支付完成时间
     */
    private LocalDateTime payTime;
    /**
     * 支付金额（分）
     */
    private BigDecimal totalFee;
    /**
     * 交易流水号
     */
    private String transactionId;
    /**
     * 交易状态
     */
    private String tradeState;
    /**
     * 支付类型（1：微信 2：支付宝）
     */
    private Integer payType;
    /**
     * 其他属性
     */
    private String attr;
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
