package com.pingo.messenger;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CallManager {

    private final SharedPreferences prefs;
    private static final String KEY_CALLS = "call_history";

    public CallManager(Context context) {
        prefs = context.getSharedPreferences(
                "pingo_calls",
                Context.MODE_PRIVATE
        );
    }

    // Сабти занг дар таърих
    public void addCall(String name, String type, String result) {
        if (name == null || name.trim().isEmpty()) {
            return;
        }

        try {
            JSONArray calls = new JSONArray(
                    prefs.getString(KEY_CALLS, "[]")
            );

            JSONObject call = new JSONObject();
            call.put("name", name.trim());
            call.put("type", type == null ? "voice" : type);
            call.put("result", result == null ? "unknown" : result);
            call.put("time", System.currentTimeMillis());

            calls.put(call);

            prefs.edit()
                    .putString(KEY_CALLS, calls.toString())
                    .apply();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Гирифтани таърихи зангҳо
    public ArrayList<JSONObject> getCalls() {
        ArrayList<JSONObject> result = new ArrayList<>();

        try {
            JSONArray calls = new JSONArray(
                    prefs.getString(KEY_CALLS, "[]")
            );

            for (int i = calls.length() - 1; i >= 0; i--) {
                result.add(calls.getJSONObject(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // Пок кардани таърихи зангҳо
    public void clearCalls() {
        prefs.edit()
                .remove(KEY_CALLS)
                .apply();
    }
}
