package com.pronix.hms;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.pronix.hms.common.Utils;
import com.pronix.hms.models.PatientDO;

public class InpatientPatientDetails extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inpatient_patient_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Details");
    }

    public void movetoPrerequisites(View view) {
        startActivity(new Intent(this, DailyTreatment.class));
    }

    public void movetoPrescription(View view) {
        PatientDO patientData = (PatientDO) getIntent().getSerializableExtra("PatientDetails");
        Intent intent = new Intent(this, Prescription.class);
        intent.putExtra("PatientDetails", patientData);
        startActivity(intent);
    }

    public void showDischargeConfirmation(View view) {
//        confirmationAlert();
        Utils.customeAlertDialog(this, "Confirmation", "Ready to discharge?", false);
    }

    public void extendDaysDialog(View view)
    {
        customeAlertDialog();
    }

    public void confirmationAlert() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(InpatientPatientDetails.this);
        alertDialog.setTitle("Confirmation");

        alertDialog.setMessage("Ready to discharge?");

        alertDialog.setIcon(R.mipmap.ic_launcher);

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        alertDialog.show();
    }


    public void customeAlertDialog()
    {
        final Dialog dialog = new Dialog(InpatientPatientDetails.this);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.daysextend_view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button but_Ok = (Button) dialog.findViewById(R.id.but_Ok);
        Button but_Cancel = (Button) dialog.findViewById(R.id.but_Cancel);
        but_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        but_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }



}
