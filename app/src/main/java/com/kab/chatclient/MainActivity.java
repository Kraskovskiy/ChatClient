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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private AsyncFakeServer mAsyncFakeServer;
    private CursorObserver mObserver;
    private EditText mETSendMessageField;
    private ListView mListViewChat;
    private DB mDB;
    private CustomCursorListAdapter mCustomCursorListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mETSendMessageField = (EditText) findViewById(R.id.etMessage);
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

        txtToChat();
        dbConnection();
        createListViewAsync();
    }

    private void sendMessageTo(View v) {
        DB db = new DB(getApplicationContext());
            db.open();
                Message m = new Message(getString(R.string.userLogin), mETSendMessageField.getText().toString(), Utils.getCurrentDate());
                db.append(m.getSenderMessage(), m.getTextMessage(), m.getDateMessage());
            db.close();

        Toast.makeText(MainActivity.this, R.string.toastSendMessage, Toast.LENGTH_SHORT).show();
        mETSendMessageField.setText("");
        hideKeyboard(v);
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void ClearAllHistory() {
        mDB.dbTrunc();
        getLoaderManager().getLoader(0).forceLoad();
    }

    public void dbConnection() {
        mDB = new DB(this);
        mDB.open();
        getLoaderManager().initLoader(0, null, this);
    }


    public void createListViewAsync()  {
        String[] from = new String[]{DBHelper.COLUMN_SENDER, DBHelper.COLUMN_MESSAGE, DBHelper.COLUMN_DATE};
        int[] to = new int[]{R.id.senderItem, R.id.textItem, R.id.dateItem};

        mCustomCursorListAdapter = new CustomCursorListAdapter(this, R.layout.my_list, null, from, to, 0);
        mListViewChat = (ListView) findViewById(R.id.txtChatList);
        assert mListViewChat != null;
        mListViewChat.setAdapter(mCustomCursorListAdapter);
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

    public void txtToChat() {
        mAsyncFakeServer = new AsyncFakeServer(getApplicationContext());
        mAsyncFakeServer.execute();
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
                ClearAllHistory();
                Toast.makeText(this, R.string.textClearAll, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    protected void onDestroy() {
        super.onDestroy();
        if (mObserver != null) {
            getContentResolver().unregisterContentObserver(mObserver);
            mObserver = null;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(this, mDB);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCustomCursorListAdapter.swapCursor(cursor);
        mObserver = new CursorObserver(new Handler(), loader);
        cursor.registerContentObserver(mObserver);
        cursor.setNotificationUri(getContentResolver(), DBHelper.URI_TABLE_NAME);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCustomCursorListAdapter.swapCursor(null);
    }

}
