
package com.pingo.messenger;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class ProfileActivity extends Activity {

    private final int background = Color.rgb(10, 17, 32);
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

        getWindow().setStatusBarColor(background);
        getWindow().setNavigationBarColor(background);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(24, 25, 24, 24);
        root.setBackgroundColor(background);

        TextView heading = makeText("Профили ман", 28, Color.WHITE);
        heading.setTypeface(null, Typeface.BOLD);
        root.addView(heading);

        TextView avatar = makeText("👤", 48, accent);
        avatar.setGravity(Gravity.CENTER);
        avatar.setBackground(rounded(
                Color.rgb(25, 45, 60), 100
        ));

        LinearLayout.LayoutParams avatarParams =
                new LinearLayout.LayoutParams(120, 120);
        avatarParams.gravity = Gravity.CENTER;
        avatarParams.topMargin = 30;
        avatarParams.bottomMargin = 20;
        root.addView(avatar, avatarParams);

        TextView nameLabel = makeText("Номи шумо", 15, Color.LTGRAY);
        nameLabel.setPadding(0, 12, 0, 6);
        root.addView(nameLabel);

        EditText name = new EditText(this);
        name.setSingleLine(true);
        name.setHint("Номи худро нависед");
        name.setTextColor(Color.WHITE);
        name.setHintTextColor(Color.GRAY);
        name.setPadding(16, 8, 16, 8);
        name.setBackground(rounded(
                Color.rgb(25, 36, 54), 14
        ));
        root.addView(name,
                new LinearLayout.LayoutParams(-1, 58));

        TextView phoneLabel =
                makeText("Рақами телефон", 15, Color.LTGRAY);
        phoneLabel.setPadding(0, 20, 0, 6);
        root.addView(phoneLabel);

        EditText phone = new EditText(this);
        phone.setSingleLine(true);
        phone.setHint("+992 ...");
        phone.setInputType(3);
        phone.setTextColor(Color.WHITE);
        phone.setHintTextColor(Color.GRAY);
        phone.setPadding(16, 8, 16, 8);
        phone.setBackground(rounded(
                Color.rgb(25, 36, 54), 14
        ));
        root.addView(phone,
                new LinearLayout.LayoutParams(-1, 58));

        Button save = new Button(this);
        save.setText("САБТ КАРДАН");
        save.setAllCaps(false);
        save.setTextColor(background);
        save.setTypeface(null, Typeface.BOLD);
        save.setBackground(rounded(accent, 16));

        LinearLayout.LayoutParams saveParams =
                new LinearLayout.LayoutParams(-1, 58);
        saveParams.topMargin = 25;
        root.addView(save, saveParams);

        save.setOnClickListener(v -> {
            String username = name.getText().toString().trim();
            String number = phone.getText().toString().trim();

            if (username.isEmpty()) {
                name.setError("Номро нависед");
                return;
            }

            getSharedPreferences("PINGO_PROFILE", MODE_PRIVATE)
                    .edit()
                    .putString("name", username)
                    .putString("phone", number)
                    .apply();

            Toast.makeText(
                    this, "Профил сабт шуд!",
                    Toast.LENGTH_SHORT
            ).show();
        });

        String savedName = getSharedPreferences(
                "PINGO_PROFILE", MODE_PRIVATE
        ).getString("name", "");

        String savedPhone = getSharedPreferences(
                "PINGO_PROFILE", MODE_PRIVATE
        ).getString("phone", "");

        name.setText(savedName);
        phone.setText(savedPhone);

        setContentView(root);
    }
    }
