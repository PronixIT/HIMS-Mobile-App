package com.pronix.hms;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import android.transition.Explode;
import android.transition.Slide;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pronix.hms.models.PatientDO;
import com.pronix.hms.common.Constants;
import com.pronix.hms.common.CustomItemClickListener;
import com.pronix.hms.common.Utils;
import com.pronix.hms.models.WebServiceDO;
import com.pronix.hms.renderer.PatientAdapter;
import com.pronix.hms.services.AsyncTask;
import com.pronix.hms.services.OnTaskCompleted;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi on 1/29/2018.
 */

public class PatientList extends AppCompatActivity implements OnTaskCompleted {
    RecyclerView recyclerView;
    ArrayList<PatientDO> list = new ArrayList<>();
    Dialog dialog;
    SwipeRefreshLayout swipeRefreshLayout;
    String[] value = {"Varun","Latha", "Krisnh Mohan", "Suma"};
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientlist);
        applyFontStyle((ViewGroup) this.getWindow().getDecorView());
        setupWindowAnimations();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Patients");

        recyclerView = (RecyclerView) findViewById(R.id.recyleView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // cancel the Visual indication of a refresh

                WebServiceDO webServiceDO = new WebServiceDO();
                JSONObject jsonObject = new JSONObject();
                webServiceDO.result = Constants.SENT;
                webServiceDO.request = "DETAILS";
                new AsyncTask(PatientList.this, new OnTaskCompleted() {
                    @Override
                    public void onTaskCompletes(WebServiceDO webServiceDO) {
                        if(webServiceDO.result.equals(Constants.SUCCESS)) {
                            Type listType = new TypeToken<List<PatientDO>>() {
                            }.getType();
                            list = new Gson().fromJson(webServiceDO.responseContent, listType);
                            loadRecyclerViewData();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, Constants.URLBase + "" + Constants.REQUEST_PATIENTLIST + "/" + Constants.userDetails.userId, "GET", null).execute(webServiceDO);

            }
        });
//        callProfileWebServices();
        loadTemporaryValues();
    }

    public void loadRecyclerViewData() {
        PatientAdapter adapter = new PatientAdapter(false, list, getAssets(), new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                ImageView iv_Image = v.findViewById(R.id.iv_Img);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) PatientList.this, iv_Image, PatientList.this.getString(R.string.picture_transition_name));
                    Intent in = new Intent();
                    in.setClass(PatientList.this, PatientDetails.class);
                    int pos = recyclerView.getChildLayoutPosition(v);
                    in.putExtra("Name", list.get(pos).getName());
                    in.putExtra("Gender", list.get(pos).getGender());
                    in.putExtra("PatientDetails", list.get(pos));
                    startActivity(in, options.toBundle());
                } else {
                    Intent in = new Intent();
                    in.setClass(PatientList.this, PatientDetails.class);
                    int pos = recyclerView.getChildLayoutPosition(v);
                    in.putExtra("Name", list.get(pos).getName());
                    in.putExtra("Gender", list.get(pos).getGender());
                    in.putExtra("PatientDetails", list.get(pos));
                    startActivity(in);
                }

            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void setupWindowAnimations() {
//        Fade fade = new Fade();
//        fade.setDuration(1000);
//        getWindow().setEnterTransition(fade);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            slide.setDuration(500);
            getWindow().setEnterTransition(new Explode());
        }
    }

    private Explode makeExplodeTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(3000);
            explode.setInterpolator(new AnticipateOvershootInterpolator());
            return explode;
        }
        return null;
    }

    public void loadTemporaryValues()
    {
        loadValues();
        loadRecyclerViewData();
    }

    public void loadValues() {
        PatientDO patientDO;
        for (int i = 0; i < 4; i++) {
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
            new AsyncTask(PatientList.this, PatientList.this, Constants.URLBase + "" + Constants.REQUEST_PATIENTLIST + "/" + Constants.userDetails.userId, "GET", null).execute(webServiceDO);
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
            if(webServiceDO.result.equals(Constants.SUCCESS)) {

                Type listType = new TypeToken<List<PatientDO>>() {
                }.getType();
                list = new Gson().fromJson(webServiceDO.responseContent, listType);
                loadRecyclerViewData();
            }
            else {
                Utils.showalert(this, "Alert", "Error " + webServiceDO.responseContent);
            }
        } catch (Exception e) {
            e.getMessage();
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

//    public void onClick(View clickedView) {
//        // save rect of view in screen coordinates
//        final Rect viewRect = new Rect();
//        clickedView.getGlobalVisibleRect(viewRect);
//
//        // create Explode transition with epicenter
//        Transition explode = new Explode()
//                .setEpicenterCallback(new Transition.EpicenterCallback() {
//                    @Override
//                    public Rect onGetEpicenter(Transition transition) {
//                        return viewRect;
//                    }
//                });
//        explode.setDuration(1000);
//        TransitionManager.beginDelayedTransition(recyclerView, explode);
//
//        // remove all views from Recycler View
//        recyclerView.setAdapter(null);
//    }
}
