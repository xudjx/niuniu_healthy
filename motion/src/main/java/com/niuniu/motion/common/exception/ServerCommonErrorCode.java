package com.niuniu.motion.common.exception;

public enum ServerCommonErrorCode {

    UNKNOWN_ERROR               (-10025,  "unknown error", "网络错误"),
    UNKNOWN_DJERROR             (-10001, "unknown dajia error", "网络错误"),
    OBJECT_NOT_FOUND            (-10003, "object not found", "不存在"),
    OBJECT_EXISTS               (-10004, "object already exists", "已存在"),
    RPC_ERROR                   (-10004, "rpc return null", "网络错误"),
    DJ_RSP_ERROR                (-10005, "dajia response error", "网络错误"),

    PARAM_ERROR                 (-20000, "Request parameter error", "参数错误"),
    MISSING_PARAM               (-20001, "Missing important request parameter", "参数错误"),
    OBJECT_NOT_EXIST            (-20004, "target object not exist", "对象不存在"),
    MUST_AUTH                   (-20005, "this interface must auth", "权限错误"),
    INVALID_AUTH_TOKEN          (-20006, "invalid auth token", "token错误"),
    NOT_FOUND_TOKEN             (-20007, "not found auth token", "token错误"),
    INCORRECT_AUTH_TOKEN        (-20008, "incorrect auth token", "token错误"),
    NOT_FOUND                   (-20404, "not found", "网络错误"),
    EXPIRE_AUTH_TOKEN           (-20009, "expire auth token", "token失效"),;

    private int code;
    private String msg;
    private String chineseMsg;

    ServerCommonErrorCode(int code, String msg, String chineseMsg) {
        this.code = code;
        this.msg = msg;
        this.chineseMsg = chineseMsg;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getChineseMsg()
    {
        return chineseMsg;
    }

    public void setChineseMsg(String chineseMsg)
    {
        this.chineseMsg = chineseMsg;
    }

}
