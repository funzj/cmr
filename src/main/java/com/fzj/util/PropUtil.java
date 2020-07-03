package com.fzj.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author ZJ.Fun
 * @date 2020Äê7ÔÂ3ÈÕ11:03:41
 */
public class PropUtil {

    public static Properties prop;

    static {
        prop = new Properties();
        InputStream in = PropUtil.class.getResourceAsStream("/config.properties");
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProp(String key){
        return prop.getProperty(key);
    }

}
