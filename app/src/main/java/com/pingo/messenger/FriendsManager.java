package com.pingo.messenger;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;

import java.util.ArrayList;

public class FriendsManager {

    private final SharedPreferences prefs;
    private static final String KEY_FRIENDS = "friends_list";

    public FriendsManager(Context context) {
        prefs = context.getSharedPreferences(
                "pingo_friends",
                Context.MODE_PRIVATE
        );
    }

    // Гирифтани рӯйхати ҳамаи дӯстҳо
    public ArrayList<String> getFriends() {
        ArrayList<String> friends = new ArrayList<>();

        try {
            String saved = prefs.getString(KEY_FRIENDS, "[]");
            JSONArray array = new JSONArray(saved);

            for (int i = 0; i < array.length(); i++) {
                friends.add(array.getString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return friends;
    }

    // Илова кардани дӯст
    public boolean addFriend(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        String cleanName = name.trim();

        ArrayList<String> friends = getFriends();

        for (String friend : friends) {
            if (friend.equalsIgnoreCase(cleanName)) {
                return false;
            }
        }

        friends.add(cleanName);
        saveFriends(friends);
        return true;
    }

    // Ҷустуҷӯи дӯст
    public ArrayList<String> searchFriends(String query) {
        ArrayList<String> results = new ArrayList<>();

        if (query == null || query.trim().isEmpty()) {
            return getFriends();
        }

        String search = query.trim().toLowerCase();

        for (String friend : getFriends()) {
            if (friend.toLowerCase().contains(search)) {
                results.add(friend);
            }
        }

        return results;
    }

    // Санҷидани мавҷудияти дӯст
    public boolean isFriend(String name) {
        if (name == null) {
            return false;
        }

        for (String friend : getFriends()) {
            if (friend.equalsIgnoreCase(name.trim())) {
                return true;
            }
        }

        return false;
    }

    // Нест кардани дӯст
    public boolean removeFriend(String name) {
        if (name == null) {
            return false;
        }

        ArrayList<String> friends = getFriends();
        boolean removed = false;

        for (int i = friends.size() - 1; i >= 0; i--) {
            if (friends.get(i).equalsIgnoreCase(name.trim())) {
                friends.remove(i);
                removed = true;
            }
        }

        if (removed) {
            saveFriends(friends);
        }

        return removed;
    }

    // Нигоҳ доштани рӯйхати дӯстҳо
    private void saveFriends(ArrayList<String> friends) {
        JSONArray array = new JSONArray();

        for (String friend : friends) {
            array.put(friend);
        }

        prefs.edit()
                .putString(KEY_FRIENDS, array.toString())
                .apply();
    }

    // Нест кардани ҳамаи дӯстҳо
    public void clearFriends() {
        prefs.edit()
                .remove(KEY_FRIENDS)
                .apply();
    }
}
