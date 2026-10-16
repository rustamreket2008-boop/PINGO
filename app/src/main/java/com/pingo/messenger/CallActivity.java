package com.pingo.messenger;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class CallActivity extends Activity {

    private boolean callActive = false;
    private TextView statusText;
    private Button callButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(28, 28, 28, 28);
        root.setBackgroundColor(Color.rgb(13, 20, 34));

        TextView title = new TextView(this);
        title.setText("PINGO CALL");
        title.setTextSize(28);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);

        TextView avatar = new TextView(this);
        avatar.setText("☎");
        avatar.setTextSize(64);
        avatar.setTextColor(Color.rgb(0, 210, 160));
        avatar.setGravity(Gravity.CENTER);
        avatar.setPadding(0, 50, 0, 25);

        TextView contact = new TextView(this);
        String name = getIntent().getStringExtra("contactName");
        if (name == null || name.trim().isEmpty()) {
            name = "Дӯст";
        }
        contact.setText(name);
        contact.setTextSize(25);
        contact.setTypeface(null, Typeface.BOLD);
        contact.setTextColor(Color.WHITE);
        contact.setGravity(Gravity.CENTER);

        statusText = new TextView(this);
        statusText.setText("Омода ба занг");
        statusText.setTextSize(16);
        statusText.setTextColor(Color.LTGRAY);
        statusText.setGravity(Gravity.CENTER);
        statusText.setPadding(0, 12, 0, 35);

        callButton = new Button(this);
        callButton.setText("☎  Зангро оғоз кун");
        callButton.setTextColor(Color.WHITE);
        callButton.setBackgroundTintList(
            android.content.res.ColorStateList.valueOf(
                Color.rgb(0, 170, 125)
            )
        );

        callButton.setOnClickListener(v -> {
            if (!callActive) {
                callActive = true;
                statusText.setText("Занг фаъол нест — сервер лозим аст");
                callButton.setText("✕  Бастан");
                Toast.makeText(this,
                    "Барои занги воқеӣ Firebase ва WebRTC лозиманд",
                    Toast.LENGTH_LONG).show();
            } else {
                finish();
            }
        });

        Button backButton = new Button(this);
        backButton.setText("Бозгашт");
        backButton.setOnClickListener(v -> finish());

        root.addView(title);
        root.addView(avatar);
        root.addView(contact);
        root.addView(statusText);
        root.addView(callButton);
        root.addView(backButton);

        setContentView(root);
    }
                        }
          
