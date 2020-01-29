package com.ms.demo.siyu.mymessenger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ms.demo.siyu.mymessenger.R;
import com.ms.demo.siyu.mymessenger.entity.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    List<User> friendsList;
    private Context mContext;

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row_new_message, parent, false);
        return new MessageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        User friend = friendsList.get(position);
        holder.userName.setText(friend.getUserName());
        Glide.with(mContext).load(friend.getProfileImg()).into(holder.avatar);
        holder.itemView.setTag(friend.getUserName());
    }

    @Override
    public int getItemCount() {
        return friendsList == null? 0 : friendsList.size();
    }

    public void setUsers(List<User> users) {
        friendsList = users;
        notifyDataSetChanged();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView userName;
        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            userName = itemView.findViewById(R.id.user_name);
        }
    }
}
