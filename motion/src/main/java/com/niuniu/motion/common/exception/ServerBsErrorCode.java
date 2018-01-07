package com.niuniu.motion.common.exception;

public enum ServerBsErrorCode {

    DB_QUERY_ERROR                         (-20001, "db query error", "数据库错误"),
    DB_QUERY_NO_RESULT                     (-20002, "db query no result", "数据库错误"),
    DB_NULL_FIELD                          (-20003, "target field is illegally null", "数据库错误"),
    INVALID_TOKEN                          (-20004, "token is valid", "token非法"),
    LOGON_FAIL                             (-20005, "username and password don't match", "登录失败"),
    DB_SAVE_NO_RESULT                      (-20007, "db save error", "数据库错误"),
    NOT_ALLOWED                            (-20007, "not allowed", "不允许"),

    PROFILE_NOT_EXIST                      (-20007, "profile not exist", "用户个人信息不存在");

    private int code;
    private String msg;
    private String chineseMsg;

    ServerBsErrorCode(int code, String msg, String chineseMsg) {
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
