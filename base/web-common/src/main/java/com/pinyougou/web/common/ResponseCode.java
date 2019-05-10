package com.pinyougou.web.common;

/**
 * @author 邱长海
 */
public enum ResponseCode {

    SUCCESS(0, "请求成功"),
    ERROR(1, "未知错误"),
    SERVER_ERROR(5, "请求出错"),
    NEED_LOGIN(10, "您没有登录或您的登录已失效，请重新登录"),
    PERMISSION_ERROR(6, "您暂无此项操作的权限"),
    ILLEGAL_ARGUMENT(2, "非法的请求参数");

    private final int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public ResponseCode setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
