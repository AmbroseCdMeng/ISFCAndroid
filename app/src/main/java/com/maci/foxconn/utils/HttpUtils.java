package com.maci.foxconn.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {
    /**
     * Http Get Request
     *
     * @param path example: http://localhost/users/login?username=username&password=password
     * @return
     */
    public static String doGet(String path) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();

            if (HttpURLConnection.HTTP_OK == code) {
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                is.close();
                return baos.toString();
            }
        } catch (MalformedURLException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        }
        return null;
    }


    /**
     * Http Post Request
     *
     * @param path    example: http://localhost/users/login
     * @param context username=username&password=password
     *                If it has Chinese Words, Like this:   URLEncode.encode(""Chinese Words", "utf-8")
     * @return
     */
    public static String doPost(String path, String context) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-length", String.valueOf(context.length()));
            conn.setDoOutput(true);
            conn.getOutputStream().write(context.getBytes());

            int code = conn.getResponseCode();
            if (HttpURLConnection.HTTP_OK == code) {
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = is.read()) != -1) {
                    baos.write(buffer, 0, len);
                }
                is.close();
                return baos.toString();
            }
        } catch (IOException e) {
            return e.getMessage();
        }
        return null;
    }
}
