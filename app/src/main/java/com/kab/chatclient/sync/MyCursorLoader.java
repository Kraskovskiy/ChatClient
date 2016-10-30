package com.kab.chatclient.sync;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

import com.kab.chatclient.data.ChatClientDataBase;

/**
 * Created by Kraskovskiy on 07.07.2016.
 */
public class MyCursorLoader extends CursorLoader {
    private ChatClientDataBase mDB;

    public MyCursorLoader(Context context, ChatClientDataBase db) {
        super(context);
        this.mDB = db;
    }

    @Override
    public Cursor loadInBackground() {
        return mDB.getAllData();
    }

}
