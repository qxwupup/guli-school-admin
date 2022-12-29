package com.qxw.common.core.result;

import cn.hutool.json.JSONObject;
import com.qxw.common.core.object.ResponseResult;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Optional;

/**
 * API统一响应
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code = 0;

    private boolean success = true;


    private String msg;


    private long timestamp = System.currentTimeMillis();

    private T data;

    private String message;


    private Result(IResultCode resultCode) {
        this(resultCode, null, resultCode.getMessage());
    }

    private Result(IResultCode resultCode, String msg) {
        this(resultCode, null, msg);
    }

    private Result(IResultCode resultCode, T data) {
        this(resultCode, data, resultCode.getMessage());
    }

    private Result(IResultCode resultCode, T data, String msg) {
        this(resultCode.getCode(), data, msg);
    }

    private Result(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.message = msg;
        this.success = ResultCode.SUCCESS.code == code;
    }


    /**
     * 判断返回是否为成功
     *
     * @param result Result
     * @return 是否成功
     */
    public static boolean isSuccess(@Nullable Result<?> result) {
        return Optional.ofNullable(result)
                .map(x -> ObjectUtils.nullSafeEquals(ResultCode.SUCCESS.code, x.code))
                .orElse(Boolean.FALSE);
    }

    /**
     * 判断返回是否为成功
     *
     * @param result Result
     * @return 是否成功
     */
    public static boolean isNotSuccess(@Nullable Result<?> result) {
        return !Result.isSuccess(result);
    }

    /**
     * 返回R
     *
     * @param data 数据
     * @param <T>  T 泛型标记
     * @return Result
     */
    public static <T> Result<T> data(T data) {
        return data(data, ResultConstant.DEFAULT_SUCCESS_MESSAGE);
    }


    public static <T> Result<T> data() {
        return data(null, "成功！");
    }

    /**
     * 返回R
     *
     * @param data 数据
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return Result
     */
    public static <T> Result<T> data(T data, String msg) {
        return data(HttpServletResponse.SC_OK, data, msg);
    }

    /**
     * 返回R
     *
     * @param code 状态码
     * @param data 数据
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return Result
     */
    public static <T> Result<T> data(int code, T data, String msg) {
        return new Result<>(code, data, data == null ? ResultConstant.DEFAULT_NULL_MESSAGE : msg);
    }


    /**
     * 返回R
     *
     * @param data 数据
     * @param <T>  T 泛型标记
     * @return Result
     */
    public static <T> Result<T> success(T data) {
        return data(data, ResultConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * 返回R
     *
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return Result
     */
    public static <T> Result<T> success(String msg) {
        return new Result<>(ResultCode.SUCCESS, msg);
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param <T>        T 泛型标记
     * @return Result
     */
    public static <T> Result<T> success(IResultCode resultCode) {
        return new Result<>(resultCode);
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param msg        消息
     * @param <T>        T 泛型标记
     * @return Result
     */
    public static <T> Result<T> success(IResultCode resultCode, String msg) {
        return new Result<>(resultCode, msg);
    }

    /**
     * 返回R
     *
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return Result
     */
    public static <T> Result<T> fail(String msg) {
        return new Result<>(ResultCode.FAILURE, msg);
    }


    /**
     * 返回R
     *
     * @param code 状态码
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return Result
     */
    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, null, msg);
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param <T>        T 泛型标记
     * @return Result
     */
    public static <T> Result<T> fail(IResultCode resultCode) {
        return new Result<>(resultCode);
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param msg        消息
     * @param <T>        T 泛型标记
     * @return Result
     */
    public static <T> Result<T> fail(IResultCode resultCode, String msg) {
        return new Result<>(resultCode, msg);
    }

    /**
     * 返回R
     *
     * @param flag 成功状态
     * @return Result
     */
    public static <T> Result<T> status(boolean flag) {
        return flag ? success(ResultConstant.DEFAULT_SUCCESS_MESSAGE) : fail(ResultConstant.DEFAULT_FAILURE_MESSAGE);
    }

    /**
     * 返回R
     *
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return Result
     */
    public static <T> Result<T> error(String msg) {
        return new Result<T>(ResultCode.SC_INTERNAL_SERVER_ERROR, msg);
    }

    /**
     * 返回R
     *
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return Result
     */
    public static <T> Result<T> error(IResultCode msg) {
        return new Result<T>(ResultCode.SC_INTERNAL_SERVER_ERROR, msg.getMessage());
    }

    /**
     * 返回R
     *
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return Result
     */
    public static <T> Result<T> error(int code, String msg) {
        return new Result<T>(code, null, msg);
    }


    public static Builder builder() {
        return new Builder(Result.data(new JSONObject()));
    }

    public static <T> Result<T> convert(ResponseResult<T> datasourceResult) {
        return datasourceResult.isSuccess() ? Result.data(datasourceResult.getData()) : Result.fail(datasourceResult.getMsg());
    }


    public static class Builder {
        private final Result<JSONObject> objectResult;

        Builder(Result<JSONObject> objectResult) {
            this.objectResult = objectResult;
        }

        public Builder put(String key, Object obj) {
            if (this.objectResult.data == null) {
                this.objectResult.data = new JSONObject();
            }
            objectResult.data.putOpt(key, obj);
            return this;
        }

        public Result<JSONObject> build() {
            return objectResult;
        }
    }


}
