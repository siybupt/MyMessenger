package com.ms.demo.siyu.mymessenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ms.demo.siyu.mymessenger.adapter.MessageAdapter;
import com.ms.demo.siyu.mymessenger.entity.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewMessageActivity extends AppCompatActivity {
    public static final String USER_KEY = "USER_NAME";
    MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        messageAdapter = new MessageAdapter();
        RecyclerView recyclerView = findViewById(R.id.friend_list_recycler_view);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        fetchUsersFromFirebase();

    }

    private void fetchUsersFromFirebase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();
                Iterator<DataSnapshot> iterator  = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    users.add(iterator.next().getValue(User.class));
                }
                messageAdapter.setUsers(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onItemClick(View view) {

        Intent intent = new Intent(this, ChatLogActivity.class);
        String user  = (String)view.getTag();
        intent.putExtra(USER_KEY, user);
        startActivity(intent);
    }
}
