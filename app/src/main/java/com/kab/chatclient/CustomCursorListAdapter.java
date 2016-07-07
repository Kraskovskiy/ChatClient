package com.kab.chatclient;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by Kraskovskiy on 07.07.16.
 */
public class CustomCursorListAdapter extends SimpleCursorAdapter {
        private Context mContext;
        private int mLayout;
        private Cursor mCursor;
        private final LayoutInflater mInflater;

        public CustomCursorListAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
            mLayout =layout;
            mContext = context;
            mInflater =LayoutInflater.from(context);
            mCursor =c;
        }

        @Override
        public View newView (Context context, Cursor cursor, ViewGroup parent) {
            return mInflater.inflate(mLayout, null);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);

            TextView txtSender = (TextView) view.findViewById(R.id.senderItem);
            TextView txtMessage = (TextView) view.findViewById(R.id.textItem);
            TextView txtDate = (TextView) view.findViewById(R.id.dateItem);

            int message_index=cursor.getColumnIndexOrThrow(DBHelper.COLUMN_MESSAGE);
            int sender_index=cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SENDER);

            if (cursor.getString(message_index).startsWith("#")) {
                txtMessage.setTextColor(Color.RED);
            } else {
                txtMessage.setTextColor(Color.GREEN);
            }

            if (cursor.getString(sender_index).equals(mContext.getResources().getString(R.string.userLogin))) {
                txtSender.setTextColor(Color.BLUE);
            } else {
                txtSender.setTextColor(Color.GREEN);
            }

        }

    }

