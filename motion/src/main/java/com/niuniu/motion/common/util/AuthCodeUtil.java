package com.niuniu.motion.common.util;

import com.niuniu.motion.common.exception.CommonUtilErrorCode;
import com.niuniu.motion.common.exception.NiuSvrException;
import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import static com.niuniu.motion.common.util.AuthCodeUtil.DiscuzAuthcodeMode.Encode;

/**
 * 用户authToken 生成算法
 *
 * 举例：用户信息（{"tokenId":12345,"role":0,"expireTime":1515917871332}）
 * 加密之后：ajJ1enZycklBQzFRZlJBTlhQdUkyN01iMHJzL3NHNGY3MmhVeFJ2MkRFNzNXejlyRFVadjJJOE1qYlRqRTVWdFlWdDU1NmhUU3h4NUdPRTFoa29JRk95TnFVM1BiZHNzbXo0YWkveW1scDlFcmc9PQ==
 */
public class AuthCodeUtil {

    public static char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /// <summary>
    /// 从字符串的指定位置截取指定长度的子字符串
    /// </summary>
    /// <param name="str">原字符串</param>
    /// <param name="startIndex">子字符串的起始位置</param>
    /// <param name="length">子字符串的长度</param>
    /// <returns>子字符串</returns>
    public static String CutString(String str, int startIndex, int length) {
        if (startIndex >= 0) {
            if (length < 0) {
                length = length * -1;
                if (startIndex - length < 0) {
                    length = startIndex;
                    startIndex = 0;
                } else {
                    startIndex = startIndex - length;
                }
            }

            if (startIndex > str.length()) {
                return "";
            }

        } else {
            if (length < 0) {
                return "";
            } else {
                if (length + startIndex > 0) {
                    length = length + startIndex;
                    startIndex = 0;
                } else {
                    return "";
                }
            }
        }

        if (str.length() - startIndex < length) {

            length = str.length() - startIndex;
        }

        return str.substring(startIndex, startIndex + length);
    }

    /// <summary>
    /// 从字符串的指定位置开始截取到字符串结尾的了符串
    /// </summary>
    /// <param name="str">原字符串</param>
    /// <param name="startIndex">子字符串的起始位置</param>
    /// <returns>子字符串</returns>
    private static String CutString(String str, int startIndex) {
        return CutString(str, startIndex, str.length());
    }

    /// <summary>
    /// MD5函数
    /// </summary>
    /// <param name="str">原始字符串</param>
    /// <returns>MD5结果</returns>
    private static String MD5(String str) throws NoSuchAlgorithmException {
        MessageDigest mdInst = MessageDigest.getInstance("MD5");
        return toHexString(mdInst.digest(str.getBytes()));
    }

    /// <summary>
    /// 用于 RC4 处理密码
    /// </summary>
    /// <param name="pass">密码字串</param>
    /// <param name="kLen">密钥长度，一般为 256</param>
    /// <returns></returns>
    static private byte[] GetKey(byte[] pass, int kLen) {
        byte[] mBox = new byte[kLen];

        for (int i = 0; i < kLen; i++) {
            mBox[i] = (byte) i;
        }

        int j = 0;
        for (int i = 0; i < kLen; i++) {

            j = (j + (mBox[i] + 256) % 256 + pass[i % pass.length])
                    % kLen;

            byte temp = mBox[i];
            mBox[i] = mBox[j];
            mBox[j] = temp;
        }

        return mBox;
    }

    /// <summary>
    /// 生成随机字符
    /// </summary>
    /// <param name="lens">随机字符长度</param>
    /// <returns>随机字符</returns>
    public static String RandomString(int lens) {
        char[] CharArray = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        int clens = CharArray.length;
        String sCode = "";
        Random random = new Random();
        for (int i = 0; i < lens; i++) {
            sCode += CharArray[Math.abs(random.nextInt(clens))];
        }
        return sCode;
    }

    /// <summary>
    /// 使用 Discuz authcode 方法对字符串加密
    /// </summary>
    /// <param name="source">原始字符串</param>
    /// <param name="key">密钥</param>
    /// <param name="expiry">加密字串有效时间，单位是秒</param>
    /// <returns>加密结果</returns>
    public static String authcodeEncode(String source, String key, int expiry) throws NiuSvrException {
        return authcode(source, key, Encode, expiry);

    }

    /// <summary>
    /// 使用 Discuz authcode 方法对字符串加密
    /// </summary>
    /// <param name="source">原始字符串</param>
    /// <param name="key">密钥</param>
    /// <returns>加密结果</returns>
    public static String authcodeEncode(String source, String key) throws NiuSvrException {
        return authcode(source, key, Encode, 0);

    }

