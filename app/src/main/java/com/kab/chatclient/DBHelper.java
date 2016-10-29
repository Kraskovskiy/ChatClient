package com.kab.chatclient;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import com.kab.chatclient.MyDataBaseContract.ChatDbEntry;

/**
 * Created by Kraskovskiy on 06.07.16.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final int NUMBER_OF_VERSION_DB = 1;

    public static final Uri URI_TABLE_NAME = Uri.parse("sqlite://com.kab.chatclient/table/" + ChatDbEntry.TABLE_NAME);

    public static final String DB_CREATE_STRING = "create table "+ ChatDbEntry.TABLE_NAME+ " ("
            + ChatDbEntry.COLUMN_ID+" integer primary key autoincrement,"
            + ChatDbEntry.COLUMN_SENDER+" text,"
            + ChatDbEntry.COLUMN_MESSAGE+" text,"
            + ChatDbEntry.COLUMN_DATE+" text"
            +");";

    public DbHelper(Context context) {
        super(context, ChatDbEntry.DATABASE_NAME, null, NUMBER_OF_VERSION_DB);
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
