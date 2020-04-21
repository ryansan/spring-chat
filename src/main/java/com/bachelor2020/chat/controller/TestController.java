package com.bachelor2020.chat.controller;

import com.bachelor2020.chat.util.AESUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;

@RestController
@CrossOrigin
public class TestController {

    @GetMapping("/test/test")
    public void test(){
        //TEST Generating key works and can be used
        //Security.setProperty("crypto.policy", "unlimited");
        AESUtil aesUtil = new AESUtil();
        byte[] secretKey = aesUtil.generateSecretKey();
        System.out.println("Created key with value " + secretKey);

        //TEST encrypting and decrypting works
        String originalString = "howtodoinjava.com";
        String encryptedString = aesUtil.encryptWithKey(originalString, secretKey) ;
        String decryptedString = aesUtil.decryptWithKey(encryptedString, secretKey) ;

        System.out.println(originalString);
        System.out.println(encryptedString);
        System.out.println(decryptedString);

    }
}
