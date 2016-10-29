package com.kab.chatclient;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Kraskovskiy on 06.07.16.
 */
public class Utility {
    private Utility() {}

    public static  String getCurrentDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        return dateFormat.format(c.getTime());
    }
}
