package com.m.d.im.common.util.tool;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 马东 on 2017/9/23.
 */
public class MD5Util {
    /**
     * 加密
     * @param pwd
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String EncoderByMD5(String pwd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5= MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr=base64en.encode(md5.digest(pwd.getBytes("utf-8")));
        return newstr;
    }

    /**
     * 解密
     * @param pwd
     * @param oldPwd
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static boolean checkPwd(String pwd,String oldPwd) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(EncoderByMD5(pwd).equals(oldPwd)){
            return true;
        }
        return false;
    }
}
