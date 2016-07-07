package com.kab.chatclient;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by Kraskovskiy on 06.07.16.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int NUMBER_OF_VERSION_DB = 1;

    public static final String DATABASE_NAME = "chatClientDB";
    public static final String TABLE_NAME = "dataTable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_SENDER = "sender";
    public static final String COLUMN_DATE = "date";

    public static final Uri URI_TABLE_NAME = Uri.parse("sqlite://com.kab.chatclient/table/" + TABLE_NAME);

    public static final String DB_CREATE_STRING = "create table "+ TABLE_NAME+ " ("
            + COLUMN_ID+" integer primary key autoincrement,"
            + COLUMN_SENDER+" text,"
            + COLUMN_MESSAGE+" text,"
            + COLUMN_DATE+" text"
            +");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, NUMBER_OF_VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL(DB_CREATE_STRING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
