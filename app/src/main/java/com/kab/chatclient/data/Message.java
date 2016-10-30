package com.kab.chatclient.data;

import android.content.Context;

import java.util.Random;

import com.kab.chatclient.R;
import com.kab.chatclient.Utility;

/**
 * Created by Kraskovskiy on 06.07.16.
 */
public class Message {
    private String mTextMessage;
    private String mSenderMessage;
    private String mDateMessage;

    public Message(Context context) {
        Random rnd = new Random();
        mSenderMessage = context.getResources().getString(R.string.title_sender_name) + rnd.nextInt(100);
        mTextMessage = context.getResources().getString(R.string.msg_text_message) + rnd.nextInt(100);
        mDateMessage = Utility.getCurrentDate();
    }

    public Message(String senderMessage, String textMessage, String dateMessage) {
        mSenderMessage = senderMessage;
        mTextMessage = textMessage;
        mDateMessage = dateMessage;
    }

    public String getTextMessage() {
        return mTextMessage;
    }

    public String getSenderMessage() {
        return mSenderMessage;
    }

    public String getDateMessage() {
        return mDateMessage;
    }

    @Override
    public String toString() {
        return mSenderMessage +
                "\n" +
                mTextMessage +
                "\n" +
                mDateMessage +
                "\n";
    }
}
