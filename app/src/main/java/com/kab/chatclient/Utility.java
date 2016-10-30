package com.kab.chatclient;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Kraskovskiy on 06.07.16.
 */
public class Utility {
    private Utility() {}

    public static  String getCurrentDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.US);
        return dateFormat.format(c.getTime());
    }

    public static Message messageGenerator(Context context) {
        Random rnd = new Random();
        if (rnd.nextInt(100)>45) {
            return  new Message(context);
        } else {
            return  new Message(context.getString(R.string.title_sender_default_name),context.getString(R.string.msg_sender_default_message), Utility.getCurrentDate());
        }
    }

    public static void saveMessageInDb(Context context, Message message) {
        ChatClientDataBase db = new ChatClientDataBase(context);
        db.open();
        db.append(message.getSenderMessage(), message.getTextMessage(), message.getDateMessage());
        db.close();
    }

}
