package com.kab.chatclient;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

/**
 * Created by Kraskovskiy on 07.07.2016.
 */
public class MyCursorLoader extends CursorLoader {
    private DB mDB;

    public MyCursorLoader(Context context, DB db) {
        super(context);
        this.mDB = db;
    }

    @Override
    public Cursor loadInBackground() {
        return mDB.getAllData();
    }

}
