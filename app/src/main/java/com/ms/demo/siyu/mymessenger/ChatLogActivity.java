package com.ms.demo.siyu.mymessenger;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ms.demo.siyu.mymessenger.adapter.ChatLogAdapter;
import com.ms.demo.siyu.mymessenger.entity.ChatLog;

public class ChatLogActivity extends AppCompatActivity {

    ChatLogAdapter mChatLogAdapter;
    EditText mChatContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_log);
        mChatContent = findViewById(R.id.message_text);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && getIntent() != null) {
            actionBar.setTitle(getIntent().getStringExtra(NewMessageActivity.USER_KEY));
        }
        mChatLogAdapter = new ChatLogAdapter();
        mChatLogAdapter.addItem(new ChatLog("aaaa", null, "This is message 1"));
        mChatLogAdapter.addItem(new ChatLog(null, "tttt", "Send message out"));
        mChatLogAdapter.addItem(new ChatLog("aaaa", null, "Send out message to you asdfasdfsdf"));
        mChatLogAdapter.addItem(new ChatLog(null, "tttt", "Send message outd adfadf asf a af sf "));

        RecyclerView recyclerView = findViewById(R.id.recycler_view_chatlog);
        recyclerView.setAdapter(mChatLogAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void onSend(View view) {
        mChatLogAdapter.addItem(new ChatLog("aaaa", null, mChatContent.getText().toString()));

    }
}
