package com.eta.core.util;


import org.apache.commons.lang3.RandomStringUtils;

public class IDUtil {
    /**
     * 生产主键
     *
     * @return
     */
    public static String random4() {
        String num = System.currentTimeMillis()+RandomStringUtils.randomNumeric(4);
        return num;
    }

    public static String randomUnion(String no) {
        String num = RandomStringUtils.randomNumeric(4);
        return no + num;
    }
}
