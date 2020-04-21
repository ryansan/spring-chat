package com.bachelor2020.chat.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class AESUtil {
    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 128);//OG: 16
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public String encryptWithKey(String strToEncrypt, byte[] secretKey)
    {
        try
        {
            // rebuild key using SecretKeySpec
            //SecretKey originalKey = new SecretKeySpec(secretKey, 0, secretKey.length, "AES");

            SecretKey convertedKey = convertSecretKeyFromBytesToSecretKey(secretKey);

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            System.out.println("Ciper ok");
            cipher.init(Cipher.ENCRYPT_MODE, convertedKey);
            System.out.println("init ok");
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public String decryptWithKey(String strToDecrypt, byte[] secretKey)
    {
        try
        {
            // rebuild key using SecretKeySpec
            //SecretKey originalKey = new SecretKeySpec(secretKey, 0, secretKey.length, "AES");
            SecretKey convertedKey = convertSecretKeyFromBytesToSecretKey(secretKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, convertedKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public byte[] generateSecretKey(){
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyGen.init(128); // for example
        SecretKey secretKey = keyGen.generateKey();
        return secretKey.getEncoded();
    }

    public SecretKey convertSecretKeyFromBytesToSecretKey(byte[] bytes){
        return new SecretKeySpec(bytes, 0, bytes.length, "AES");
    }
}
