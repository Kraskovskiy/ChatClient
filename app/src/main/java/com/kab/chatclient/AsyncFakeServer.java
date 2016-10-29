package com.kab.chatclient;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import java.util.Random;

/**
 * Created by Kraskovskiy on 06.07.16.
 */
public class AsyncFakeServer extends AsyncTask<Void, Void, Void> {
    private Context mContext;

    public AsyncFakeServer(Context context) {
        this.mContext = context;
    }

    private void createTxtMessage() {
        ChatClientDataBase db = new ChatClientDataBase(mContext);
        Random rnd = new Random();
        while (true) {
            try {
                db.open();
                    Message m = messageGenerator();
                    db.append(m.getSenderMessage(),m.getTextMessage(),m.getDateMessage());
                db.close();

                Thread.sleep(1000*(rnd.nextInt(4)+1));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Message messageGenerator() {
        Random rnd = new Random();
        if (rnd.nextInt(100)>45) {
          return  new Message(mContext);
        } else {
            return  new Message(mContext.getString(R.string.title_sender_default_name),mContext.getString(R.string.msg_sender_default_message), Utility.getCurrentDate());
        }
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        Looper.prepare();
        createTxtMessage();
        return null;
    }

}