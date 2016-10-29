package com.kab.chatclient;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.kab.chatclient.MyDataBaseContract.ChatDbEntry;
/**
 * Created by Kraskovskiy on 06.07.2016.
 */
public class ChatClientDataBase {
    private final Context mContext;
    private DbHelper mDbHelper;
    private SQLiteDatabase mDb;

    public ChatClientDataBase(Context context) {
        mContext = context;
    }

    public void open() {
        mDbHelper = new DbHelper(mContext);
        mDb = mDbHelper.getWritableDatabase();
    }

    public void close() {
        if (mDbHelper !=null) mDbHelper.close();
    }

    public Cursor getAllData() {
        return mDb.query(ChatDbEntry.TABLE_NAME, null, null, null, null, null, null);
    }

    public void append(String sender, String message, String date) {
        ContentValues cv = new ContentValues();
        cv.put(ChatDbEntry.COLUMN_SENDER, sender);
        cv.put(ChatDbEntry.COLUMN_MESSAGE, message);
        cv.put(ChatDbEntry.COLUMN_DATE, date);
        mDb.insert(ChatDbEntry.TABLE_NAME, null, cv);
        mContext.getContentResolver().notifyChange(DbHelper.URI_TABLE_NAME, null);
    }

    public void delete(long id) {
        mDb.delete(ChatDbEntry.TABLE_NAME, ChatDbEntry.COLUMN_ID + " = " + id, null);
        mContext.getContentResolver().notifyChange(DbHelper.URI_TABLE_NAME, null);
    }

    public void dbTrunc()
    {
        mDb.execSQL("DROP TABLE " + ChatDbEntry.TABLE_NAME);
        mDb.execSQL(DbHelper.DB_CREATE_STRING);
        mContext.getContentResolver().notifyChange(DbHelper.URI_TABLE_NAME, null);
    }

    public void clearAll()
    {
        mDb.delete(ChatDbEntry.TABLE_NAME, null, null);
        mContext.getContentResolver().notifyChange(DbHelper.URI_TABLE_NAME, null);
    }
}
