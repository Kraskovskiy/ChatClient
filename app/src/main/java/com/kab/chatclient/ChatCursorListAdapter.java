package com.kab.chatclient;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.kab.chatclient.MyDataBaseContract.ChatDbEntry;
/**
 * Created by Kraskovskiy on 07.07.16.
 */
public class ChatCursorListAdapter extends SimpleCursorAdapter {
        private Context mContext;
        private int mLayout;
        private int mLayoutInvert;
        private Cursor mCursor;
        private final LayoutInflater mInflater;

        public ChatCursorListAdapter(Context context, int layout,int layoutInvert, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
            mLayout =layout;
            mLayoutInvert =layoutInvert;
            mContext = context;
            mInflater =LayoutInflater.from(context);
            mCursor =c;
        }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(mLayout, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        TextView txtSender = (TextView) view.findViewById(R.id.text_sender_item);
        TextView txtMessage = (TextView) view.findViewById(R.id.text_item);

        int message_index = cursor.getColumnIndexOrThrow(ChatDbEntry.COLUMN_MESSAGE);
        int sender_index = cursor.getColumnIndexOrThrow(ChatDbEntry.COLUMN_SENDER);

        if (cursor.getString(message_index).startsWith("#")) {
            txtMessage.setTextColor(Color.RED);
        } else {
            txtMessage.setTextColor(Color.GREEN);
        }

        if (cursor.getString(sender_index).equals(mContext.getResources().getString(R.string.title_user_login))) {
            txtSender.setTextColor(Color.BLUE);
        } else {
            txtSender.setTextColor(Color.GREEN);
        }

    }

}

