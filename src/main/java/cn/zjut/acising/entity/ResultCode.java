package cn.zjut.acising.entity;

/**
 * @author: SQJ
 * @data: 2018/6/29 10:16
 * @version:
 */
public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 失败
     */
    FAIL(1000, "失败"),

    NOTFOUND(1001, "没有数据"),
    REGISTERALREADY(1002, "已经注册");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }


}