    /// <summary>
    /// 使用 Discuz authcode 方法对字符串解密
    /// </summary>
    /// <param name="source">原始字符串</param>
    /// <param name="key">密钥</param>
    /// <returns>解密结果</returns>
    public static String authcodeDecode(String source, String key) throws NiuSvrException {
        return authcode(source, key, DiscuzAuthcodeMode.Decode, 0);

    }

    /// <summary>
    /// 使用 变形的 rc4 编码方法对字符串进行加密或者解密
    /// </summary>
    /// <param name="source">原始字符串</param>
    /// <param name="key">密钥</param>
    /// <param name="operation">操作 加密还是解密</param>
    /// <param name="expiry">加密字串过期时间</param>
    /// <returns>加密或者解密后的字符串</returns>
    private static String authcode(String source, String key,
                                   DiscuzAuthcodeMode operation, int expiry) throws NiuSvrException {
        try {
            if (source == null || key == null) {
                if (operation == Encode) {
                    throw new NiuSvrException(CommonUtilErrorCode.ENCRYPT_EXCEPTION);
                } else {
                    throw new NiuSvrException(CommonUtilErrorCode.DECRYPT_EXCEPTION);
                }
            }

            int ckey_length = 4;
            String keya, keyb, keyc, cryptkey, result;


            key = MD5(key);

            keya = MD5(CutString(key, 0, 16));

            keyb = MD5(CutString(key, 16, 16));

            source = operation == DiscuzAuthcodeMode.Decode ? new String(Base64.decodeBase64(source)) : source;
            keyc = ckey_length > 0 ? (operation == DiscuzAuthcodeMode.Decode ? CutString(
                    source, 0, ckey_length)
                    : RandomString(ckey_length))
                    : "";
            cryptkey = keya + MD5(keya + keyc);
            if (operation == DiscuzAuthcodeMode.Decode) {
                byte[] temp;

                temp = Base64.decodeBase64(CutString(source, ckey_length));
                result = new String(RC4(temp, cryptkey));
                if (CutString(result, 10, 16).equals(CutString(MD5(CutString(result, 26) + keyb), 0, 16))
                        && (Long.parseLong(CutString(result, 0, 10)) == 0 || Long.parseLong(CutString(result, 0, 10)) > getUnixTimestamp())) {
                    return CutString(result, 26);
                } else {
                    if (operation == Encode) {
                        throw new NiuSvrException(CommonUtilErrorCode.ENCRYPT_EXCEPTION);
                    } else {
                        throw new NiuSvrException(CommonUtilErrorCode.DECRYPT_EXCEPTION);
                    }
                }
            } else {
                source = (expiry == 0 ? "0000000000" : String.valueOf(getUnixTimestamp() + expiry)) + CutString(MD5(source + keyb), 0, 16)
                        + source;

                byte[] temp = RC4(source.getBytes("GBK"), cryptkey);

                return Base64.encodeBase64String((keyc + Base64.encodeBase64String(temp)).getBytes());

            }
        } catch (Exception e) {
            if (operation == Encode) {
                throw new NiuSvrException(CommonUtilErrorCode.ENCRYPT_EXCEPTION);
            } else {
                throw new NiuSvrException(CommonUtilErrorCode.DECRYPT_EXCEPTION);
            }
        }

    }

    // / <summary>
    // / RC4 原始算法
    // / </summary>
    // / <param name="input">原始字串数组</param>
    // / <param name="pass">密钥</param>
    // / <returns>处理后的字串数组</returns>
    private static byte[] RC4(byte[] input, String pass) {
        if (input == null || pass == null)
            return null;


        byte[] output = new byte[input.length];
        byte[] mBox = GetKey(pass.getBytes(), 256);

        // 加密
        int i = 0;
        int j = 0;

        for (int offset = 0; offset < input.length; offset++) {
            i = (i + 1) % mBox.length;
            j = (j + (mBox[i] + 256) % 256) % mBox.length;

            byte temp = mBox[i];
            mBox[i] = mBox[j];
            mBox[j] = temp;
            byte a = input[offset];

            //byte b = mBox[(mBox[i] + mBox[j] % mBox.Length) % mBox.Length];
            // mBox[j] 一定比 mBox.Length 小，不需要在取模
            byte b = mBox[(toInt(mBox[i]) + toInt(mBox[j])) % mBox.length];

            output[offset] = (byte) ((int) a ^ toInt(b));
        }

        return output;
    }

    private static int toInt(byte b) {
        return (b + 256) % 256;
    }

    private static long getUnixTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChar[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    public enum DiscuzAuthcodeMode {
        Encode, Decode
    }

}