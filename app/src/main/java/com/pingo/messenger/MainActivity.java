
package com.pingo.messenger;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private final int green = Color.rgb(32, 201, 120);
    private final int dark = Color.rgb(16, 61, 43);
    private final int bg = Color.rgb(244, 248, 245);

    private LinearLayout content;
    private final ArrayList<String> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(dark);
        getWindow().setNavigationBarColor(dark);
        showPage("Чатҳо");
    }

    private TextView label(String s, int size, int color,
                           boolean bold) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(size);
        t.setTextColor(color);
        if (bold) t.setTypeface(null, Typeface.BOLD);
        return t;
    }

    private GradientDrawable rounded(int color) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(24);
        return d;
    }

    private LinearLayout column() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        return l;
    }

    private void showPage(String page) {
        LinearLayout root = column();
        root.setBackgroundColor(bg);

        LinearLayout header = column();
        header.setPadding(22, 18, 22, 18);
        header.setBackgroundColor(dark);
        header.addView(label("PINGO", 29, Color.WHITE, true));
        header.addView(label("Connect • Chat • Share",
                13, Color.rgb(190, 240, 211), false));
        root.addView(header);

        LinearLayout tabs = new LinearLayout(this);
        tabs.setBackgroundColor(Color.WHITE);

        String[] names = {"Чатҳо", "Status", "Зангҳо", "Профил"};
        for (String name : names) {
            TextView tab = label(name, 12,
                    name.equals(page) ? green : dark, true);
            tab.setGravity(Gravity.CENTER);
            tab.setPadding(2, 16, 2, 16);
            tabs.addView(tab, new LinearLayout.LayoutParams(
                    0, -2, 1));
            tab.setOnClickListener(v -> showPage(name));
        }
        root.addView(tabs);

        ScrollView scroll = new ScrollView(this);
        content = column();
        content.setPadding(18, 18, 18, 18);
        scroll.addView(content);
        root.addView(scroll, new LinearLayout.LayoutParams(
                -1, 0, 1));

        TextView footer = label("PINGO • Паёмрасони замонавӣ",
                12, Color.GRAY, false);
        footer.setGravity(Gravity.CENTER);
        footer.setPadding(8, 14, 8, 14);
        footer.setBackgroundColor(Color.WHITE);
        root.addView(footer);

        setContentView(root);

        if (page.equals("Чатҳо")) showChats();
        if (page.equals("Status")) showStatuses();
        if (page.equals("Зангҳо")) showCalls();
        if (page.equals("Профил")) showProfile();
    }

    private void showChats() {
        content.addView(label("Сӯҳбатҳо", 25, dark, true));
        content.addView(label("Бо дӯстони худ сӯҳбат кунед",
                14, Color.DKGRAY, false));

        addRow("👤", "Дӯст", "Салом! Чӣ хабар?");
        addRow("👨‍👩‍👧", "Оила", "Паёмҳои нав");
        addRow("👥", "Дӯстҳо", "Гурӯҳи дӯстҳо");

        Button newChat = new Button(this);
        newChat.setText("+ Чати нав");
        content.addView(newChat);

        newChat.setOnClickListener(v -> {
            EditText input = new EditText(this);
            input.setHint("Номи шахс ё гурӯҳ");

            new AlertDialog.Builder(this)
                .setTitle("Чати нав")
                .setView(input)
                .setNegativeButton("Бекор", null)
                .setPositiveButton("Кушодан", (d, w) -> {
                    String name = input.getText().toString().trim();
                    if (!name.isEmpty()) openChat(name);
                }).show();
        });
    }

    private void addRow(String emoji, String name, String preview) {
        LinearLayout card = new LinearLayout(this);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(14, 16, 14, 16);
        card.setBackground(rounded(Color.WHITE));

        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(-1, -2);
        lp.setMargins(0, 0, 0, 12);
        content.addView(card, lp);

        card.addView(label(emoji, 27, dark, false));

        LinearLayout info = column();
        info.setPadding(14, 0, 0, 0);
        info.addView(label(name, 17, dark, true));
        info.addView(label(preview, 13, Color.DKGRAY, false));

        card.addView(info, new LinearLayout.LayoutParams(
                0, -2, 1));

        card.setOnClickListener(v -> openChat(name));
    }

    private void openChat(String name) {
        LinearLayout root = column();
        root.setBackgroundColor(bg);

        LinearLayout top = new LinearLayout(this);
        top.setGravity(Gravity.CENTER_VERTICAL);
        top.setPadding(14, 15, 14, 15);
        top.setBackgroundColor(dark);

        TextView back = label("‹", 34, Color.WHITE, true);
        top.addView(back);
        back.setOnClickListener(v -> showPage("Чатҳо"));

        TextView title = label("   " + name, 19,
                Color.WHITE, true);
        top.addView(title);
        root.addView(top);

        ScrollView scroll = new ScrollView(this);
        LinearLayout list = column();
        list.setPadding(14, 14, 14, 14);

        TextView note = label(
                "Сӯҳбат бо " + name, 13, Color.GRAY, false);
        note.setGravity(Gravity.CENTER);
        note.setPadding(0, 8, 0, 14);
        list.addView(note);

        for (String message : messages) {
            TextView bubble = label(message, 15, dark, false);
            bubble.setPadding(14, 11, 14, 11);
            bubble.setBackground(rounded(Color.WHITE));

            LinearLayout.LayoutParams p =
                    new LinearLayout.LayoutParams(-2, -2);
            p.setMargins(0, 0, 0, 9);
            list.addView(bubble, p);
        }

        scroll.addView(list);
        root.addView(scroll, new LinearLayout.LayoutParams(
                -1, 0, 1));

        LinearLayout composer = new LinearLayout(this);
        composer.setGravity(Gravity.CENTER_VERTICAL);
        composer.setPadding(8, 6, 8, 6);
        composer.setBackgroundColor(Color.WHITE);

        EditText input = new EditText(this);
        input.setSingleLine(true);
        input.setHint("Паём нависед...");
        composer.addView(input, new LinearLayout.LayoutParams(
                0, -2, 1));

        Button send = new Button(this);
        send.setText("➤");
        send.setTextColor(Color.WHITE);
        send.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(green));
        composer.addView(send);

        send.setOnClickListener(v -> {
            String value = input.getText().toString().trim();
            if (!value.isEmpty()) {
                messages.add(value);
                ((InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(
                                input.getWindowToken(), 0);
                openChat(name);
            }
        });

        root.addView(composer);
        setContentView(root);
    }

    private void showStatuses() {
        content.addView(label("Status", 25, dark, true));
        content.addView(label("Лаҳзаҳои худро мубодила кунед",
                14, Color.DKGRAY, false));

        addRow("➕", "Status-и ман", "Илова кардани Status");
        addRow("🟢", "Дӯст", "Status-и нав");

        Button add = new Button(this);
        add.setText("+ Status-и нав");
        content.addView(add);

        add.setOnClickListener(v -> {
            EditText input = new EditText(this);
            input.setHint("Матни Status");

            new AlertDialog.Builder(this)
                .setTitle("Status-и нав")
                .setView(input)
                .setNegativeButton("Бекор", null)
                .setPositiveButton("Нашр", (d, w) -> {
                    String s = input.getText().toString().trim();
                    if (!s.isEmpty()) {
                        new AlertDialog.Builder(this)
                            .setTitle("Status-и шумо")
                            .setMessage(s)
                            .setPositiveButton("Хуб", null)
                            .show();
                    }
                }).show();
        });
    }

    private void showCalls() {
        content.addView(label("Зангҳо", 25, dark, true));
        addRow("📞", "Дӯст", "Занги овозӣ");
        addRow("🎥", "Оила", "Занги видеоӣ");

        Button call = new Button(this);
        call.setText("+ Занги нав");
        content.addView(call);
        call.setOnClickListener(v ->
            new AlertDialog.Builder(this)
                .setTitle("Зангҳо")
                .setMessage("Барои зангҳои воқеӣ сервери зангҳо лозим аст.")
                .setPositiveButton("Хуб", null)
                .show()
        );
    }

    private void showProfile() {
        content.addView(label("Профили ман", 25, dark, true));

        TextView avatar = label("👤", 55, dark, false);
        avatar.setGravity(Gravity.CENTER);
        avatar.setPadding(0, 22, 0, 10);
        content.addView(avatar);

        TextView name = label("Корбари PINGO", 20, dark, true);
        name.setGravity(Gravity.CENTER);
        content.addView(name);

        TextView info = label("Рақами телефон ҳанӯз пайваст нест",
                13, Color.DKGRAY, false);
        info.setGravity(Gravity.CENTER);
        info.setPadding(0, 8, 0, 20);
        content.addView(info);

        addRow("🔒", "Махфият", "Танзимоти махфият");
        addRow("🔔", "Огоҳиномаҳо", "Танзимоти огоҳиномаҳо");
        addRow("⚙️", "Танзимот", "Танзимоти барнома");
    }
            }
                            
