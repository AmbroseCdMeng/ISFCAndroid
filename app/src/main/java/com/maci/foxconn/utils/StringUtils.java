package com.maci.foxconn.utils;

public class StringUtils {
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isNullOrEmpty(Object string) {
        return string == null || string.toString().isEmpty();
    }

    public static String defaultIfNullOrEmpty(String string, String defaultValue) {
        return isNullOrEmpty(string) ? defaultValue : string;
    }


    public static String defaultIfNullOrEmpty(Object string, String defaultValue) {
        return isNullOrEmpty(string) ? defaultValue : string.toString();
    }
}
