package com.niuniu.motion.dto;

import com.niuniu.motion.common.exception.NiuSvrException;

public class ResultDTO extends BaseDTO {

    public static final int SUCCESS = 1;
    public static final int FAILED = 0;

    public ResultDTO(int status) {
        this.status = status;
    }

    public ResultDTO(int status, String resultCode, String resultDesc) {
        this.status = status;
        this.resultDesc = resultDesc;
        this.resultCode = resultCode;
    }

    public ResultDTO() {

    }

    public int status;

    public String resultDesc;

    public String resultCode;

    public Object result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public NiuSvrException errorMsg;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
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
