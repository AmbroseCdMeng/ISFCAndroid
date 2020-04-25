package com.maci.foxconn.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class HttpUtilsTest {

    @Test
    public void doGet() {
        String url = "http://10.161.139.45:5088/api/App/QueryInStockForms?formno=20200328";
        String result = HttpUtils.doGet(url);

        String r1 = "http://10.161.139.45:5088/api/Sys/LoginValidate?sysname=A&userid=F1680502&pwd=F1680502&ip=10";
        String r2 = HttpUtils.doGet(r1);
    }

    @Test
    public void doPost() {
    }
}