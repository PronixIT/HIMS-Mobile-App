package com.pronix.hms;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
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
import com.pronix.hms.models.PatientDO;
import com.pronix.hms.common.Utils;
import com.pronix.hms.models.WebServiceDO;
import com.pronix.hms.services.AsyncTask;
import com.pronix.hms.services.OnTaskCompleted;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ravi on 1/31/2018.
 */

public class PatientDetails extends Activity implements View.OnClickListener, OnTaskCompleted {

    TextView tv_Name, tv_Gender, tv_BloodGroup, tv_Mobile, tv_Address, tv_Email, tv_Age, tv_PatientId;
    Button but_MedicalHistory;
    static PatientDO patientData;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Dialog dialog;
    String str_Prescription = "";
    Dialog customDialog = null;
    String newDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_patientdetailsnew);
        applyFontStyle((ViewGroup) this.getWindow().getDecorView());
        initialieControls();

    }

    public void initialieControls() {
        try {
            patientData = (PatientDO) getIntent().getSerializableExtra("PatientDetails");
            Typeface customFont = Utils.getFontStyle(getAssets());
            tv_Name = (TextView) findViewById(R.id.tv_Name);
            tv_Gender = (TextView) findViewById(R.id.tv_Gender);


            ((TextView) findViewById(R.id.tv_HeaderGender)).setTypeface(customFont);
            ((TextView) findViewById(R.id.tv_Gender)).setTypeface(customFont);
            ((TextView) findViewById(R.id.tv_HeaderAge)).setTypeface(customFont);
            ((TextView) findViewById(R.id.tv_Age)).setTypeface(customFont);
            ((TextView) findViewById(R.id.tv_DOB)).setTypeface(customFont);
            ((TextView) findViewById(R.id.tv_HeaderBloodGroup)).setTypeface(customFont);
            ((TextView) findViewById(R.id.tv_BloddGroup)).setTypeface(customFont);
            ((TextView) findViewById(R.id.tv_Email)).setTypeface(customFont);
            ((TextView) findViewById(R.id.tv_Mobile)).setTypeface(customFont);
            ((TextView) findViewById(R.id.tv_Address)).setTypeface(customFont);
            ((Button) findViewById(R.id.but_MedicalHistory)).setTypeface(customFont);
            ((Button) findViewById(R.id.but_Interventions)).setTypeface(customFont);

            if (getIntent().getStringExtra("Status") != null) {
                ((Button) findViewById(R.id.but_NextIntervention)).setVisibility(View.INVISIBLE);
                ((Button) findViewById(R.id.but_Inpatient)).setVisibility(View.INVISIBLE);
            }

            but_MedicalHistory = (Button) findViewById(R.id.but_MedicalHistory);
            but_MedicalHistory.setOnClickListener(this);
//            collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//            collapsingToolbarLayout.setTitle(patientData.getUserId());
//            collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
//            collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
//            collapsingToolbarLayout.setExpandedTitleTypeface(customFont);
//            collapsingToolbarLayout.setCollapsedTitleTypeface(customFont);


            tv_Address = (TextView) findViewById(R.id.tv_Address);
            tv_BloodGroup = (TextView) findViewById(R.id.tv_BloddGroup);
            tv_Email = (TextView) findViewById(R.id.tv_Email);
            tv_Mobile = (TextView) findViewById(R.id.tv_Mobile);
            tv_Age = (TextView) findViewById(R.id.tv_Age);
            tv_PatientId = (TextView) findViewById(R.id.tv_DOB);

            tv_Name.setText(Utils.isNull(patientData.getFullName()));
            tv_Gender.setText(Utils.isNull(patientData.getGender()));
            tv_Address.setText(Utils.isNull(patientData.getAddress()));
            tv_Email.setText(Utils.isNull(patientData.getEmail()).equals("") ? "cravi.1992@gmail.com" : Utils.isNull(patientData.getEmail()));
            tv_Mobile.setText(Utils.isNull(patientData.getMobile()).equals("") ? "9492501867" : Utils.isNull(patientData.getMobile()));
            tv_Age.setText(Utils.isNull(patientData.getAge()));
            tv_BloodGroup.setText(Utils.isNull(patientData.getBloodGroup()));
            tv_PatientId.setText(Utils.isNull(patientData.getPatientId()));
//            tv_HeaderName.setText(Utils.isNull(patientData.getUserId()));
            tv_Gender.setTypeface(customFont);

//            AppBarLayout appBarLayout = findViewById(R.id.app_bar);
//            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//                boolean isShow = false;
//                int scrollRange = -1;
//
//                @Override
//                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                    if (scrollRange == -1) {
//                        scrollRange = appBarLayout.getTotalScrollRange();
//                    }
//                    if (scrollRange + verticalOffset == 0) {
//                        collapsingToolbarLayout.setTitle(patientData.getUserId());
//                        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
//                        collapsingToolbarLayout.setContentScrim(getResources().getDrawable(R.drawable.effect));
//                        isShow = true;
//                    } else if (isShow) {
//                        collapsingToolbarLayout.setTitle(patientData.getUserId());//carefull there should a space between double quote otherwise it wont work
//
//                        isShow = false;
//                    }
//                }
//            });
        } catch (Exception e) {
            e.getMessage();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_MedicalHistory:
                Intent in = new Intent(this, MedicalHistoryTabs.class);
                in.putExtra("PatientId", patientData.getPatientId());
                startActivity(in);
                break;
            case R.id.but_Prescription:
//                customeDialog();
                Intent intent = new Intent(this, Prescription.class);
                intent.putExtra("PatientDetails", patientData);
                startActivity(intent);
                break;
            case R.id.but_Interventions:
                Intent in1 = new Intent(this, Intervention.class);
                in1.putExtra("PatientId", patientData.getPatientId());
                startActivity(in1);
                break;
            case R.id.but_NextIntervention:

                selectNextIntervention();
                break;
        }
    }

    public void customeDialog() {
        customDialog = new Dialog(this, R.style.DialogTheme);
        customDialog.setCancelable(false);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.prescription);
        Typeface custom = Utils.getFontStyle(getAssets());
        final EditText et_Prescription = (EditText) customDialog.findViewById(R.id.et_Prescription);
        et_Prescription.setText(Utils.isNull(patientData.getPrescription()));
        Button but_Submit = (Button) customDialog.findViewById(R.id.but_Submit);
        Button but_Cancel = (Button) customDialog.findViewById(R.id.but_Cancel);
        et_Prescription.setTypeface(custom);
        but_Submit.setTypeface(custom);
        but_Cancel.setTypeface(custom);
        ((TextView) customDialog.findViewById(R.id.tv_Header)).setTypeface(custom);
        but_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_Prescription = et_Prescription.getText().toString().trim();
                calPrescriptionWebService(et_Prescription.getText().toString().trim());
            }
        });
        but_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
        customDialog.show();


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

    public void progressDialog(Activity activity) {
        dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progressview);
        dialog.show();
    }

    public void calPrescriptionWebService(String prescription) {
        try {
            progressDialog(this);
            WebServiceDO webServiceDO = new WebServiceDO();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("prescription", prescription);
            jsonObject.put("appointmentId", patientData.getAppointmentId());
            webServiceDO.result = Constants.SENT;
            webServiceDO.request = "PRESCRIPTION";
            new AsyncTask(PatientDetails.this, PatientDetails.this, Constants.URLBase + "" + Constants.REQUEST_PRESCRIPTION, "POST", jsonObject.toString()).execute(webServiceDO);
        } catch (Exception e) {
            e.getMessage();
            Utils.hideProgress(dialog);
        }
    }


    public void calNextInterventionWebService(int noofDays) {
        try {
            progressDialog(this);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_YEAR, noofDays);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            newDate = dateFormat.format(calendar.getTime());
            WebServiceDO webServiceDO = new WebServiceDO();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("patientId", patientData.getPatientId());
            jsonObject.put("doctorId", Constants.userDetails.getUserId());
            jsonObject.put("date", newDate);
            webServiceDO.result = Constants.SENT;
            webServiceDO.request = "NEXTINTERVENTION";
            new AsyncTask(PatientDetails.this, PatientDetails.this, Constants.URLBase + "" + Constants.REQUEST_NEXTINTERVENTION, "POST", jsonObject.toString()).execute(webServiceDO);
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
                if (webServiceDO.request.equals("NEXTINTERVENTION")) {
                    JSONObject jsonObject = new JSONObject(webServiceDO.responseContent);
                    if (jsonObject.getString("status").toUpperCase().equals("SUCCESS")) {
                        Utils.showalert(this, "Alert", patientData.getFullName() + " next intervention is on " + newDate);
                    } else {
                        Utils.showalert(this, "Alert", jsonObject.getString("errorDescription"));
                    }

                } else {
                    JSONObject jsonObject = new JSONObject(webServiceDO.responseContent);
                    if (jsonObject.getString("status").toUpperCase().equals("SUCCESS")) {
                        patientData.setPrescription(str_Prescription);
                        confirmationAlert();
                    } else {
                        Utils.showalert(this, "Alert", jsonObject.getString("errorDescription"));
                    }
                }
            } else
                Utils.showalert(this, "Alert", "Error " + webServiceDO.responseContent);

        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void confirmationAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PatientDetails.this);
        alertDialog.setTitle("Alert");

        alertDialog.setMessage("Prescription updated successfully");

        alertDialog.setIcon(R.mipmap.ic_launcher);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Utils.hideProgress(customDialog);
            }
        });

        alertDialog.show();
    }

    public void selectNextIntervention() {
        final String[] items = {"15 Days", "30 days"};
        new AlertDialog.Builder(this)
                .setTitle("Select next intervention")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
//                        calNextInterventionWebService((whichButton == 0) ? 14 : 28);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date());
                        calendar.add(Calendar.DAY_OF_YEAR, (whichButton == 0) ? 14 : 28);
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        newDate = dateFormat.format(calendar.getTime());
                        Utils.showalert(PatientDetails.this, "Alert", patientData.getFullName() + " next intervention is on " + newDate);
                    }
                })
                .show();
    }
}
