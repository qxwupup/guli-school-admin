package com.qxw.common.core.result;

import java.io.Serializable;


/**
 * API同意响应
 *
 */
public interface IResultCode extends Serializable {

    /**
     * 消息
     *
     * @return String
     */
    String getMessage();

    /**
     * 状态码
     *
     * @return int
     */
    int getCode();
}
