package com.pingo.messenger;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatsActivity extends Activity {

    private final int dark = Color.rgb(16, 61, 43);
    private final int green = Color.rgb(32, 201, 120);
    private final int background = Color.rgb(244, 248, 245);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(dark);
        getWindow().setNavigationBarColor(dark);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(20, 24, 20, 20);
        root.setBackgroundColor(background);

        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.VERTICAL);
        header.setPadding(20, 22, 20, 22);
        header.setBackground(createRounded(dark, 28));

        TextView title = new TextView(this);
        title.setText("PINGO");
        title.setTextSize(30);
        title.setTextColor(Color.WHITE);
        title.setTypeface(null, Typeface.BOLD);
        header.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("Чатҳо ва дӯстони шумо");
        subtitle.setTextSize(15);
        subtitle.setTextColor(Color.rgb(190, 240, 211));
        subtitle.setPadding(0, 6, 0, 0);
        header.addView(subtitle);

        root.addView(header);

        TextView section = new TextView(this);
        section.setText("Сӯҳбатҳо");
        section.setTextSize(25);
        section.setTextColor(dark);
        section.setTypeface(null, Typeface.BOLD);
        section.setPadding(0, 28, 0, 8);
        root.addView(section);

        TextView info = new TextView(this);
        info.setText(
                "Барои оғози сӯҳбат ба саҳифаи асосии PINGO баргардед ва дӯст илова кунед."
        );
        info.setTextSize(16);
        info.setTextColor(Color.DKGRAY);
        info.setPadding(0, 4, 0, 20);
        root.addView(info);

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setGravity(Gravity.CENTER);
        card.setPadding(20, 28, 20, 28);
        card.setBackground(createRounded(Color.WHITE, 24));

        TextView icon = new TextView(this);
        icon.setText("💬");
        icon.setTextSize(46);
        icon.setGravity(Gravity.CENTER);
        card.addView(icon);

        TextView emptyTitle = new TextView(this);
        emptyTitle.setText("Сӯҳбатҳо ҳоло холӣ ҳастанд");
        emptyTitle.setTextSize(18);
        emptyTitle.setTextColor(dark);
        emptyTitle.setTypeface(null, Typeface.BOLD);
        emptyTitle.setGravity(Gravity.CENTER);
        emptyTitle.setPadding(0, 12, 0, 8);
        card.addView(emptyTitle);

        TextView emptyDescription = new TextView(this);
        emptyDescription.setText(
                "Вақте сӯҳбатҳо пайдо шаванд, онҳо дар ин ҷо намоиш дода мешаванд."
        );
        emptyDescription.setTextSize(14);
        emptyDescription.setTextColor(Color.GRAY);
        emptyDescription.setGravity(Gravity.CENTER);
        card.addView(emptyDescription);

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        cardParams.setMargins(0, 8, 0, 0);
        root.addView(card, cardParams);

        TextView footer = new TextView(this);
        footer.setText("PINGO • Connect • Chat • Share");
        footer.setTextSize(12);
        footer.setTextColor(green);
        footer.setGravity(Gravity.CENTER);
        footer.setPadding(0, 28, 0, 0);
        root.addView(footer);

        setContentView(root);
    }

    private GradientDrawable createRounded(int color, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(radius);
        return drawable;
    }
}
