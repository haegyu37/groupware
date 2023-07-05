package com.groupware.wimir.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    public static String generateMD5(String input) {
        try {
            MessageDigest mdMD5 = MessageDigest.getInstance("MD5");
            byte[] md5Hash = mdMD5.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexMD5hash = new StringBuilder();
            for (byte b : md5Hash) {
                String hexString = String.format("%02x", b);
                hexMD5hash.append(hexString);
            }
            return hexMD5hash.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}

