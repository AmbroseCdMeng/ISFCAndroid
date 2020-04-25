package com.maci.foxconn.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

    private static Properties getProperties(String name) {
        Properties props = new Properties();
        InputStream inputStream = Utils.class.getResourceAsStream(name);
        try {
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }


    public static Properties getNetConfigProperties() {
        return getProperties("/assets/net-config.properties");
    }

    public static String getNetConfigProperties(String key) {
        return getNetConfigProperties().getProperty(key);
    }

    public static String getNetConfigProperties(String key, String defaultValue) {
        return getNetConfigProperties().getProperty(key, defaultValue);
    }

    public static Properties getAppConfigProperties() {
        return getProperties("/assets/app-config.properties");
    }

    public static String getAppConfigProperties(String key) {
        return getAppConfigProperties().getProperty(key);
    }

    public static String getAppConfigProperties(String key, String defaultValue) {
        return getAppConfigProperties().getProperty(key, defaultValue);
    }
}
