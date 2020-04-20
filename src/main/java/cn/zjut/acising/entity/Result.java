package cn.zjut.acising.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: SQJ
 * @data: 2018/6/29 10:08
 * @version:
 */
@Data
public class Result implements Serializable {

    private int code = 200;
    private String msg = "成功";
    private Object data = null;
    private Boolean success = true;

    public static Result success() {
        return new Result();
    }

    public static Result success(Object o) {
        Result result = new Result();
        result.setData(o);
        return result;
    }

    public static Result failure(ResultCode resultCode, Object o) {
        Result result = new Result();
        result.setCode(resultCode.code());
        result.setMsg(resultCode.message());
        result.setData(o);
        result.setSuccess(false);
        return result;
    }

    public static Result failure(ResultCode resultCode) {
        Result result = new Result();
        result.setCode(resultCode.code());
        result.setMsg(resultCode.message());
        result.setSuccess(false);
        return result;
    }

}
