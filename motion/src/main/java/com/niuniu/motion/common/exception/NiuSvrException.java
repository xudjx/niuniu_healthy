package com.niuniu.motion.common.exception;

import java.io.Serializable;

public class NiuSvrException extends Exception implements Serializable {

    private static final long serialVersionUID = -4583945443048946425L;

    private int code;
    private String msg;
    private String chineseMsg;

    public NiuSvrException(int code, String msg, String chineseMsg) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.chineseMsg = chineseMsg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getChineseMsg() {
        return chineseMsg;
    }
}
