
package com.pingo.messenger;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class StatusManager {

    private final SharedPreferences prefs;
    private static final String KEY_STATUSES = "statuses";

    public StatusManager(Context context) {
        prefs = context.getSharedPreferences(
                "pingo_statuses",
                Context.MODE_PRIVATE
        );
    }

    // Ҳамаи статусҳоро гирифтан
    public ArrayList<JSONObject> getStatuses() {
        ArrayList<JSONObject> statuses = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(
                    prefs.getString(KEY_STATUSES, "[]")
            );

            for (int i = 0; i < array.length(); i++) {
                statuses.add(array.getJSONObject(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statuses;
    }

    // Илова кардани статуси нав
    public boolean addStatus(String userName, String text) {
        if (userName == null || userName.trim().isEmpty()
                || text == null || text.trim().isEmpty()) {
            return false;
        }

        try {
            JSONArray array = new JSONArray(
                    prefs.getString(KEY_STATUSES, "[]")
            );

            JSONObject status = new JSONObject();
            status.put("user", userName.trim());
            status.put("text", text.trim());
            status.put("time", System.currentTimeMillis());

            array.put(status);

            prefs.edit()
                    .putString(KEY_STATUSES, array.toString())
                    .apply();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Нест кардани статус аз рӯйи индекс
    public boolean removeStatus(int index) {
        try {
            JSONArray oldArray = new JSONArray(
                    prefs.getString(KEY_STATUSES, "[]")
            );

            if (index < 0 || index >= oldArray.length()) {
                return false;
            }

            JSONArray newArray = new JSONArray();

            for (int i = 0; i < oldArray.length(); i++) {
                if (i != index) {
                    newArray.put(oldArray.getJSONObject(i));
                }
            }

            prefs.edit()
                    .putString(KEY_STATUSES, newArray.toString())
                    .apply();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Нест кардани ҳамаи статусҳо
    public void clearStatuses() {
        prefs.edit()
                .remove(KEY_STATUSES)
                .apply();
    }
}
