package com.niuniu.motion.common.util;

import org.apache.commons.codec.binary.Base32;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class DESUtil {
  static Cipher ecipher;

  static Cipher dcipher;

  final static String strkey = "*fdY5L!@Up':qpasdfml_zaeYugHfjnv";

  static {
    DESKeySpec dks;
    try {
      dks = new DESKeySpec(strkey.getBytes());
      SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
      SecretKey desKey = skf.generateSecret(dks);
      ecipher = Cipher.getInstance("DES");
      dcipher = Cipher.getInstance("DES");
      ecipher.init(Cipher.ENCRYPT_MODE, desKey);
      dcipher.init(Cipher.DECRYPT_MODE, desKey);
    } catch (InvalidKeyException | NoSuchAlgorithmException
        | InvalidKeySpecException | NoSuchPaddingException e) {
      e.printStackTrace();
    }
  }

  public static String encrypt(String str) throws Exception {
    // Encode the string into bytes using utf-8
    byte[] utf8 = str.getBytes("UTF8");

    // Encrypt
    byte[] enc = ecipher.doFinal(utf8);

    // Encode bytes to base32 to get a string
    return urlFriendlyEncoding(new Base32().encodeToString(enc));
  }

  public static String decrypt(String str) throws Exception {
    str = urlFriendlyDecoding(str);
    // Decode base32 to get bytes
    byte[] dec = new Base32().decode(str);

    byte[] utf8 = dcipher.doFinal(dec);

    // Decode using utf-8
    return new String(utf8, "UTF8");
  }

  private static String urlFriendlyEncoding(String base32str) {
    return base32str.replace("=", "").toLowerCase();
  }

  private static String urlFriendlyDecoding(String friendlstr) {
    String str = friendlstr;
    if (str.length() % 4 != 0) {
      str+="===".substring(0, 4-str.length() % 4);
    }
    return str.toUpperCase();
  }

}
