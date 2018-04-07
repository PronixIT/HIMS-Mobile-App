package com.pronix.hms;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pronix.hms.common.Constants;
import com.pronix.hms.common.Utils;
import com.pronix.hms.models.WebServiceDO;
import com.pronix.hms.services.AsyncTask;
import com.pronix.hms.services.OnTaskCompleted;

import org.json.JSONObject;

/**
 * Created by ravi on 1/26/2018.
 */

public class RegistrationActivity extends Activity implements View.OnClickListener, OnTaskCompleted {
    TextView tv_HeaderRegistration;
    EditText et_Name, et_Email, et_Mobile, et_Password, et_ConfirmPassword;
    Button but_Signup;
    Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupWindowAnimations();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initializeControls();
//        tv_HeaderRegistration = (TextView) findViewById(R.id.tv_HeaderRegistration);

//        but_Signup = (Button) findViewById(R.id.but_Signup);
//        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/opensansregular.ttf");
//        tv_HeaderRegistration.setTypeface(customFont);
//        et_Name.setTypeface(customFont);
//        but_Signup.setTypeface(customFont);
    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        fade.setDuration(1000);
        getWindow().setReturnTransition(slide);
    }

    public void initializeControls() {
        try {
            et_Name = (TextInputEditText) findViewById(R.id.et_Name);
            et_Email = (TextInputEditText) findViewById(R.id.et_Email);
            et_Mobile = (TextInputEditText) findViewById(R.id.et_Mobile);
            et_Password = (TextInputEditText) findViewById(R.id.et_RegPassword);
            et_ConfirmPassword = (TextInputEditText) findViewById(R.id.et_ConfirmPassword);
            but_Signup = (Button) findViewById(R.id.but_Signup);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.but_Signup:
                    if (!validation())
                        calCredentialsWebservice();
                    break;

            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public boolean validation() {
        boolean status = false;
        String alert = "";
        if (et_Name.getText().toString().trim().equals("")) {
            alert = "Name is required";
        } else if (et_Email.getText().toString().trim().equals(""))
            alert = "Email is required";
        else if (et_Mobile.getText().toString().trim().equals(""))
            alert = "Mobile number is required";
        else if (et_Password.getText().toString().trim().equals(""))
            alert = "Password is required";
        else if (et_ConfirmPassword.getText().toString().trim().equals(""))
            alert = "Confirm password is required";
        else if (et_Name.getText().toString().trim().length() < 3)
            alert = "Name should be minimum 3 characters";
        else if (!et_Password.getText().toString().trim().equals(et_ConfirmPassword.getText().toString()))
            alert = "Password and confirm password should be same";

        if (!alert.equals("")) {
            status = true;
            Utils.showalert(this, "Alert", alert);
        }
        return status;

    }

    public void calCredentialsWebservice() {
        try {
            progressDialog(this);
            WebServiceDO webServiceDO = new WebServiceDO();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fullName", et_Name.getText().toString());
            jsonObject.put("emailId", et_Email.getText().toString());
            jsonObject.put("mobile", et_Mobile.getText().toString());
            jsonObject.put("password", et_Password.getText().toString());
            webServiceDO.result = Constants.SENT;
            webServiceDO.request = "REGISTRATION";
            new AsyncTask(RegistrationActivity.this, RegistrationActivity.this, Constants.URLBase + "" + Constants.REQUEST_REGISTER, "POST", jsonObject.toString()).execute(webServiceDO);
        } catch (Exception e) {
            e.getMessage();
            Utils.hideProgress(dialog);
        }

    }

    public void progressDialog(Activity activity) {
        dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progressview);
        dialog.show();
    }

    @Override
    public void onTaskCompletes(WebServiceDO webServiceDO) {
        try {
            Utils.hideProgress(dialog);
            if(webServiceDO.result.equals(Constants.SUCCESS))
            {
                JSONObject jsonObject = new JSONObject(webServiceDO.responseContent);
                if(jsonObject.getString("status").toUpperCase().equals("SUCCESS"))
                {
                    confirmationAlert();
                }
                else
                {
                    Utils.showalert(RegistrationActivity.this, "Alert", jsonObject.getString("errorCode") + " : " + jsonObject.getString("errorDescription"));
                }

            }
            else
            {
                Utils.showalert(RegistrationActivity.this, "Alert", webServiceDO.responseContent);
            }

        } catch (Exception e) {
            Utils.hideProgress(dialog);
            e.getMessage();
        }
    }

    public void movetoLoginScreen() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
    public void confirmationAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegistrationActivity.this);
                alertDialog.setTitle("Alert");

                alertDialog.setMessage("Profile registered successfully");

                alertDialog.setIcon(R.mipmap.ic_launcher);

                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        movetoLoginScreen();
                    }
                });

                alertDialog.show();
    }
}
