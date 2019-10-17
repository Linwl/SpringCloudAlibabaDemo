package com.linwl.springcloudalibabademo.utils;

import javax.crypto.Cipher;
import java.security.Key;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 10:37
 * @description ：
 * @modified By：
 */
public class DESUtils {
    private DESUtils(){}

    private static String strDefaultKey = "linwl#@^&#@";


    /**
     * 加密字节数组
     * @param arrB  需加密的字节数组
     * @return 加密后的字节数组
     */
    public static byte[] encrypt(byte[] arrB, Key key) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        return encryptCipher.doFinal(arrB);
    }

    /**
     * 加密字符串
     * @param strIn  需加密的字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String strIn) throws Exception {
        return CommonTools.byteArr2HexStr(encrypt(strIn.getBytes(),getKey(strDefaultKey.getBytes())));
    }

    /**
     * 加密字符串
     * @param strIn 需加密的字符串
     * @param key 加密key
     * @return
     * @throws Exception
     */
    public static String encrypt(String strIn,String key) throws Exception {
        return CommonTools.byteArr2HexStr(encrypt(strIn.getBytes(),getKey(key.getBytes())));
    }

    /**
     * 解密字节数组
     * @param arrB  需解密的字节数组
     * @return 解密后的字节数组
     */
    public static byte[] decrypt(byte[] arrB,Key key) throws Exception {
        Cipher decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
        return decryptCipher.doFinal(arrB);
    }

    /**
     * 解密字符串
     * @param strIn  需解密的字符串
     * @return 解密后的字符串
     */
    public static String decrypt(String strIn) throws Exception {
        return new String(decrypt(CommonTools.hexStr2ByteArr(strIn),getKey(strDefaultKey.getBytes())));
    }

    /**
     * 解密字符串
     * @param strIn 需要解密的字符串
     * @param key 解密key
     * @return 解密后的字符串
     * @throws Exception
     */
    public static String decrypt(String strIn,String key) throws Exception {
        return new String(decrypt(CommonTools.hexStr2ByteArr(strIn),getKey(key.getBytes())));
    }

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
     * @param arrBTmp  构成该字符串的字节数组
     * @return 生成的密钥
     */
    private static Key getKey(byte[] arrBTmp) throws Exception {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];
        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        // 生成密钥
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
        return key;
    }

}
