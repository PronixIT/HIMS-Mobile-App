package com.pronix.hms;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
 * Created by ravi on 2/1/2018.
 */

public class ProfileActivity extends Activity implements View.OnClickListener, OnTaskCompleted {
    Typeface customFont;
    TextInputEditText et_Hospital, et_Mobile, et_Email, et_Specialization, et_AcademicQualification, et_YearsExp,
            et_Timings, et_Consultation, et_FullName;
    TextView tv_Name;
    Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        applyFontStyle((ViewGroup) this.getWindow().getDecorView());
        customFont = Utils.getFontStyle(getAssets());
//                Typeface.createFromAsset(getAssets(), "fonts/ubunturegular.ttf");
//        calProfileDataWebservice();
        initializeControls();
//        applyFontStyle()

    }

    public void initializeControls() {
        ((TextInputLayout) findViewById(R.id.tl_mobile)).setTypeface(customFont);
        ((TextInputLayout) findViewById(R.id.tl_Hospital)).setTypeface(customFont);
        ((TextInputLayout) findViewById(R.id.tl_Email)).setTypeface(customFont);
        ((TextInputLayout) findViewById(R.id.tl_Specialization)).setTypeface(customFont);
        ((TextInputLayout) findViewById(R.id.tl_Qualification)).setTypeface(customFont);
        ((TextInputLayout) findViewById(R.id.tl_Timings)).setTypeface(customFont);
        ((TextInputLayout) findViewById(R.id.tl_Consultation)).setTypeface(customFont);
        ((TextInputLayout) findViewById(R.id.tl_FullName)).setTypeface(customFont);
        ((Button) findViewById(R.id.but_Submit)).setTypeface(customFont);
        ((TextView) findViewById(R.id.tv_Designation)).setTypeface(customFont);
        et_Hospital = (TextInputEditText) findViewById(R.id.et_Hospital);
        et_Hospital.setTypeface(customFont);
        et_Mobile = (TextInputEditText) findViewById(R.id.et_Mobile);
        et_Mobile.setTypeface(customFont);
        et_Mobile.setText(Constants.userDetails.mobile);
        et_Email = (TextInputEditText) findViewById(R.id.et_Email);
        et_Email.setTypeface(customFont);
        et_Email.setText(Constants.userDetails.email);
        et_Specialization = (TextInputEditText) findViewById(R.id.et_Specilization);
        et_Specialization.setTypeface(customFont);
        et_AcademicQualification = (TextInputEditText) findViewById(R.id.et_Qualification);
        et_YearsExp = (TextInputEditText) findViewById(R.id.et_Exp);
        et_YearsExp.setTypeface(customFont);
        et_AcademicQualification.setTypeface(customFont);
        tv_Name = (TextView) findViewById(R.id.tv_Name);
        tv_Name.setText(Constants.userDetails.name);
        et_Consultation = (TextInputEditText) findViewById(R.id.et_Consultation);
        et_Timings = (TextInputEditText) findViewById(R.id.et_Timings);
        et_FullName = (TextInputEditText) findViewById(R.id.et_FullName);
        et_Consultation.setTypeface(customFont);
        et_Timings.setTypeface(customFont);
        tv_Name.setTypeface(customFont);
        et_FullName.setTypeface(customFont);
        et_FullName.setText(Constants.userDetails.name);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_Submit:
                if (!validation()) {
                    sendProfileWebservice();
                }
                break;
        }
    }

    public boolean validation() {
        boolean status = false;
        String alert = "";
        if (et_Email.getText().toString().trim().equals("")) {
            alert = "Email is required";
        } else if (et_Hospital.getText().toString().trim().equals("")) {
            alert = "Hospital is required";
        } else if (et_Mobile.getText().toString().trim().equals("")) {
            alert = "mobile is required";
        } else if (et_FullName.getText().toString().trim().equals("")) {
            alert = "FullName is required";
        } else if (et_Specialization.getText().toString().trim().equals("")) {
            alert = "Specialization is required";
        } else if (et_AcademicQualification.getText().toString().trim().equals("")) {
            alert = "Qualification is required";
        } else if (et_YearsExp.getText().toString().trim().equals("")) {
            alert = "Experience is required";
        } else if (et_Timings.getText().toString().trim().equals("")) {
            alert = "Timings field is required";
        } else if (et_Consultation.getText().toString().trim().equals("")) {
            alert = "Consultation is required";
        }

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

    public void calProfileDataWebservice() {
        try {
            progressDialog(this);
            WebServiceDO webServiceDO = new WebServiceDO();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", Constants.userDetails.userId);
            webServiceDO.result = Constants.SENT;
            webServiceDO.request = "PROFILEDATA";
            new AsyncTask(ProfileActivity.this, ProfileActivity.this, Constants.URLBase + "" + Constants.REQUEST_PROFILE, "POST", jsonObject.toString()).execute(webServiceDO);
        } catch (Exception e) {
            e.getMessage();
            Utils.hideProgress(dialog);
        }
    }

    public void sendProfileWebservice() {
        try {
            progressDialog(this);
            WebServiceDO webServiceDO = new WebServiceDO();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", Constants.userDetails.userId);
            jsonObject.put("hospitalName", et_Hospital.getText().toString());
            jsonObject.put("specialization", et_Specialization.getText().toString());
            jsonObject.put("exp", et_YearsExp.getText().toString());
            jsonObject.put("qualification", et_AcademicQualification.getText().toString());
            jsonObject.put("consultationFee", "RS" + et_Consultation.getText().toString().toUpperCase().replace("RS",""));
            jsonObject.put("timings", et_Timings.getText().toString());
            jsonObject.put("fullName", et_FullName.getText().toString());
            webServiceDO.result = Constants.SENT;
            webServiceDO.request = "PROFILE";
            new AsyncTask(ProfileActivity.this, ProfileActivity.this, Constants.URLBase + "" + Constants.REQUEST_UPDATEPROFILE, "POST", jsonObject.toString()).execute(webServiceDO);
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
                if (webServiceDO.request.equals("PROFILE")) {
                    JSONObject jsonObject = new JSONObject(webServiceDO.responseContent);
                    if (jsonObject.getString("status").toUpperCase().equals("SUCCESS")) {
                        Utils.showalert(ProfileActivity.this, "Alert", "Profile Updated Successfully");
                    } else {
                        Utils.showalert(ProfileActivity.this, "Alert", jsonObject.getString("errorDescription"));
                    }

                } else if (webServiceDO.request.equals("PROFILEDATA")) {
                    JSONObject jsonObject = new JSONObject(webServiceDO.responseContent);
                    setFields(jsonObject);
                }

            } else {
                Utils.showalert(ProfileActivity.this, "Alert", webServiceDO.responseContent);
            }
        } catch (Exception e) {
            e.getMessage();
            Utils.hideProgress(dialog);
        }

    }

    public void setFields(JSONObject jsonObject) {
        try {
            et_YearsExp.setText(jsonObject.getString("exp"));
            et_AcademicQualification.setText(jsonObject.getString("qualification"));
            et_Specialization.setText(jsonObject.getString("specialization"));
            et_Hospital.setText(jsonObject.getString("hospitalName"));
            et_Timings.setText(jsonObject.getString("timings"));
            et_Consultation.setText(jsonObject.getString("consultationFee"));
            et_FullName.setText(jsonObject.getString("fullName"));
        } catch (Exception e) {

        }
    }

    public void applyFontStyle(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            Typeface custom = Utils.getFontStyle(getAssets());
            if (view instanceof TextInputEditText)
                ((TextInputEditText) view).setTypeface(custom);
            else if (view instanceof TextView)
                ((TextView) view).setTypeface(custom);
            else if (view instanceof TextInputLayout)
                ((TextInputLayout) view).setTypeface(custom);
            else if (view instanceof Button)
                ((Button) view).setTypeface(custom);
            else if (view instanceof EditText)
                ((EditText) view).setTypeface(custom);
            else if (view instanceof ViewGroup)
                applyFontStyle((ViewGroup) view);


        }

    }

    public void confirmationAlert(String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfileActivity.this);
        alertDialog.setTitle("Alert");

        alertDialog.setMessage(message);

        alertDialog.setIcon(R.mipmap.ic_launcher);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        alertDialog.show();
    }
}
