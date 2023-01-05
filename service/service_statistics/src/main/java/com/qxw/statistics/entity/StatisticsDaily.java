package com.qxw.statistics.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 网站统计日数据实体类
 *
 * @author qxw
 * @since 2023-01-05
 */
@Data
public class StatisticsDaily implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 统计日期
     */
    private String dateCalculated;
    /**
     * 注册人数
     */
    private Integer registerNum;
    /**
     * 登录人数
     */
    private Integer loginNum;
    /**
     * 每日播放视频数
     */
    private Integer videoViewNum;
    /**
     * 每日新增课程数
     */
    private Integer courseNum;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
