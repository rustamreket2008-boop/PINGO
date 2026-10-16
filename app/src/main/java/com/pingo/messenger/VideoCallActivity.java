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

public class VideoCallActivity extends Activity {

    private boolean videoCallActive = false;
    private TextView statusText;
    private Button videoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(24, 24, 24, 24);
        root.setBackgroundColor(Color.rgb(12, 18, 32));

        TextView title = new TextView(this);
        title.setText("PINGO VIDEO");
        title.setTextSize(28);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);

        TextView camera = new TextView(this);
        camera.setText("▣");
        camera.setTextSize(90);
        camera.setTextColor(Color.rgb(0, 210, 160));
        camera.setGravity(Gravity.CENTER);
        camera.setPadding(0, 45, 0, 25);

        TextView contact = new TextView(this);
        String name = getIntent().getStringExtra("contactName");
        if (name == null || name.trim().isEmpty()) {
            name = "Дӯст";
        }
        contact.setText(name);
        contact.setTextSize(24);
        contact.setTypeface(null, Typeface.BOLD);
        contact.setTextColor(Color.WHITE);
        contact.setGravity(Gravity.CENTER);

        statusText = new TextView(this);
        statusText.setText("Видео-занг омода нест");
        statusText.setTextSize(16);
        statusText.setTextColor(Color.LTGRAY);
        statusText.setGravity(Gravity.CENTER);
        statusText.setPadding(0, 15, 0, 30);

        videoButton = new Button(this);
        videoButton.setText("▣ Оғози видео-занг");
        videoButton.setTextColor(Color.WHITE);
        videoButton.setBackgroundTintList(
            android.content.res.ColorStateList.valueOf(
                Color.rgb(0, 160, 120)
            )
        );

        videoButton.setOnClickListener(v -> {
            if (!videoCallActive) {
                videoCallActive = true;
                statusText.setText(
                    "Барои видео-занги воқеӣ пайвастшавӣ лозим аст"
                );
                videoButton.setText("✕ Бастан");

                Toast.makeText(
                    this,
                    "Firebase ва WebRTC ҳанӯз пайваст нестанд",
                    Toast.LENGTH_LONG
                ).show();
            } else {
                finish();
            }
        });

        Button backButton = new Button(this);
        backButton.setText("Бозгашт");
        backButton.setOnClickListener(v -> finish());

        root.addView(title);
        root.addView(camera);
        root.addView(contact);
        root.addView(statusText);
        root.addView(videoButton);
        root.addView(backButton);

        setContentView(root);
    }
          }
          
