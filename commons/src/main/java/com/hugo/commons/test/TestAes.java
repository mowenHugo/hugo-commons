package com.hugo.commons.test;

import org.apache.commons.codec.binary.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Author : hugo
 * @Date : 15/3/10 上午9:40.
 */
public class TestAes {

    public static String src = "Test Aes Security";

    public static void main(String[] args) {
        testAes();
    }

    public static void testAes() {
        try {
            //生成Key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();

            //key的转换
            Key key = new SecretKeySpec(keyBytes, "AES");

            //加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("jdk aes encrypt : " + Base64.encodeBase64String(result));

            //解密
            cipher.init(Cipher.DECRYPT_MODE, key);
            result = cipher.doFinal(result);
            System.out.println("jdk aes decrypt: " + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
