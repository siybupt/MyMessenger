package com.ms.demo.siyu.mymessenger.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.demo.siyu.mymessenger.R;
import com.ms.demo.siyu.mymessenger.entity.ChatLog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatLogAdapter extends RecyclerView.Adapter<ChatLogAdapter.ChatItemHolder> {

    List<ChatLog> chatLogList = new ArrayList<>();
    private static final int TYPE_RECEIVE = 0;
    private static final int TYPE_SEND = 1;
    @NonNull
    @Override
    public ChatItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater.from(parent.getContext()).inflate(R.layout.)
        View itemView;
        switch (viewType) {
            case TYPE_RECEIVE:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_from_row, parent, false);

                return new FromChatItemHolder(itemView);
            case TYPE_SEND:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_to_row, parent, false);
                return new ToChatItemHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatItemHolder holder, int position) {
        ChatLog chatLog = chatLogList.get(position);
        if (holder instanceof FromChatItemHolder) {
            FromChatItemHolder fromChatItemHolder = (FromChatItemHolder) holder;
            fromChatItemHolder.chatContent.setText(chatLog.getChatContent());
            //Glide.with(holder.itemView).load(chatLog.)

        } else if (holder instanceof ToChatItemHolder) {
            ToChatItemHolder toChatItemHolder = (ToChatItemHolder) holder;
            toChatItemHolder.chatContent.setText(chatLog.getChatContent());
        }
    }

    @Override
    public int getItemCount() {
        return chatLogList == null? 0 : chatLogList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatLog chatLog = chatLogList.get(position);
        if (chatLog.getFrom() == null || chatLog.getFrom().isEmpty()) {
            return TYPE_SEND;
        }
        return TYPE_RECEIVE;
    }

    public void addItem(ChatLog chatLog) {
        chatLogList.add(chatLog);
        notifyDataSetChanged();
    }

    public class ChatItemHolder extends RecyclerView.ViewHolder {
        public ChatItemHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class FromChatItemHolder extends ChatItemHolder {

        ImageView mFromImage;
        TextView chatContent;

        public FromChatItemHolder(@NonNull View itemView) {
            super(itemView);
            mFromImage = itemView.findViewById(R.id.image_from);
            chatContent = itemView.findViewById(R.id.receive_chat_content);
        }
    }

    public class ToChatItemHolder extends ChatItemHolder {
        ImageView mFromImage;
        TextView chatContent;
        public ToChatItemHolder(@NonNull View itemView) {
            super(itemView);
            mFromImage = itemView.findViewById(R.id.image_user);
            chatContent = itemView.findViewById(R.id.send_chat_content);
        }
    }
}
