package com.pingo.messenger;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(24, 32, 24, 24);
        layout.setBackgroundColor(Color.rgb(245, 248, 247));

        TextView title = new TextView(this);
        title.setText("💬  Чатҳо");
        title.setTextSize(28);
        title.setTextColor(Color.rgb(20, 120, 90));
        title.setGravity(Gravity.CENTER_VERTICAL);
        title.setPadding(0, 0, 0, 24);
        layout.addView(title);

        TextView info = new TextView(this);
        info.setText("Суҳбатҳои шумо дар ин ҷо нишон дода мешаванд.");
        info.setTextSize(17);
        info.setTextColor(Color.DKGRAY);
        layout.addView(info);

        setContentView(layout);
    }
}
