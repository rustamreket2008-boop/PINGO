
package com.pingo.messenger;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ContactsActivity extends Activity {

    private final int bg = Color.rgb(10, 17, 32);
    private final int accent = Color.rgb(0, 210, 170);
    private LinearLayout list;

    private GradientDrawable rounded(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(radius);
        return d;
    }

    private TextView makeText(String value, int size, int color) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        return t;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(bg);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(22, 25, 22, 20);
        root.setBackgroundColor(bg);

        TextView title = makeText(
                "Дӯстони PINGO", 27, Color.WHITE);
        title.setTypeface(null, Typeface.BOLD);
        root.addView(title);

        TextView subtitle = makeText(
                "Рақам ё номи дӯстатро ворид кун",
                14, Color.LTGRAY);
        subtitle.setPadding(0, 8, 0, 20);
        root.addView(subtitle);

        EditText search = new EditText(this);
        search.setSingleLine(true);
        search.setHint("Ном ё рақами телефон");
        search.setTextColor(Color.WHITE);
        search.setHintTextColor(Color.GRAY);
        search.setPadding(16, 8, 16, 8);
        search.setBackground(rounded(
                Color.rgb(25, 36, 54), 15));
        root.addView(search,
                new LinearLayout.LayoutParams(-1, 58));

        Button add = new Button(this);
        add.setText("＋  ИЛОВА КАРДАН");
        add.setAllCaps(false);
        add.setTextColor(bg);
        add.setTypeface(null, Typeface.BOLD);
        add.setBackground(rounded(accent, 15));

        LinearLayout.LayoutParams addParams =
                new LinearLayout.LayoutParams(-1, 55);
        addParams.topMargin = 14;
        addParams.bottomMargin = 22;
        root.addView(add, addParams);

        TextView heading = makeText(
                "Рӯйхати дӯстҳо", 19, Color.WHITE);
        heading.setTypeface(null, Typeface.BOLD);
        root.addView(heading);

        list = new LinearLayout(this);
        list.setOrientation(LinearLayout.VERTICAL);

        root.addView(list,
                new LinearLayout.LayoutParams(-1, -2));

        add.setOnClickListener(v -> {
            String value = search.getText().toString().trim();

            if (value.isEmpty()) {
                search.setError("Ном ё рақамро навис");
                return;
            }

            try {
                JSONArray friends = new JSONArray(
                        getSharedPreferences(
                                "PINGO_CONTACTS", MODE_PRIVATE)
                                .getString("friends", "[]"));

                boolean exists = false;

                for (int i = 0; i < friends.length(); i++) {
                    JSONObject item = friends.getJSONObject(i);
                    if (item.getString("value")
                            .equalsIgnoreCase(value)) {
                        exists = true;
                        break;
                    }
                }

                if (exists) {
                    Toast.makeText(this,
                            "Ин дӯст аллакай илова шудааст",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject friend = new JSONObject();
                friend.put("value", value);
                friends.put(friend);

                getSharedPreferences(
                        "PINGO_CONTACTS", MODE_PRIVATE)
                        .edit()
                        .putString("friends", friends.toString())
                        .apply();

                search.setText("");
                showFriends();

                Toast.makeText(this,
                        "Ба рӯйхати дӯстҳо илова шуд",
                        Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(this,
                        "Хато ҳангоми сабт",
                        Toast.LENGTH_SHORT).show();
            }
        });

        showFriends();
        setContentView(root);
    }

    private void showFriends() {
        list.removeAllViews();

        try {
            JSONArray friends = new JSONArray(
                    getSharedPreferences(
                            "PINGO_CONTACTS", MODE_PRIVATE)
                            .getString("friends", "[]"));

            if (friends.length() == 0) {
                TextView empty = makeText(
                        "Ҳоло дӯст илова нашудааст.",
                        14, Color.LTGRAY);
                empty.setPadding(0, 18, 0, 0);
                list.addView(empty);
                return;
            }

            for (int i = 0; i < friends.length(); i++) {
                String value = friends.getJSONObject(i)
                        .getString("value");

                LinearLayout row = new LinearLayout(this);
                row.setGravity(Gravity.CENTER_VERTICAL);
                row.setPadding(14, 12, 14, 12);
                row.setBackground(rounded(
                        Color.rgb(25, 36, 54), 15));

                LinearLayout.LayoutParams rowParams =
                        new LinearLayout.LayoutParams(-1, -2);
                rowParams.topMargin = 10;

                TextView avatar = makeText(
                        "👤", 25, accent);
                row.addView(avatar);

                TextView name = makeText(
                        value, 16, Color.WHITE);
                name.setPadding(15, 0, 0, 0);

                row.addView(name,
                        new LinearLayout.LayoutParams(0, -2, 1));

                Button remove = new Button(this);
                remove.setText("✕");
                remove.setAllCaps(false);
                remove.setTextColor(Color.WHITE);
                row.addView(remove);

                remove.setOnClickListener(v -> {
                    try {
                        JSONArray old = new JSONArray(
                                getSharedPreferences(
                                        "PINGO_CONTACTS",
                                        MODE_PRIVATE)
                                        .getString("friends", "[]"));

                        JSONArray updated = new JSONArray();

                        for (int j = 0; j < old.length(); j++) {
                            JSONObject item = old.getJSONObject(j);
                            if (!item.getString("value")
                                    .equals(value)) {
                                updated.put(item);
                            }
                        }

                        getSharedPreferences(
                                "PINGO_CONTACTS", MODE_PRIVATE)
                                .edit()
                                .putString("friends",
                                        updated.toString())
                                .apply();

                        showFriends();
                    } catch (Exception ignored) {
                    }
                });

                list.addView(row, rowParams);
            }
        } catch (Exception ignored) {
        }
    }
                  }
                 
