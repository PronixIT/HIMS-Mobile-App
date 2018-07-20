package com.pronix.hms;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pronix.hms.common.Constants;
import com.pronix.hms.common.CustomItemClickListener;
import com.pronix.hms.common.Utils;
import com.pronix.hms.models.PatientDO;
import com.pronix.hms.models.WebServiceDO;
import com.pronix.hms.renderer.PatientAdapter;
import com.pronix.hms.services.AsyncTask;
import com.pronix.hms.services.OnTaskCompleted;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ImpCasesActivity extends AppCompatActivity implements OnTaskCompleted {
    RecyclerView rv_ImpPateientList;
    Dialog dialog;
    Toolbar toolbar;
    String[] value = {"Varun","Latha", "Suma"};
    ArrayList<PatientDO> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impcases);
        setupWindowAnimations();
        initializeControls();
//        callProfileWebServices();
        loadValues();
        loadRecyclerViewData(list);
    }

    private void setupWindowAnimations() {
//        Fade fade = new Fade();
//        fade.setDuration(1000);
//        getWindow().setEnterTransition(fade);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(500);
            getWindow().setEnterTransition(fade);
        }
    }

    public void initializeControls() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Important Cases");

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
            patientDO.setEmail(value[i]+"@gmail.com");
            patientDO.setAddress("Hyderabad");
            patientDO.setPatientId("PT0000000"+i);
            patientDO.setAppointmentId("APT0000000"+i);
            if (value[i].equals("Latha") || value[i].equals("Suma")) {
                patientDO.setGender("Female");
                patientDO.setAge("32");
                patientDO.setMobile("987879890"+i);
                patientDO.setAddress("Bangalore");
                patientDO.setBloodGroup("O+");

            } else {
                patientDO.setGender("Male");
                patientDO.setMobile("984" + i + "48803" + i);
                patientDO.setAddress("Hyderabad");
                patientDO.setBloodGroup("B+");
            }
            if(i == 0)
            {
                patientDO.setPrescription("Take 2 capsules twice a day");
            }
            else if(i == 1)
                patientDO.setPrescription("Paracetmole Take 2 capsules twice a day, \n 1X after lunch, 1x after dinner");
            else
                patientDO.setPrescription("");
            list.add(patientDO);
        }
    }

    public void callProfileWebServices() {
        try {
            progressDialog(this);
            WebServiceDO webServiceDO = new WebServiceDO();
            JSONObject jsonObject = new JSONObject();
            webServiceDO.result = Constants.SENT;
            webServiceDO.request = "DETAILS";
            new AsyncTask(ImpCasesActivity.this, ImpCasesActivity.this, Constants.URLBase + "" + Constants.REQUEST_PATIENTLIST + "/" + Constants.userDetails.userId, "GET", null).execute(webServiceDO);
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

                Type listType = new TypeToken<List<PatientDO>>() {
                }.getType();
                ArrayList<PatientDO> list = new Gson().fromJson(webServiceDO.responseContent, listType);
                loadRecyclerViewData(list);
            } else {
                Utils.showalert(this, "Alert", "Error " + webServiceDO.responseContent);
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void loadRecyclerViewData(final ArrayList<PatientDO> list) {
        PatientAdapter adapter = new PatientAdapter(true, list, getAssets(), new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                ImageView iv_Image = v.findViewById(R.id.iv_Img);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) ImpCasesActivity.this, iv_Image, ImpCasesActivity.this.getString(R.string.picture_transition_name));
                    Intent in = new Intent();
                    in.setClass(ImpCasesActivity.this, PatientDetails.class);
                    int pos = rv_ImpPateientList.getChildLayoutPosition(v);
                    in.putExtra("Name", list.get(pos).getName());
                    in.putExtra("Gender", list.get(pos).getGender());
                    in.putExtra("PatientDetails", list.get(pos));
                    startActivity(in, options.toBundle());
                } else {
                    Intent in = new Intent();
                    in.setClass(ImpCasesActivity.this, PatientDetails.class);
                    int pos = rv_ImpPateientList.getChildLayoutPosition(v);
                    in.putExtra("Name", list.get(pos).getName());
                    in.putExtra("Gender", list.get(pos).getGender());
                    in.putExtra("PatientDetails", list.get(pos));
                    startActivity(in);
                }
//
            }
        });
        rv_ImpPateientList.setAdapter(adapter);
    }
}
