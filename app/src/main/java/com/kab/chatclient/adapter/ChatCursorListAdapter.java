package com.kab.chatclient.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.kab.chatclient.R;
import com.kab.chatclient.data.MyDataBaseContract.ChatDbEntry;

/**
 * Created by Kraskovskiy on 07.07.16.
 */
public class ChatCursorListAdapter extends SimpleCursorAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public ChatCursorListAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    private int getItemViewType(Cursor cursor) {
        int sender_index = cursor.getColumnIndexOrThrow(ChatDbEntry.COLUMN_SENDER);
        if (!cursor.getString(sender_index).equals(mContext.getResources().getString(R.string.title_user_login))) {
            return 0;
        } else {
            return 1;
        }
    }

    private Spannable getSpanText(String string) {
        Spannable spannable = new SpannableString(string);
        if (!string.contains("#")) {
            spannable.setSpan(new ForegroundColorSpan(Color.GREEN), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spannable = findAndColorHashTag(string);
        }
        return spannable;
    }

    private Spannable findAndColorHashTag(String string) {
        String[] stringArray = string.split(" ");
        SpannableStringBuilder sb = new SpannableStringBuilder();

        for (int i = 0; i < stringArray.length; i++) {
            SpannableString spannable = new SpannableString(stringArray[i]);

            if (stringArray[i].contains("#")) {
                spannable.setSpan(new ForegroundColorSpan(Color.RED), stringArray[i].indexOf("#"), stringArray[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new ForegroundColorSpan(Color.GREEN), 0, stringArray[i].indexOf("#"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spannable.setSpan(new ForegroundColorSpan(Color.GREEN), 0, stringArray[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if ((i + 1) < stringArray.length) {
                sb.append(spannable).append(" ");
            } else {
                sb.append(spannable);
            }
        }
        return sb;
    }

    @Override
    public int getItemViewType(int position) {
        Cursor cursor = (Cursor) getItem(position);
        return getItemViewType(cursor);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.txtSender.setText(cursor.getString(cursor.getColumnIndex(ChatDbEntry.COLUMN_SENDER)));
        holder.txtMessage.setText(getSpanText(cursor.getString(cursor.getColumnIndex(ChatDbEntry.COLUMN_MESSAGE))));
        holder.txtDate.setText(cursor.getString(cursor.getColumnIndex(ChatDbEntry.COLUMN_DATE)));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        View v = null;

        if (getItemViewType(cursor) == 0) {
            v = mInflater.inflate(R.layout.my_list, parent, false);
            holder.txtSender = (TextView) v.findViewById(R.id.text_sender_item);
            holder.txtMessage = (TextView) v.findViewById(R.id.text_item);
            holder.txtDate = (TextView) v.findViewById(R.id.text_date_item);
        } else {
            v = mInflater.inflate(R.layout.my_list_invert, parent, false);
            holder.txtSender = (TextView) v.findViewById(R.id.text_sender_item);
            holder.txtMessage = (TextView) v.findViewById(R.id.text_item);
            holder.txtDate = (TextView) v.findViewById(R.id.text_date_item);
        }

        v.setTag(holder);
        return v;
    }

    private static class ViewHolder {
        private TextView txtSender;
        private TextView txtMessage;
        private TextView txtDate;
    }
}

