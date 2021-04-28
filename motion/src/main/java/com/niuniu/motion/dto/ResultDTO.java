package com.niuniu.motion.dto;

import com.niuniu.motion.common.exception.NiuSvrException;

public class ResultDTO extends BaseDTO {

    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";

    public ResultDTO(String msg) {
        this.msg = msg;
    }

    public ResultDTO(String msg, NiuSvrException errorMsg) {
        this.msg = msg;
        this.errorMsg = errorMsg;
    }

    public ResultDTO() {

    }

    public String msg;  //success 成功， failed 失败

    public int retCode;

    public Object result;

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public NiuSvrException errorMsg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public NiuSvrException getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(NiuSvrException errorMsg) {
        this.errorMsg = errorMsg;
    }
}
