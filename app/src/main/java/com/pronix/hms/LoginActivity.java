package com.pronix.hms;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pronix.hms.common.Constants;
import com.pronix.hms.common.Utils;
import com.pronix.hms.models.UserDetails;
import com.pronix.hms.models.WebServiceDO;
import com.pronix.hms.services.AsyncTask;
import com.pronix.hms.services.OnTaskCompleted;

import org.json.JSONObject;
import org.w3c.dom.Text;

import it.sephiroth.android.library.easing.Back;
import it.sephiroth.android.library.easing.EasingManager;

/**
 * Created by ravi on 1/25/2018.
 */

public class LoginActivity extends Activity implements View.OnClickListener, View.OnFocusChangeListener, OnTaskCompleted {
    CardView singupButton = null;
    TextView forgetPassword, tv_HeaderLogin, tv_SignUp;
    EditText et_User, et_Password;
    Button but_Login;
    Animation myAnim;
    Dialog dialog;

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
        myAnim = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.fadein);
    }

    public void initializeControls() {
        et_User = (EditText) findViewById(R.id.et_User);
        et_User.setOnFocusChangeListener(this);
        et_Password = (EditText) findViewById(R.id.et_Password);
        et_Password.setOnFocusChangeListener(this);
        tv_HeaderLogin = (TextView) findViewById(R.id.tv_LOGIN);
        tv_SignUp = (TextView) findViewById(R.id.tv_SignUp);
        but_Login = (Button) findViewById(R.id.but_Login);
        but_Login.setOnClickListener(this);

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
                }, 200);

            }
        });

        et_Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_Password.setTextSize(28);
//                et_Password.setElevation(20);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        et_Password.setTextSize(24);
//                        et_Password.setElevation(0);
                    }
                }, 200);
            }
        });


    }

    public void setTypeface() {
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/opensansitalic.ttf");
        forgetPassword = (TextView) findViewById(R.id.forgetPassword);
        forgetPassword.setTypeface(custom_font);

        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/robotoregular.ttf");
        et_User.setTypeface(customFont);
        et_Password.setTypeface(customFont);
        tv_HeaderLogin.setTypeface(customFont);
        tv_SignUp.setTypeface(customFont);
        tv_HeaderLogin.setVisibility(View.GONE);
        but_Login.setTypeface(customFont);

        Typeface customopensanslight = Typeface.createFromAsset(getAssets(), "fonts/opensanslight.ttf");

        Typeface lobster = Typeface.createFromAsset(getAssets(), "fonts/lobster.otf");

        Typeface customroboto = Typeface.createFromAsset(getAssets(), "fonts/robotoregular.ttf");


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
        switch (view.getId()) {
            case R.id.singup_button:
                Intent in = new Intent();
                in.setClass(this, RegistrationActivity.class);
                startActivity(in);
                break;
            case R.id.but_Login:
                if (!validations()) {
//                    calCredentialsWebservice();
                    Constants.userDetails = new UserDetails();
                    Constants.userDetails.name = "Vamsi Krishna";
                    Constants.userDetails.email = "VKrishna01@gmail.com";
                    Constants.userDetails.mobile = et_User.getText().toString();
                    Constants.userDetails.userId = "DR0000001";
                    moveToMenuScreen();
                }

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Utils.customeAlertDialog(this, "Alert", "Temporary message", true);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.et_User:
                if (b) {

                    et_User.setBackgroundResource(R.drawable.hover);
                    et_User.startAnimation(myAnim);
                } else {
                    et_User.setBackgroundResource(R.color.transperant);
                }
                break;
            case R.id.et_Password:
                if (b) {
                    final Animation myAnim = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.fadein);
                    et_Password.setBackgroundResource(R.drawable.hover);
                    et_Password.startAnimation(myAnim);
                } else {
                    et_Password.setBackgroundResource(R.color.transperant);
                }
                break;
        }
    }

    public boolean validations() {
        boolean status = false;
        String alert = "";
        if (et_User.getText().toString().trim().equals(""))
            alert = "Mobile is required";
        else if (et_Password.getText().toString().trim().equals(""))
            alert = "Password is required";

        if (!alert.equals("")) {
            status = true;
            Utils.showalert(this, "Alert", alert);
        }
        return status;
    }

    public void progressDialog(Activity activity) {
        dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progressview);
        dialog.show();
    }

    public void calCredentialsWebservice() {
        try {
            progressDialog(this);
            WebServiceDO webServiceDO = new WebServiceDO();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", et_User.getText().toString());
            jsonObject.put("password", et_Password.getText().toString());
            webServiceDO.result = Constants.SENT;
            webServiceDO.request = "CREDENTIALS";
            new AsyncTask(LoginActivity.this, LoginActivity.this, Constants.URLBase + "" + Constants.REQUEST_LOGIN, "POST", jsonObject.toString()).execute(webServiceDO);
        } catch (Exception e) {
            e.getMessage();
            Utils.hideProgress(dialog);
        }

    }

    @Override
    public void onTaskCompletes(WebServiceDO webServiceDO) {
        try {
            Utils.hideProgress(dialog);
            if (webServiceDO.result.equals(Constants.SUCCESS)) {
                if (webServiceDO.request.equals("CREDENTIALS")) {
                    JSONObject jsonObject = new JSONObject(webServiceDO.responseContent);
                    JSONObject json = new JSONObject(jsonObject.getString("responseStatus"));
                    if(json.getString("status").toUpperCase().equals("SUCCESS")) {
                        JSONObject userDetails = new JSONObject(jsonObject.getString("doctorDetailsData"));
                        Constants.userDetails = new UserDetails();
                        Constants.userDetails.name = userDetails.getString("fullName");
                        Constants.userDetails.email = userDetails.getString("emailId");
                        Constants.userDetails.mobile = userDetails.getString("mobile");
                        Constants.userDetails.userId = userDetails.getString("userId");
                        moveToMenuScreen();
                    }
                    else
                    {
                        Utils.showalert(LoginActivity.this, "Alert", json.getString("errorCode") + " : " + json.getString("errorDescription"));
                    }
                }
            } else {
                Utils.showalert(this, "Alert", webServiceDO.responseContent);
                Utils.hideProgress(dialog);
            }
        } catch (Exception e) {
            Utils.hideProgress(dialog);
        }
    }

    public void moveToMenuScreen() {
        Intent in = new Intent(this, MenuActiivty.class);
//        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, , "Name");
        startActivity(in);
    }
}
