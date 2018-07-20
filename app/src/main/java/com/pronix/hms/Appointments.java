package com.pronix.hms;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pronix.hms.models.PatientDO;

public class Appointments extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Menu");
    }

    public void moveToOutPatients(View view)
    {
        Intent in = new Intent(this, PatientList.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(in,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            startActivity(in);
        }
    }

    public void moveToInPatients(View view)
    {
        Intent in = new Intent(this, InpatientList.class);
        in.putExtra("Status", "Inpatient");
        in.putExtra("PatientDetails", loadData());
        startActivity(in);
    }

    public PatientDO loadData()
    {
        PatientDO patientDO = new PatientDO();
        patientDO.setName("Vishnu Vardhan");
        patientDO.setGender("Male");
        patientDO.setAge("29");
        patientDO.setAddress("Vijayawada");
        patientDO.setMobile("90833***40");
        patientDO.setPatientId("PT00000008");
        patientDO.setEmail("vardhan@gmail.com");
        patientDO.setBloodGroup("O-");
        patientDO.setFullName("Vishnu Vardhan");
        patientDO.setPrescription("");
        return patientDO;
    }
}
