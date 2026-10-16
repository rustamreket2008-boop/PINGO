package com.pingo.messenger;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends Activity {

    private final int bg = Color.rgb(10, 17, 32);
    private final int accent = Color.rgb(0, 210, 170);

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
        root.setPadding(22, 28, 22, 20);
        root.setBackgroundColor(bg);

        TextView title = makeText(
                "Танзимот", 29, Color.WHITE);
        title.setTypeface(null, Typeface.BOLD);
        root.addView(title);

        TextView subtitle = makeText(
                "PINGO • Идоракунии барнома",
                14, Color.LTGRAY);
        subtitle.setPadding(0, 8, 0, 25);
        root.addView(subtitle);

        addSwitch(root, "Огоҳиномаҳо",
                "notifications", true);

        addSwitch(root, "Овози паёмҳо",
                "message_sound", true);

        addSwitch(root, "Ларзиши телефон",
                "vibration", true);

        addSwitch(root, "Намоиши ҳолати онлайн",
                "show_online", true);

        TextView privacy = makeText(
                "МАХФИЯТ ВА АМНИЯТ", 13, accent);
        privacy.setPadding(0, 28, 0, 12);
        root.addView(privacy);

        addSwitch(root, "Пешнамоиши паёмҳо",
                "message_preview", true);

        TextView about = makeText(
                "Дар бораи PINGO", 17, Color.WHITE);
        about.setPadding(0, 25, 0, 12);
        root.addView(about);

        TextView version = makeText(
                "Нусхаи барнома: 1.0\n"
                + "Паёмрасони PINGO",
                14, Color.LTGRAY);
        root.addView(version);

        setContentView(root);
    }

    private void addSwitch(LinearLayout root,
                           String label,
                           String key,
                           boolean defaultValue) {

        LinearLayout row = new LinearLayout(this);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(15, 12, 12, 12);
        row.setBackground(rounded(
                Color.rgb(25, 36, 54), 15));

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(-1, -2);
        params.bottomMargin = 10;

        TextView text = makeText(
                label, 15, Color.WHITE);

        row.addView(text,
                new LinearLayout.LayoutParams(0, -2, 1));

        Switch toggle = new Switch(this);

        boolean saved = getSharedPreferences(
                "PINGO_SETTINGS", MODE_PRIVATE)
                .getBoolean(key, defaultValue);

        toggle.setChecked(saved);
        toggle.setOnCheckedChangeListener((button, checked) -> {
            getSharedPreferences(
                    "PINGO_SETTINGS", MODE_PRIVATE)
                    .edit()
                    .putBoolean(key, checked)
                    .apply();

            Toast.makeText(this,
                    label + (checked
                            ? " фаъол шуд"
                            : " хомӯш шуд"),
                    Toast.LENGTH_SHORT).show();
        });

        row.addView(toggle);
        root.addView(row, params);
    }
          }
                                          
