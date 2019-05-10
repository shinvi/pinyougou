package com.pinyougou.common.util;

import kotlin.text.Charsets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getSha1(String value) {
        return encode("sha1", value);
    }

    public static String getMD5(String value) {
        return encode("md5", value);
    }

    private static String encode(String algorithm, String value) {
        if (value == null) {
            return null;
        }
        try {
            MessageDigest messageDigest
                    = MessageDigest.getInstance(algorithm);
            messageDigest.update(value.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    private static String toHexString(byte[] bytes, String separator) {
        StringBuilder hexString = new StringBuilder();
        byte[] var4 = bytes;
        int var5 = bytes.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            byte b = var4[var6];
            String hexstring = Integer.toHexString(255 & b);
            if (hexstring.length() < 2) {
                hexString.append("0");
            }

            hexString.append(Integer.toHexString(255 & b)).append(separator);
        }

        return hexString.toString();
    }

    public static String formatVoucherF(String voucher) {
        if (voucher != null && !voucher.trim().equals("")) {
            StringBuilder sBuilder = new StringBuilder();
            int pos = 0;

            do {
                if (sBuilder.length() == 0) {
                    sBuilder.append(voucher.substring(pos, pos + 4));
                } else {
                    sBuilder.append(String.format("  %s", voucher.substring(pos, pos + 4)));
                }

                pos += 4;
            } while (pos < voucher.length());

            return sBuilder.toString();
        } else {
            return "";
        }
    }
}
