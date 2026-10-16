
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

public class StatusActivity extends Activity {

    private final int bg = Color.rgb(10, 17, 32);
    private final int accent = Color.rgb(0, 210, 170);
    private LinearLayout statusList;

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
        root.setPadding(22, 25, 22, 18);
        root.setBackgroundColor(bg);

        TextView title = makeText("Статусҳо", 28, Color.WHITE);
        title.setTypeface(null, Typeface.BOLD);
        root.addView(title);

        TextView subtitle = makeText(
                "Лаҳзаҳои худро мубодила кун",
                14, Color.LTGRAY);
        subtitle.setPadding(0, 8, 0, 20);
        root.addView(subtitle);

        EditText input = new EditText(this);
        input.setHint("Имрӯз чӣ хабар?");
        input.setTextColor(Color.WHITE);
        input.setHintTextColor(Color.GRAY);
        input.setPadding(16, 10, 16, 10);
        input.setBackground(rounded(
                Color.rgb(25, 36, 54), 15));
        root.addView(input,
                new LinearLayout.LayoutParams(-1, 100));

        Button publish = new Button(this);
        publish.setText("＋  ИНТИШОРИ СТАТУС");
        publish.setAllCaps(false);
        publish.setTextColor(bg);
        publish.setTypeface(null, Typeface.BOLD);
        publish.setBackground(rounded(accent, 15));

        LinearLayout.LayoutParams buttonParams =
                new LinearLayout.LayoutParams(-1, 55);
        buttonParams.topMargin = 12;
        buttonParams.bottomMargin = 20;
        root.addView(publish, buttonParams);

        TextView heading = makeText(
                "Статусҳои ман", 19, Color.WHITE);
        heading.setTypeface(null, Typeface.BOLD);
        root.addView(heading);

        ScrollView scroll = new ScrollView(this);
        statusList = new LinearLayout(this);
        statusList.setOrientation(LinearLayout.VERTICAL);
        scroll.addView(statusList);

        root.addView(scroll,
                new LinearLayout.LayoutParams(-1, 0, 1));

        publish.setOnClickListener(v -> {
            String value = input.getText().toString().trim();

            if (value.isEmpty()) {
                input.setError("Матни статусро навис");
                return;
            }

            try {
                JSONArray statuses = loadStatuses();
                statuses.put(value);

                getSharedPreferences(
                        "PINGO_STATUSES", MODE_PRIVATE)
                        .edit()
                        .putString("statuses", statuses.toString())
                        .apply();

                input.setText("");
                showStatuses();

                Toast.makeText(this,
                        "Статус сабт шуд",
                        Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(this,
                        "Хато ҳангоми сабт",
                        Toast.LENGTH_SHORT).show();
            }
        });

        showStatuses();
        setContentView(root);
    }

    private JSONArray loadStatuses() {
        try {
            return new JSONArray(
                    getSharedPreferences(
                            "PINGO_STATUSES", MODE_PRIVATE)
                            .getString("statuses", "[]"));
        } catch (Exception e) {
            return new JSONArray();
        }
    }

    private void showStatuses() {
        statusList.removeAllViews();
        JSONArray statuses = loadStatuses();

        if (statuses.length() == 0) {
            TextView empty = makeText(
                    "Ҳоло статус нест.",
                    14, Color.LTGRAY);
            empty.setPadding(0, 18, 0, 0);
            statusList.addView(empty);
            return;
        }

        for (int i = statuses.length() - 1; i >= 0; i--) {
            String value = statuses.optString(i, "");

            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(16, 14, 16, 14);
            card.setBackground(rounded(
                    Color.rgb(25, 36, 54), 15));

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(-1, -2);
            params.topMargin = 10;

            TextView label = makeText(
                    "●  Статуси ман", 13, accent);
            card.addView(label);

            TextView content = makeText(
                    value, 16, Color.WHITE);
            content.setPadding(0, 8, 0, 8);
            card.addView(content);

            Button delete = new Button(this);
            delete.setText("Нест кардан");
            delete.setAllCaps(false);
            card.addView(delete);

            final int index = i;
            delete.setOnClickListener(v -> {
                JSONArray old = loadStatuses();
                JSONArray updated = new JSONArray();

                for (int j = 0; j < old.length(); j++) {
                    if (j != index) {
                        updated.put(old.optString(j));
                    }
                }

                getSharedPreferences(
                        "PINGO_STATUSES", MODE_PRIVATE)
                        .edit()
                        .putString("statuses", updated.toString())
                        .apply();

                showStatuses();
            });

            statusList.addView(card, params);
        }
    }
                            }
        
