package com.niuniu.motion.common.exception;

public enum CommonUtilErrorCode {

    ENCRYPT_EXCEPTION               (-100, "error while encrypting", "编码错误"),
    DECRYPT_EXCEPTION               (-101, "error while decrypting, maybe fake data", "解码错误");

    private int errorCode;
    private String errorMessage;
    private String chineseErrorMessage;

    CommonUtilErrorCode(int errorCode, String errorMessage, String chineseErrorMessage)
    {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.chineseErrorMessage = chineseErrorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getChineseErrorMessage()
    {
        return chineseErrorMessage;
    }

    public void setChineseErrorMessage(String chineseErrorMessage)
    {
        this.chineseErrorMessage = chineseErrorMessage;
    }
}
