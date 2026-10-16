
package com.pingo.messenger;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;

import java.util.ArrayList;

public class ChatStorage {

    private final SharedPreferences prefs;

    public ChatStorage(Context context) {
        prefs = context.getSharedPreferences(
                "pingo_chats",
                Context.MODE_PRIVATE
        );
    }

    public ArrayList<String> getMessages(String chatName) {
        ArrayList<String> messages = new ArrayList<>();

        try {
            String saved = prefs.getString(chatName, "[]");
            JSONArray data = new JSONArray(saved);

            for (int i = 0; i < data.length(); i++) {
                messages.add(data.getString(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }

    public void addMessage(String chatName, String message) {
        JSONArray data;

        try {
            String saved = prefs.getString(chatName, "[]");
            data = new JSONArray(saved);

        } catch (Exception e) {
            data = new JSONArray();
        }

        data.put(message);

        prefs.edit()
                .putString(chatName, data.toString())
                .apply();
    }

    public void clearMessages(String chatName) {
        prefs.edit()
                .remove(chatName)
                .apply();
    }
}
