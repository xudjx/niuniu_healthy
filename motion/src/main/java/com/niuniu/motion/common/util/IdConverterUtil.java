package com.niuniu.motion.common.util;

import com.niuniu.motion.common.exception.CommonUtilErrorCode;
import com.niuniu.motion.common.exception.NiuSvrException;

/**
 * 用户ID转换算法
 *
 * 举例：id = 1230， 加密之后：x4bgji4xstjme
 */
public class IdConverterUtil {

    public static String commonEncrypt(String toEncrypt) throws NiuSvrException {
        try {
            return DESUtil.encrypt(toEncrypt);
        } catch (Exception e) {
            throw new NiuSvrException(CommonUtilErrorCode.ENCRYPT_EXCEPTION);
        }
    }

    public static String commonDecrypt(String toDecrypt) throws NiuSvrException {
        try {
            return DESUtil.decrypt(toDecrypt);
        } catch (Exception e) {
            throw new NiuSvrException(CommonUtilErrorCode.DECRYPT_EXCEPTION);
        }
    }
}
