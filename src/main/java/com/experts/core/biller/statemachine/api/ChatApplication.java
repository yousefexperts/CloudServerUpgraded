package com.experts.core.biller.statemachine.api;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IMap;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryRemovedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;

import java.io.IOException;
import java.io.Serializable;


public class ChatApplication {

    private String username;
    private final IMap<String, ChatMessage> map = Hazelcast.newHazelcastInstance(null).getMap("chat-application");


    public static void main(String[] args) {
        ChatApplication application = new ChatApplication();
        String username = (args != null && args.length > 0) ? args[0] : null;
        if (username == null) {
            System.out.println("Enter username: ");
            int input;
            StringBuilder u = new StringBuilder();
            try {
                while ((input = System.in.read()) != '\n') {
                    u.append((char) input);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            username = u.toString();
        }
        System.out.println("Hello " + username);
        application.setUsername(username);
        application.run();
    }
    private void setUsername(String name) {
        this.username = name;
        new ChatMessage(username, "has joined").send(map);
    }

    private void run() {
        showConnected(map);
        map.addEntryListener(new ChatCallback(), true);
        while (true) {
            int input;
            StringBuilder message = new StringBuilder();
            ChatMessage chat;
            try {
                while ((input = System.in.read()) != '\n') {
                    message.append((char) input);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            chat = new ChatMessage(username, message.toString());
            chat.send(map);
        }
    }
    private void showConnected(IMap<String, ChatMessage> map) {
        for (String user : map.keySet()) {
            System.out.println(user + " is online");
        }
    }

    private static class ChatMessage implements Serializable {
        private String username;
        private String message;
        ChatMessage(String username, String message) {
            this.username = username;
            this.message = message;
        }
        void send(IMap<String, ChatMessage> map) {
            map.put(username, this);
        }
        @Override
        public String toString() {
            return username + ": " + message;
        }
    }
    private class ChatCallback implements EntryAddedListener<String, ChatMessage>, EntryRemovedListener<String, ChatMessage>,
            EntryUpdatedListener<String, ChatMessage> {

        ChatCallback() {
        }
        public void entryAdded(EntryEvent<String, ChatMessage> event) {
            if (!username.equals(event.getKey())) {
                System.out.println(event.getValue());
            }
        }
        public void entryRemoved(EntryEvent<String, ChatMessage> event) {
            if (!username.equals(event.getKey())) {
                System.out.println(event.getKey() + " left");
            }
        }
        public void entryUpdated(EntryEvent<String, ChatMessage> event) {
            if (!username.equals(event.getKey())) {
                System.out.println(event.getValue().toString());
            }
        }
    }
}
