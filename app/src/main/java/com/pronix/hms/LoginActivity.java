package com.pronix.hms;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import it.sephiroth.android.library.easing.Back;
import it.sephiroth.android.library.easing.EasingManager;

/**
 * Created by ravi on 1/25/2018.
 */

public class LoginActivity extends Activity implements View.OnClickListener{
    CardView singupButton = null;
    TextView forgetPassword, tv_HeaderLogin;
    EditText et_User, et_Password;
    Button but_Login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        singupButton = (CardView) findViewById(R.id.singup_button);
        singupButton.setOnClickListener(this);
        singupButton.setVisibility(View.GONE);
        initializeControls();
        setTypeface();
        showSingupButton();
    }
    public void initializeControls()
    {
        et_User = (EditText) findViewById(R.id.et_User);
        et_Password = (EditText) findViewById(R.id.et_Password);
        tv_HeaderLogin = (TextView) findViewById(R.id.tv_LOGIN);
        but_Login = (Button) findViewById(R.id.but_Login);

        et_User.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_User.setTextSize(28);
                et_User.setElevation(20);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void afterTextChanged(Editable editable) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        et_User.setTextSize(24);
                        et_User.setElevation(0);
                    }
                },200);

            }
        });
    }

    public void setTypeface()
    {
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/opensansitalic.ttf");
        forgetPassword = (TextView) findViewById(R.id.forgetPassword);
        forgetPassword.setTypeface(custom_font);

        Typeface customFont = Typeface.createFromAsset(getAssets(),  "fonts/opensansregular.ttf");
        et_User.setTypeface(customFont);

        Typeface customopensanslight = Typeface.createFromAsset(getAssets(),  "fonts/opensanslight.ttf");
        et_Password.setTypeface(customopensanslight);
        Typeface lobster = Typeface.createFromAsset(getAssets(),  "fonts/lobster.otf");
        tv_HeaderLogin.setTypeface(lobster);

        Typeface customroboto = Typeface.createFromAsset(getAssets(),  "fonts/robotoregular.ttf");
        but_Login.setTypeface(customroboto);


    }

    private void showSingupButton() {



        singupButton.setVisibility(View.VISIBLE);
        singupButton.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        findViewById(R.id.singup_container).setVisibility(View.VISIBLE);

        final float scale = getResources().getDisplayMetrics().density;
        final int curr_singup_margin = -singupButton.getWidth();
        final int target_singup_margin = (int) (-35 * scale + 0.5f);

        EasingManager manager = new EasingManager(new EasingManager.EasingCallback() {

            @Override
            public void onEasingValueChanged(double value, double oldValue) {
                int diff_margin = curr_singup_margin - target_singup_margin;
                int margin = target_singup_margin + (int) (diff_margin - (diff_margin * value));

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) singupButton.getLayoutParams();
                layoutParams.setMargins(0, 0, margin, 0);
                singupButton.requestLayout();


            }

            @Override
            public void onEasingStarted(double value) {
                int diff_margin = curr_singup_margin - target_singup_margin;
                int margin = target_singup_margin + (int) (diff_margin - (diff_margin * value));

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) singupButton.getLayoutParams();
                layoutParams.setMargins(0, 0, margin, 0);
                singupButton.requestLayout();

            }

            @Override
            public void onEasingFinished(double value) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) singupButton.getLayoutParams();
                layoutParams.setMargins(0, 0, target_singup_margin, 0);
                singupButton.requestLayout();

            }
        });

        manager.start(Back.class, EasingManager.EaseType.EaseInOut, 0, 1, 1600);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.singup_button:
                Intent in = new Intent();
                in.setClass(this, RegistrationActivity.class);
                startActivity(in);
                break;
        }
    }
}
