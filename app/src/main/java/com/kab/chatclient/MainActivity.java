package com.kab.chatclient;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.kab.chatclient.MyDataBaseContract.ChatDbEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private CursorObserver mObserver;
    private EditText mETSendMessageField;
    private ListView mListViewChat;
    private ChatClientDataBase mDb;
    private ChatCursorListAdapter mChatCursorListAdapter;
    private SenderMessageScheduler mSchedule = new SenderMessageScheduler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mETSendMessageField = (EditText) findViewById(R.id.edit_text_message);
        mETSendMessageField.setFocusableInTouchMode(true);
        mETSendMessageField.requestFocus();
        mETSendMessageField.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    sendMessageTo(v);
                    return true;
                }
                return false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessageTo(view);
            }
        });

        startSenderMessageScheduler();
        openDbConnection();
        createListViewChat();
    }

    private void sendMessageTo(View v) {
        ChatClientDataBase db = new ChatClientDataBase(getApplicationContext());
            db.open();
                Message m = new Message(getString(R.string.title_user_login), mETSendMessageField.getText().toString(), Utility.getCurrentDate());
                db.append(m.getSenderMessage(), m.getTextMessage(), m.getDateMessage());
            db.close();

        Toast.makeText(MainActivity.this, R.string.msg_toast_send_message, Toast.LENGTH_SHORT).show();
        mETSendMessageField.setText("");
        hideKeyboard(v);
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void clearAllHistory() {
        mDb.dbTrunc();
        getLoaderManager().getLoader(0).forceLoad();
    }

    public void openDbConnection() {
        mDb = new ChatClientDataBase(this);
        mDb.open();
        getLoaderManager().initLoader(0, null, this);
    }


    public void createListViewChat()  {
        String[] from = new String[]{ChatDbEntry.COLUMN_SENDER, ChatDbEntry.COLUMN_MESSAGE, ChatDbEntry.COLUMN_DATE};
        int[] to = new int[]{R.id.text_sender_item, R.id.text_item, R.id.text_date_item};

        mChatCursorListAdapter = new ChatCursorListAdapter(this, R.layout.my_list, null, from, to, 0);
        mListViewChat = (ListView) findViewById(R.id.list_view_chat);
        assert mListViewChat != null;
        mListViewChat.setAdapter(mChatCursorListAdapter);
        mListViewChat.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);

        mListViewChat.postDelayed(new Runnable() {
            @Override
            public void run() {
                mListViewChat.setSelection(mListViewChat.getCount());
            }
        }, 500);

        mListViewChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });
    }

    public void startSenderMessageScheduler(){
        mSchedule.startScheduler();
        mSchedule.sendMessageSchedule(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mObserver != null) {
            getContentResolver().unregisterContentObserver(mObserver);
            mObserver = null;
        }
        mSchedule.stopScheduler();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(this, mDb);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mChatCursorListAdapter.swapCursor(cursor);
        mObserver = new CursorObserver(new Handler(), loader);
        cursor.registerContentObserver(mObserver);
        cursor.setNotificationUri(getContentResolver(), DbHelper.URI_TABLE_NAME);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mChatCursorListAdapter.swapCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionBarItem1:
                clearAllHistory();
                Toast.makeText(this, R.string.action_text_clear_all, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

}
