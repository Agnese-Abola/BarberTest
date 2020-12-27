package com.company.springmvcweb.data.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * helps model classes
 */
public class Helper {

    /**
     * add String to String in required position
     *
     * @param value
     * @param positionFromEnd
     * @param toInsert
     * @return
     */
    public static String adCharToString(long value, int positionFromEnd, String toInsert) {
        String stringValue = String.valueOf(value);
        int length = stringValue.length();
        String newValue = "";
        for (int i = 0; i < length; i++) {
            if (i == length - positionFromEnd) {
                newValue = newValue + toInsert;
            }
            newValue = newValue + stringValue.charAt(i);
        }
        return newValue;
    }

    /**
     * converts from unixtime to date
     * @param timestamp
     * @return
     */
    public static String convertDate(long timestamp) {
        Date date = new java.util.Date(timestamp * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm");
        return sdf.format(date);
    }

    /**
     * converts from unixtime to date in given format
     * @param timestamp
     * @param dateFormat
     * @return
     */
    public static String convertDate(long timestamp, String dateFormat) {
        Date date = new java.util.Date(timestamp * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }
}
