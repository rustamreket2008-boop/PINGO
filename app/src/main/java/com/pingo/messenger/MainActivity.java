
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
import android.text.InputType;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {

    private final int green = Color.rgb(32, 201, 120);
    private final int dark = Color.rgb(16, 61, 43);
    private final int bg = Color.rgb(244, 248, 245);

    private LinearLayout content;

    private ChatStorage chatStorage;
    private FriendsManager friendsManager;
    private StatusManager statusManager;
    private CallManager callManager;
    private ProfileManager profileManager;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        getWindow().setStatusBarColor(dark);
        getWindow().setNavigationBarColor(dark);

        chatStorage = new ChatStorage(this);
        friendsManager = new FriendsManager(this);
        statusManager = new StatusManager(this);
        callManager = new CallManager(this);
        profileManager = new ProfileManager(this);

        showPage("Чатҳо");
    }

    private TextView label(String s, int size, int color,
                           boolean bold) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(size);
        t.setTextColor(color);

        if (bold) {
            t.setTypeface(null, Typeface.BOLD);
        }

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
        header.addView(label(
                "Connect • Chat • Share",
                13, Color.rgb(190, 240, 211), false
        ));

        root.addView(header);

        LinearLayout tabs = new LinearLayout(this);
        tabs.setBackgroundColor(Color.WHITE);

        String[] names = {"Чатҳо", "Status", "Зангҳо", "Профил"};

        for (String name : names) {
            TextView tab = label(
                    name, 12,
                    name.equals(page) ? green : dark,
                    true
            );

            tab.setGravity(Gravity.CENTER);
            tab.setPadding(2, 16, 2, 16);

            tabs.addView(tab, new LinearLayout.LayoutParams(
                    0, -2, 1
            ));

            tab.setOnClickListener(v -> showPage(name));
        }

        root.addView(tabs);

        ScrollView scroll = new ScrollView(this);
        content = column();
        content.setPadding(18, 18, 18, 18);
        scroll.addView(content);

        root.addView(scroll, new LinearLayout.LayoutParams(
                -1, 0, 1
        ));

        TextView footer = label(
                "PINGO • Паёмрасони замонавӣ",
                12, Color.GRAY, false
        );
        footer.setGravity(Gravity.CENTER);
        footer.setPadding(8, 14, 8, 14);
        footer.setBackgroundColor(Color.WHITE);
        root.addView(footer);

        setContentView(root);

        switch (page) {
            case "Чатҳо":
                showChats();
                break;
            case "Status":
                showStatuses();
                break;
            case "Зангҳо":
                showCalls();
                break;
            case "Профил":
                showProfile();
                break;
        }
    }

    private void addRow(String emoji, String name, String preview,
                        Runnable action) {
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
                0, -2, 1
        ));

        card.setOnClickListener(v -> action.run());
    }

    private void showChats() {
        content.addView(label("Сӯҳбатҳо", 25, dark, true));
        content.addView(label(
                "Бо дӯстони худ сӯҳбат кунед",
                14, Color.DKGRAY, false
        ));

        ArrayList<String> friends = friendsManager.getFriends();

        if (friends.isEmpty()) {
            addRow("👋", "Хуш омадӣ!", "Аввал дӯст илова кун",
                    this::showAddFriendDialog);
        } else {
            for (String friend : friends) {
                addRow("👤", friend, "Кушодани сӯҳбат",
                        () -> openChat(friend));
            }
        }

        Button addFriend = new Button(this);
        addFriend.setText("+ Илова кардани дӯст");
        content.addView(addFriend);
        addFriend.setOnClickListener(v -> showAddFriendDialog());

        Button newChat = new Button(this);
        newChat.setText("+ Чати нав");
        content.addView(newChat);

        newChat.setOnClickListener(v -> {
            EditText input = new EditText(this);
            input.setHint("Номи чат");

            new AlertDialog.Builder(this)
                    .setTitle("Чати нав")
                    .setView(input)
                    .setNegativeButton("Бекор", null)
                    .setPositiveButton("Кушодан", (d, w) -> {
                        String name = input.getText().toString().trim();

                        if (!name.isEmpty()) {
                            openChat(name);
                        }
                    }).show();
        });
    }

    private void showAddFriendDialog() {
        EditText input = new EditText(this);
        input.setHint("Номи дӯст");
        input.setSingleLine(true);

        new AlertDialog.Builder(this)
                .setTitle("Илова кардани дӯст")
                .setView(input)
                .setNegativeButton("Бекор", null)
                .setPositiveButton("Илова", (d, w) -> {
                    String name = input.getText().toString().trim();

                    if (name.isEmpty()) {
                        Toast.makeText(this, "Номро навис",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (friendsManager.addFriend(name)) {
                        showPage("Чатҳо");
                    } else {
                        Toast.makeText(this,
                                "Ин ном холӣ аст ё аллакай вуҷуд дорад",
                                Toast.LENGTH_SHORT).show();
                    }
                }).show();
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

        TextView title = label("   " + name, 19, Color.WHITE, true);
        top.addView(title, new LinearLayout.LayoutParams(0, -2, 1));

        TextView options = label("⋮", 26, Color.WHITE, true);
        top.addView(options);
        options.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle(name)
                        .setItems(new String[]{
                                "Маълумот", "Нест кардани таърихи чат"
                        }, (dialog, which) -> {
                            if (which == 0) {
                                new AlertDialog.Builder(this)
                                        .setTitle(name)
                                        .setMessage(
                                                "Чат дар телефони шумо нигоҳ дошта мешавад."
                                        )
                                        .setPositiveButton("Хуб", null)
                                        .show();
                            } else {
                                new AlertDialog.Builder(this)
                                        .setTitle("Тоза кардани чат?")
                                        .setMessage(
                                                "Ҳамаи паёмҳои маҳаллии ин чат нест мешаванд."
                                        )
                                        .setNegativeButton("Бекор", null)
                                        .setPositiveButton("Тоза кардан",
                                                (dd, ww) -> {
                                                    chatStorage.clearMessages(name);
                                                    openChat(name);
                                                }).show();
                            }
                        }).show()
        );

        root.addView(top);

        ScrollView scroll = new ScrollView(this);
        LinearLayout list = column();
        list.setPadding(14, 14, 14, 14);

        TextView note = label(
                "Сӯҳбат бо " + name,
                13, Color.GRAY, false
        );
        note.setGravity(Gravity.CENTER);
        note.setPadding(0, 8, 0, 14);
        list.addView(note);

        for (String message : chatStorage.getMessages(name)) {
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
                -1, 0, 1
        ));

        LinearLayout composer = new LinearLayout(this);
        composer.setGravity(Gravity.CENTER_VERTICAL);
        composer.setPadding(8, 6, 8, 6);
        composer.setBackgroundColor(Color.WHITE);

        EditText input = new EditText(this);
        input.setSingleLine(true);
        input.setHint("Паём нависед...");
        composer.addView(input, new LinearLayout.LayoutParams(
                0, -2, 1
        ));

        Button send = new Button(this);
        send.setText("➤");
        send.setTextColor(Color.WHITE);
        send.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(green)
        );
        composer.addView(send);

        send.setOnClickListener(v -> {
            String value = input.getText().toString().trim();

            if (!value.isEmpty()) {
                chatStorage.addMessage(name, value);

                ((InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(
                                input.getWindowToken(), 0
                        );

                openChat(name);
            }
        });

        root.addView(composer);
        setContentView(root);
    }

    private void showStatuses() {
        content.addView(label("Status", 25, dark, true));
        content.addView(label(
                "Лаҳзаҳои худро мубодила кунед",
                14, Color.DKGRAY, false
        ));

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
                        String text = input.getText().toString().trim();

                        if (statusManager.addStatus(
                                profileManager.getName(), text)) {
                            showPage("Status");
                        } else {
                            Toast.makeText(this,
                                    "Матнро навис",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).show();
        });

        for (JSONObject status : statusManager.getStatuses()) {
            String user = status.optString("user", "Корбар");
            String text = status.optString("text", "");
            long time = status.optLong("time", 0);

            addRow("🟢", user, text + "\n" + formatTime(time), () ->
                    new AlertDialog.Builder(this)
                            .setTitle(user)
                            .setMessage(text)
                            .setPositiveButton("Хуб", null)
                            .show());
        }
    }

    private String formatTime(long time) {
        if (time <= 0) return "";
        java.text.SimpleDateFormat fmt =
                new java.text.SimpleDateFormat(
                        "dd.MM HH:mm", Locale.getDefault());
        return fmt.format(new java.util.Date(time));
    }

    private void showCalls() {
        content.addView(label("Зангҳо", 25, dark, true));

        Button call = new Button(this);
        call.setText("+ Занги нав");
        content.addView(call);

        call.setOnClickListener(v -> {
            ArrayList<String> friends = friendsManager.getFriends();

            if (friends.isEmpty()) {
                Toast.makeText(this,
                        "Аввал дӯст илова кун",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            String[] names = friends.toArray(new String[0]);

            new AlertDialog.Builder(this)
                    .setTitle("Интихоби дӯст")
                    .setItems(names, (dialog, which) -> {
                        String name = names[which];
                        callManager.addCall(name, "voice", "local");

                        new AlertDialog.Builder(this)
                                .setTitle("Занги PINGO")
                                .setMessage(
                                        "Занг ба таърих сабт шуд.\n" +
                                        "Барои занги воқеӣ байни телефонҳо сервери зангҳо лозим аст."
                                )
                                .setPositiveButton("Хуб", null)
                                .show();

                        showPage("Зангҳо");
                    }).show();
        });

        ArrayList<JSONObject> calls = callManager.getCalls();

        if (calls.isEmpty()) {
            content.addView(label(
                    "Ҳоло таърихи зангҳо нест",
                    14, Color.GRAY, false
            ));
        }

        for (JSONObject call : calls) {
            String name = call.optString("name", "Корбар");
            String type = call.optString("type", "voice");
            String result = call.optString("result", "");
            String when = formatTime(call.optLong("time", 0));

            addRow(type.equals("video") ? "🎥" : "📞",
                    name, result + " • " + when,
                    () -> new AlertDialog.Builder(this)
                            .setTitle("Таърихи занг")
                            .setMessage(name + "\n" + when)
                            .setPositiveButton("Хуб", null)
                            .show());
        }

        Button clear = new Button(this);
        clear.setText("Тоза кардани таърихи зангҳо");
        content.addView(clear);

        clear.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Тоза кардан?")
                        .setMessage("Таърихи ҳамаи зангҳо нест шавад?")
                        .setNegativeButton("Бекор", null)
                        .setPositiveButton("Тоза кардан", (d, w) -> {
                            callManager.clearCalls();
                            showPage("Зангҳо");
                        }).show()
        );
    }

    private void showProfile() {
        content.addView(label("Профили ман", 25, dark, true));

        TextView avatar = label("👤", 55, dark, false);
        avatar.setGravity(Gravity.CENTER);
        avatar.setPadding(0, 22, 0, 10);
        content.addView(avatar);

        TextView name = label(profileManager.getName(),
                20, dark, true);
        name.setGravity(Gravity.CENTER);
        content.addView(name);

        TextView about = label(profileManager.getAbout(),
                13, Color.DKGRAY, false);
        about.setGravity(Gravity.CENTER);
        about.setPadding(0, 8, 0, 20);
        content.addView(about);

        Button edit = new Button(this);
        edit.setText("✏️ Таҳрири профил");
        content.addView(edit);

        edit.setOnClickListener(v -> showEditProfile());

        addRow("🔒", "Махфият",
                "Танзимоти махфият", this::showPrivacyInfo);

        addRow("🔔", "Огоҳиномаҳо",
                "Танзимоти огоҳиномаҳо", this::showNotificationInfo);

        addRow("⚙️", "Танзимот",
                "Танзимоти барнома", this::showSettingsInfo);
    }

    private void showEditProfile() {
        LinearLayout form = column();
        form.setPadding(12, 4, 12, 4);

        EditText name = new EditText(this);
        name.setHint("Номи шумо");
        name.setText(profileManager.getName());
        form.addView(name);

        EditText about = new EditText(this);
        about.setHint("Дар бораи худ");
        about.setText(profileManager.getAbout());
        form.addView(about);

        new AlertDialog.Builder(this)
                .setTitle("Таҳрири профил")
                .setView(form)
                .setNegativeButton("Бекор", null)
                .setPositiveButton("Сабт", (d, w) -> {
                    String newName = name.getText().toString().trim();
                    String newAbout = about.getText().toString().trim();

                    if (!newName.isEmpty()) {
                        profileManager.setName(newName);
                    }

                    profileManager.setAbout(newAbout);
                    showPage("Профил");
                }).show();
    }

    private void showPrivacyInfo() {
        new AlertDialog.Builder(this)
                .setTitle("Махфият")
                .setMessage(
                        "Маълумоти ҳозира дар дохили телефони шумо нигоҳ дошта мешавад. " +
                        "Барои ҳисоби онлайн ва ҳамоҳангсозии дастгоҳҳо сервер лозим аст."
                )
                .setPositiveButton("Хуб", null)
                .show();
    }

    private void showNotificationInfo() {
        new AlertDialog.Builder(this)
                .setTitle("Огоҳиномаҳо")
                .setMessage(
                        "Огоҳиномаҳои паёмҳои онлайн баъд аз пайваст кардани сервер илова мешаванд."
                )
                .setPositiveButton("Хуб", null)
                .show();
    }
private void showSettingsInfo() {
        new AlertDialog.Builder(this)
                .setTitle("Танзимот")
                .setItems(new String[]{
                        "Тоза кардани ҳамаи Status-ҳо",
                        "Тоза кардани ҳамаи дӯстҳо"
                }, (dialog, which) -> {
                    if (which == 0) {
                        new AlertDialog.Builder(this)
                                .setTitle("Тоза кардани Status-ҳо?")
                                .setNegativeButton("Бекор", null)
                                .setPositiveButton("Тоза кардан", (d, w) -> {
                                    statusManager.clearStatuses();
                                    showPage("Status");
                                }).show();
                    } else {
                        new AlertDialog.Builder(this)
                                .setTitle("Тоза кардани дӯстҳо?")
                                .setNegativeButton("Бекор", null)
                                .setPositiveButton("Тоза кардан", (d, w) -> {
                                    friendsManager.clearFriends();
                                    showPage("Чатҳо");
                                }).show();
                    }
                }).show();
    }
                            }

    
