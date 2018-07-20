package com.pronix.hms;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;

import com.pronix.hms.common.CustomItemClickListener;
import com.pronix.hms.models.PatientDO;
import com.pronix.hms.renderer.InpatientAdapter;
import com.pronix.hms.renderer.PatientAdapter;

import java.util.ArrayList;

public class InpatientList extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rv_ImpPateientList;
    String[] value = {"Varun", "Latha", "Suma"};
    ArrayList<PatientDO> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inpatient_list);
        initializeControls();

        loadValues();
        loadRecyclerViewData(list);
    }


    public void initializeControls() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Inpatient List");

        rv_ImpPateientList = (RecyclerView) findViewById(R.id.rv_List);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_ImpPateientList.setLayoutManager(mLayoutManager);
        rv_ImpPateientList.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void loadValues() {
        PatientDO patientDO;
        for (int i = 0; i < 3; i++) {
            patientDO = new PatientDO();
            patientDO.setName(value[i]);
            patientDO.setFullName(value[i]);
            patientDO.setAge("36");
            patientDO.setEmail(value[i] + "@gmail.com");
            patientDO.setAddress("Hyderabad");
            patientDO.setPatientId("PT0000000" + i);
            patientDO.setAppointmentId("APT0000000" + i);
            if (value[i].equals("Latha") || value[i].equals("Suma")) {
                patientDO.setGender("Female");
                patientDO.setAge("32");
                patientDO.setMobile("987879890" + i);
                patientDO.setAddress("Bangalore");
                patientDO.setBloodGroup("O+");

            } else {
                patientDO.setGender("Male");
                patientDO.setMobile("984" + i + "48803" + i);
                patientDO.setAddress("Hyderabad");
                patientDO.setBloodGroup("B+");
            }
            if (i == 0) {
                patientDO.setPrescription("Take 2 capsules twice a day");
            } else if (i == 1)
                patientDO.setPrescription("Paracetmole Take 2 capsules twice a day, \n 1X after lunch, 1x after dinner");
            else
                patientDO.setPrescription("");
            list.add(patientDO);
        }
    }

    public void loadRecyclerViewData(final ArrayList<PatientDO> list) {
        InpatientAdapter adapter = new InpatientAdapter(this, list, getAssets(), new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent in = new Intent(InpatientList.this, InpatientPatientDetails.class);
                in.putExtra("PatientDetails", list.get(position));
                startActivity(in);
            }
        });
        rv_ImpPateientList.setAdapter(adapter);
    }
}
