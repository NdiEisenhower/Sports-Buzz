package com.mmbadi.sportsbuzz.utills;

import android.text.TextUtils;

import java.text.SimpleDateFormat;

public class DateHelper {

    public static String getFormattedDate(long date) {
        String formattedDated = "";
        if (date > 0) {
            try {

                long itemLong = (long) (date / 1000);
                java.util.Date d = new java.util.Date(itemLong * 1000L);
                formattedDated = new SimpleDateFormat("yyy-MM-dd HH:mm").format(d);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return formattedDated;
    }

    public static long extractDate(String date){

        String preRegex ="/Date\\(";
        String postRegex ="\\+0200\\)/";
        long dateLong = 0;

        if(!TextUtils.isEmpty(date) ){
            try {
                date = date.replaceFirst(preRegex,"");
                date = date.replaceFirst(postRegex,"");
                dateLong = Long.parseLong(date);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return dateLong;
    }
}
