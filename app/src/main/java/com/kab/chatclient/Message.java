package com.kab.chatclient;

import android.content.Context;
import java.util.Random;

/**
 * Created by Kraskovskiy on 06.07.16.
 */
public class Message {
    private String textMessage;
    private String senderMessage;
    private String dateMessage;
    private Random rnd = new Random();

    public Message(Context context) {
        senderMessage = context.getResources().getString(R.string.title_sender_name) + rnd.nextInt(100);
        textMessage = context.getResources().getString(R.string.msg_text_message) + rnd.nextInt(100);
        dateMessage = Utility.getCurrentDate();
    }

    public Message( String senderMessage, String textMessage, String dateMessage) {
        this.senderMessage = senderMessage;
        this.textMessage = textMessage;
        this.dateMessage = dateMessage;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public String getSenderMessage() {
        return senderMessage;
    }

    public String getDateMessage() {
        return dateMessage;
    }


    @Override
    public String toString() {
        return senderMessage +
                "\n" +
                textMessage +
                "\n" +
                dateMessage +
                "\n";
    }
}
