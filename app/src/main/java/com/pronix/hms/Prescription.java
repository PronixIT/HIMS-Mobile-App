package com.pronix.hms;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.pronix.hms.common.Constants;
import com.pronix.hms.common.Utils;
import com.pronix.hms.models.PatientDO;
import com.pronix.hms.models.WebServiceDO;
import com.pronix.hms.services.AsyncTask;
import com.pronix.hms.services.OnTaskCompleted;

import org.json.JSONObject;

public class Prescription extends Activity implements View.OnClickListener, OnTaskCompleted {
    EditText et_Prescription;
    Dialog dialog;
    Button but_Submit;
    PatientDO patientData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription);
        patientData = (PatientDO) getIntent().getSerializableExtra("PatientDetails");
        initializeControls();

    }

    void initializeControls() {
        et_Prescription = (EditText) findViewById(R.id.et_Prescription);
        et_Prescription.setText(Utils.isNull(patientData.getPrescription()));
        but_Submit = (Button) findViewById(R.id.but_Submit);
        but_Submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_Submit:
                calPrescriptionWebService(et_Prescription.getText().toString());
                break;
            case R.id.but_Cancel:
                finish();
                break;
        }
    }

    public void calPrescriptionWebService(String prescription) {
        try {
            progressDialog(this);
            WebServiceDO webServiceDO = new WebServiceDO();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("prescription", prescription);
            jsonObject.put("appointmentId", PatientDetails.patientData.getAppointmentId());
            webServiceDO.result = Constants.SENT;
            webServiceDO.request = "PRESCRIPTION";
            new AsyncTask(Prescription.this, Prescription.this, Constants.URLBase + "" + Constants.REQUEST_PRESCRIPTION, "POST", jsonObject.toString()).execute(webServiceDO);
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
            if (webServiceDO.result.equals(Constants.SUCCESS)) {

                JSONObject jsonObject = new JSONObject(webServiceDO.responseContent);
                if (jsonObject.getString("status").toUpperCase().equals("SUCCESS")) {
                        patientData.setPrescription(et_Prescription.getText().toString());
                        confirmationAlert();
                } else {
                    Utils.showalert(this, "Alert", jsonObject.getString("errorDescription"));
                }

            } else
                Utils.showalert(this, "Alert", "Error " + webServiceDO.responseContent);

        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void confirmationAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Alert");

        alertDialog.setMessage("Prescription updated successfully");

        alertDialog.setIcon(R.mipmap.ic_launcher);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        alertDialog.show();
    }
}
