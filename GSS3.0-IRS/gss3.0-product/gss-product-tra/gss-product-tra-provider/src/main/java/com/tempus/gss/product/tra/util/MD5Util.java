package com.tempus.gss.product.tra.util;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static byte[] getMD5Mac(byte[] bySourceByte) {
        byte[] byDisByte;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(bySourceByte);
            byDisByte = md.digest();
        } catch (NoSuchAlgorithmException n) {

            return (null);
        }
        return (byDisByte);
    }

    public static String bintoascii(byte[] bySourceByte) {
        int len, i;
        byte tb;
        char high, tmp, low;
        String result = new String();
        len = bySourceByte.length;
        for (i = 0; i < len; i++) {
            tb = bySourceByte[i];
            tmp = (char) ((tb >>> 4) & 0x000f);
            if (tmp >= 10)
                high = (char) ('a' + tmp - 10);
            else
                high = (char) ('0' + tmp);
            result += high;
            tmp = (char) (tb & 0x000f);
            if (tmp >= 10)
                low = (char) ('a' + tmp - 10);
            else
                low = (char) ('0' + tmp);
            result += low;
        }
        return result;
    }

    public static String getMD5ofStr(String inbuf){
        if (inbuf == null || "".equals(inbuf)) return "";
        return bintoascii(getMD5Mac(inbuf.getBytes()));
    }
    
    public static String getMD5ofStr(String inbuf, String charset) {
        if (inbuf == null || "".equals(inbuf)) return "";
        try {
            return bintoascii(getMD5Mac(inbuf.getBytes(charset)));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    
    public static String getSign(String inbuf) {
    	return getMD5ofStr(inbuf,"GBK").toLowerCase();
    }

   
}
