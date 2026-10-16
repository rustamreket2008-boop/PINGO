
package com.pingo.messenger;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    private final int green = Color.rgb(32, 201, 120);
    private final int dark = Color.rgb(16, 61, 43);
    private final int background = Color.rgb(244, 248, 245);
    private LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(dark);
        getWindow().setNavigationBarColor(dark);
        showChats();
    }

    private GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(radius);
        return d;
    }

    private TextView text(String value, int size, int color,
                          boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        if (bold) t.setTypeface(null, Typeface.BOLD);
        return t;
    }

    private void showChats() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(background);

        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.VERTICAL);
        header.setPadding(22, 18, 22, 18);
        header.setBackgroundColor(dark);

        TextView logo = text("PINGO", 29, Color.WHITE, true);
        header.addView(logo);

        TextView subtitle = text(
            "Connect • Chat • Share", 13,
            Color.rgb(190, 240, 211), false);
        header.addView(subtitle);

        LinearLayout tabs = new LinearLayout(this);
        tabs.setGravity(Gravity.CENTER);
        tabs.setPadding(8, 14, 8, 14);
        tabs.setBackgroundColor(Color.WHITE);

        String[] names = {"Чатҳо", "Status", "Зангҳо", "Профил"};
        for (String name : names) {
            TextView tab = text(name, 13, dark, true);
            tab.setGravity(Gravity.CENTER);
            tab.setPadding(9, 10, 9, 10);
            tabs.addView(tab, new LinearLayout.LayoutParams(
                0, -2, 1));
        }

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(20, 20, 20, 10);

        root.addView(header);
        root.addView(tabs);
        root.addView(content, new LinearLayout.LayoutParams(
            -1, 0, 1));

        TextView bottom = text(
            "  ◉ Чатҳо       ◉ Нав       ◉ Танзимот  ",
            15, dark, true);
        bottom.setGravity(Gravity.CENTER);
        bottom.setPadding(8, 18, 8, 18);
        bottom.setBackgroundColor(Color.WHITE);
        root.addView(bottom);

        setContentView(root);
        addWelcome();
    }

    private void addWelcome() {
        TextView title = text(
            "Салом! 👋", 25, dark, true);
        content.addView(title);

        TextView desc = text(
            "Ба PINGO хуш омадед", 15,
            Color.DKGRAY, false);
        desc.setPadding(0, 6, 0, 20);
        content.addView(desc);

        addCard("💬", "Паёмҳо",
            "Сӯҳбатҳои худро дар ин ҷо оғоз кунед");
        addCard("🟢", "Status",
            "Лаҳзаҳои худро бо дӯстҳо мубодила кунед");
        addCard("📞", "Зангҳо",
            "Таърихи зангҳои шумо");
        addCard("👥", "Гурӯҳҳо",
            "Бо дӯстони худ дар гурӯҳ сӯҳбат кунед");

        TextView note = text(
            "PINGO • Паёмрасони замонавӣ",
            12, Color.GRAY, false);
        note.setGravity(Gravity.CENTER);
        note.setPadding(0, 20, 0, 0);
        content.addView(note);
    }

    private void addCard(String icon, String title,
                         String description) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(16, 16, 12, 16);
        card.setBackground(shape(Color.WHITE, 22));

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, 0, 0, 12);
        card.setElevation(2);
        content.addView(card, params);

        TextView emoji = text(icon, 27, dark, false);
        emoji.setGravity(Gravity.CENTER);
        card.addView(emoji);

        LinearLayout labels = new LinearLayout(this);
        labels.setOrientation(LinearLayout.VERTICAL);
        labels.setPadding(14, 0, 0, 0);

        labels.addView(text(title, 17, dark, true));
        TextView sub = text(
            description, 12, Color.DKGRAY, false);
        sub.setPadding(0, 4, 0, 0);
        labels.addView(sub);

        card.addView(labels);

        card.setOnClickListener(v -> {
            if (title.equals("Зангҳо")) {
                showMessage("Зангҳо",
                    "Функсияи зангро баъд пайваст мекунем.");
            } else if (title.equals("Status")) {
                showMessage("Status",
                    "Функсияи Status-ро баъд пайваст мекунем.");
            } else if (title.equals("Гурӯҳҳо")) {
                showMessage("Гурӯҳҳо",
                    "Функсияи гурӯҳҳоро баъд пайваст мекунем.");
            } else {
                showMessage("Паёмҳо",
                    "Барои чатҳо серверро пайваст мекунем.");
            }
        });
    }

    private void showMessage(String title, String message) {
        new android.app.AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Хуб", null)
            .show();
    }
  }
          
