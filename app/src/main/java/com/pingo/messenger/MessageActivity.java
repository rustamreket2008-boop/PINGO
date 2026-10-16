package com.pingo.messenger;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MessageActivity extends Activity {

    private LinearLayout messagesLayout;
    private EditText messageInput;
    private String chatName = "Дӯст";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null &&
                getIntent().hasExtra("chatName")) {
            chatName = getIntent().getStringExtra("chatName");
        }

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.rgb(12, 18, 30));

        LinearLayout header = new LinearLayout(this);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(16, 16, 16, 16);
        header.setBackgroundColor(Color.rgb(20, 30, 48));

        TextView back = new TextView(this);
        back.setText("‹");
        back.setTextSize(36);
        back.setTextColor(Color.WHITE);
        back.setPadding(0, 0, 20, 0);
        back.setOnClickListener(v -> finish());

        TextView name = new TextView(this);
        name.setText(chatName);
        name.setTextSize(20);
        name.setTypeface(null, Typeface.BOLD);
        name.setTextColor(Color.WHITE);

        header.addView(back);
        header.addView(name);
        root.addView(header);

        ScrollView scroll = new ScrollView(this);
        messagesLayout = new LinearLayout(this);
        messagesLayout.setOrientation(LinearLayout.VERTICAL);
        messagesLayout.setPadding(12, 16, 12, 16);

        TextView welcome = new TextView(this);
        welcome.setText("Ба чати " + chatName + " хуш омадӣ!");
        welcome.setTextColor(Color.LTGRAY);
        welcome.setGravity(Gravity.CENTER);
        welcome.setPadding(8, 20, 8, 20);
        messagesLayout.addView(welcome);

        scroll.addView(messagesLayout);
        root.addView(scroll, new LinearLayout.LayoutParams(
                -1, 0, 1));

        LinearLayout bottom = new LinearLayout(this);
        bottom.setGravity(Gravity.CENTER_VERTICAL);
        bottom.setPadding(10, 10, 10, 10);
        bottom.setBackgroundColor(Color.rgb(20, 30, 48));

        messageInput = new EditText(this);
        messageInput.setHint("Паём навис...");
        messageInput.setTextColor(Color.WHITE);
        messageInput.setHintTextColor(Color.LTGRAY);
        messageInput.setSingleLine(true);
        messageInput.setBackgroundColor(Color.rgb(38, 49, 68));
        messageInput.setPadding(14, 10, 14, 10);

        bottom.addView(messageInput, new LinearLayout.LayoutParams(
                0, -2, 1));

        Button send = new Button(this);
        send.setText("➤");
        send.setTextColor(Color.WHITE);
        send.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(
                        Color.rgb(0, 180, 140)));

        send.setOnClickListener(v -> sendMessage());

        bottom.addView(send);
        root.addView(bottom);

        setContentView(root);
    }

    private void sendMessage() {
        String text = messageInput.getText().toString().trim();

        if (text.isEmpty()) {
            Toast.makeText(this, "Паём навис!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        TextView bubble = new TextView(this);
        bubble.setText(text);
        bubble.setTextColor(Color.WHITE);
        bubble.setTextSize(16);
        bubble.setPadding(16, 12, 16, 12);
        bubble.setBackgroundColor(Color.rgb(0, 130, 105));

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        -2, -2);
        params.gravity = Gravity.END;
        params.setMargins(30, 6, 0, 6);

        messagesLayout.addView(bubble, params);
        messageInput.setText("");
    }
  }
