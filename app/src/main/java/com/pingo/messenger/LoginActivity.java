package com.pingo.messenger;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

    private int bgColor = Color.rgb(10, 17, 32);
    private int accent = Color.rgb(0, 210, 170);

    private GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(radius);
        return d;
    }

    private TextView text(String value, int size, int color) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER);
        return t;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(bgColor);
        getWindow().setNavigationBarColor(bgColor);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setPadding(28, 35, 28, 25);
        root.setBackgroundColor(bgColor);

        TextView logo = text("P", 42, bgColor);
        logo.setTypeface(null, Typeface.BOLD);
        logo.setBackground(shape(accent, 28));

        LinearLayout.LayoutParams logoParams =
                new LinearLayout.LayoutParams(100, 100);
        logoParams.gravity = Gravity.CENTER;
        logoParams.bottomMargin = 22;
        root.addView(logo, logoParams);

        TextView title = text("PINGO", 32, Color.WHITE);
        title.setTypeface(null, Typeface.BOLD);
        root.addView(title);

        TextView subtitle = text(
                "Ҳамеша дар тамос бош!",
                16, Color.LTGRAY
        );

        LinearLayout.LayoutParams subParams =
                new LinearLayout.LayoutParams(
                        -1, -2
                );
        subParams.topMargin = 8;
        subParams.bottomMargin = 45;
        root.addView(subtitle, subParams);

        TextView label = text(
                "Воридшавӣ бо рақами телефон",
                18, Color.WHITE
        );
        label.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);

        LinearLayout.LayoutParams labelParams =
                new LinearLayout.LayoutParams(-1, -2);
        labelParams.bottomMargin = 16;
        root.addView(label, labelParams);

        EditText phone = new EditText(this);
        phone.setSingleLine(true);
        phone.setHint("+992 900 00 00 00");
        phone.setHintTextColor(Color.GRAY);
        phone.setTextColor(Color.WHITE);
        phone.setTextSize(17);
        phone.setInputType(3);
        phone.setPadding(18, 8, 18, 8);
        phone.setBackground(shape(
                Color.rgb(25, 36, 54), 18
        ));

        root.addView(phone,
                new LinearLayout.LayoutParams(-1, 60));

        Button next = new Button(this);
        next.setText("ИДОМА ДОДАН  →");
        next.setTextColor(bgColor);
        next.setTextSize(15);
        next.setTypeface(null, Typeface.BOLD);
        next.setAllCaps(false);
        next.setBackground(shape(accent, 18));

        LinearLayout.LayoutParams btnParams =
                new LinearLayout.LayoutParams(-1, 58);
        btnParams.topMargin = 22;
        root.addView(next, btnParams);

        TextView info = text(
                "Бо ворид шудан, шумо метавонед бо дӯстони худ муошират кунед.",
                13, Color.LTGRAY
        );

        LinearLayout.LayoutParams infoParams =
                new LinearLayout.LayoutParams(-1, -2);
        infoParams.topMargin = 25;
        root.addView(info, infoParams);

        TextView footer = text(
                "PINGO • Пайвасти дӯстӣ",
                13, accent
        );

        LinearLayout.LayoutParams footerParams =
                new LinearLayout.LayoutParams(-1, -2);
        footerParams.topMargin = 35;
        root.addView(footer, footerParams);

        next.setOnClickListener(v -> {
            String number = phone.getText().toString().trim();

            if (number.isEmpty()) {
                phone.setError("Рақами телефонро нависед");
                phone.requestFocus();
                return;
            }

            if (number.replaceAll("[^0-9]", "").length() < 9) {
                phone.setError("Рақами телефон нодуруст аст");
                phone.requestFocus();
                return;
            }

            Toast.makeText(
                    this,
                    "Қадами оянда: тасдиқи рақам тавассути Firebase",
                    Toast.LENGTH_LONG
            ).show();
        });

        setContentView(root);
    }
          }
          
