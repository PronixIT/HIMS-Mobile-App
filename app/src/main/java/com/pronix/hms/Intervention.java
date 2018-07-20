package com.pronix.hms;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pronix.hms.common.Constants;
import com.pronix.hms.common.Utils;
import com.pronix.hms.models.PatientDO;
import com.pronix.hms.models.PrescriptionsDO;
import com.pronix.hms.models.WebServiceDO;
import com.pronix.hms.renderer.PatientHealthHistoryAdapter;
import com.pronix.hms.renderer.PrescriptioinsAdapter;
import com.pronix.hms.services.AsyncTask;
import com.pronix.hms.services.OnTaskCompleted;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Intervention extends AppCompatActivity implements OnTaskCompleted {
    RecyclerView rv_Prescriptions;
    ArrayList<PrescriptionsDO> arrayList = new ArrayList<>();
    Dialog dialog;
    String patientId = "";
    Toolbar toolbar;
    SwipeRefreshLayout swipeRefreshLayout;
    String[] names = {"Dr.Kumar Vamsi", "Dr. krishna Prasad", "Dr. Mohan Ranganath"};
    String[] hosptals = {"Appolo", "Yashoda", "Yashoda"};
    String[] pre = {"Amoxicillin standard 50 mg 1hr \n\nAmpicillin 50mg 1/2"
            , "Clindamycin 600mg po 1 hr befor \n\ncifadroxil 2g po"
            , "Metronidazol 250 - 500 mg 8hr \n\nPenicilin G 0.3-1.2 million 50-100% dose \n\nAmoxicillin  500mg 8 ph"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_intervention);
        applyFontStyle((ViewGroup) this.getWindow().getDecorView());
        initializeControls();

//        getPrescriptionData();

//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        rv_Prescriptions.setLayoutManager(mLayoutManager);
//        rv_Prescriptions.setItemAnimator(new DefaultItemAnimator());
//
//        PrescriptioinsAdapter adapter = new PrescriptioinsAdapter(this, arrayList);
//        rv_Prescriptions.setAdapter(adapter);
//        calWebserviceMedicalHistory();
        getPrescriptionData();
        loadRecylerViewData(arrayList);
    }

    public void initializeControls()
    {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Interventions");

        rv_Prescriptions = (RecyclerView) findViewById(R.id.rv_Prescritions);
        patientId = getIntent().getStringExtra("PatientId");
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // cancel the Visual indication of a refresh
                try {
                    WebServiceDO webServiceDO = new WebServiceDO();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("doctorId", Constants.userDetails.userId);
                    jsonObject.put("patientId", patientId);
                    webServiceDO.result = Constants.SENT;
                    webServiceDO.request = "DETAILS";
                    new AsyncTask(Intervention.this, new OnTaskCompleted() {
                        @Override
                        public void onTaskCompletes(WebServiceDO webServiceDO) {
                            if(webServiceDO.result.equals(Constants.SUCCESS)) {
                                Type listType = new TypeToken<List<PrescriptionsDO>>() {
                                }.getType();
                                ArrayList<PrescriptionsDO> list = new Gson().fromJson(webServiceDO.responseContent, listType);
                                loadRecylerViewData(list);
                                swipeRefreshLayout.setRefreshing(false);
                            }else
                            {
                                swipeRefreshLayout.setRefreshing(false);
                                Toast.makeText(Intervention.this, "Failed to refresh", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, Constants.URLBase + "" + Constants.REQUEST_PREVIOUSINTERVENTION, "POST", jsonObject.toString()).execute(webServiceDO);

                }
                catch (Exception e)
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

        });
    }

    public void getPrescriptionData() {
        PrescriptionsDO prescriptionsDO;

        for (int i = 0; i < 3; i++) {
            prescriptionsDO = new PrescriptionsDO();
            prescriptionsDO.setFullName(names[i]);
            prescriptionsDO.setPrescription(pre[i]);
            prescriptionsDO.setDate(i==0 ? "14-04-2018" : (i == 1 ? "21-04-2018" : "24-04-2018"));
            prescriptionsDO.setHospitalName(hosptals[i]);
            prescriptionsDO.setSpecialization("");
            prescriptionsDO.setTimings("");
            arrayList.add(prescriptionsDO);

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

    public void calWebserviceMedicalHistory()
    {
        try {
            progressDialog(Intervention.this);
            WebServiceDO webServiceDO = new WebServiceDO();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("doctorId",Constants.userDetails.userId);
            jsonObject.put("patientId",patientId);
            webServiceDO.result = Constants.SENT;
            webServiceDO.request = "";
            new AsyncTask(Intervention.this, Intervention.this,Constants.URLBase + "" + Constants.REQUEST_PREVIOUSINTERVENTION, "POST", jsonObject.toString()).execute(webServiceDO);
        } catch (Exception e) {
            e.getMessage();
            Utils.hideProgress(dialog);
        }
    }

    public void loadRecylerViewData(ArrayList<PrescriptionsDO> list)
    {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_Prescriptions.setLayoutManager(mLayoutManager);
        rv_Prescriptions.setItemAnimator(new DefaultItemAnimator());
        PatientHealthHistoryAdapter adapter = new PatientHealthHistoryAdapter(this, list);
        rv_Prescriptions.setAdapter(adapter);
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
                Type listType = new TypeToken<List<PrescriptionsDO>>() {
                }.getType();
                ArrayList<PrescriptionsDO> list = new Gson().fromJson(webServiceDO.responseContent, listType);
                loadRecylerViewData(list);
            } else {
                Utils.showalert(Intervention.this, "Alert", webServiceDO.responseContent);
            }
        }
        catch (Exception e)
        {
            Utils.hideProgress(dialog);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
