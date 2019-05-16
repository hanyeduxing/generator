package com.sf.gis.cds.common.util;
/**
 * 生成32位长度的主键
 * @author 590582
 *
 */
public class GenerateUUID {
    public static synchronized String getUUID() {
        long time = System.currentTimeMillis();
        String timePattern = Long.toHexString(time);
        int leftBit = 14 - timePattern.length();
        if (leftBit > 0) {
            timePattern = "0000000000".substring(0, leftBit) + timePattern;
        }
        String uuid = timePattern
                + Long.toHexString(Double.doubleToLongBits(Math.random()))
                + Long.toHexString(Double.doubleToLongBits(Math.random()))
                + "000000000000000000";
        uuid = uuid.substring(0, 32).toUpperCase();
        return uuid;
    }
}
