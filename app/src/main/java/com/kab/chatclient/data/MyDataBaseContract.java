package com.kab.chatclient.data;

import android.provider.BaseColumns;

/**
 * Created by Kraskovskiy on 29.10.2016.
 */

public class MyDataBaseContract {
    private MyDataBaseContract() {}

    public static abstract class ChatDbEntry implements BaseColumns {
        public static final String DATABASE_NAME = "chatClientDB";
        public static final String TABLE_NAME = "dataTable";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_MESSAGE = "message";
        public static final String COLUMN_SENDER = "sender";
        public static final String COLUMN_DATE = "date";
    }
}
