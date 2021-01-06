package com.company.springmvcweb.data.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
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

    public static String convertDate(long timestamp) {
        Date date = new java.util.Date(timestamp * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm");
        return sdf.format(date);
    }

    public static String convertDate(long timestamp, String dateFormat) {
        Date date = new java.util.Date(timestamp * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    public static int getPriceInEuro(String price) {
        var newPrice = Double.valueOf(price);
        newPrice = newPrice * 100;
        return (int) Math.round(newPrice);
    }
}
