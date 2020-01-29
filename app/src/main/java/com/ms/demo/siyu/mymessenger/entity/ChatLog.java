package com.ms.demo.siyu.mymessenger.entity;

public class ChatLog {
    private String from;
    private String to;
    private String chatContent;


    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getChatContent() {
        return chatContent;
    }

    public ChatLog() {

    }
    public ChatLog(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.chatContent = content;
    }
}
