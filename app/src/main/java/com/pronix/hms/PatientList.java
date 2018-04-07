package com.pronix.hms;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.animation.AnticipateOvershootInterpolator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pronix.hms.DO.PatientDO;
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

public class PatientList extends Activity implements OnTaskCompleted {
    RecyclerView recyclerView;
    ArrayList<PatientDO> list = new ArrayList<>();
    Dialog dialog;
    String[] value = {"Varun","Ramesh", "Latha", "Mohan", "Suma"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientlist);
        setupWindowAnimations();
        recyclerView = (RecyclerView) findViewById(R.id.recyleView);
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/opensansregular.ttf");
//        PatientAdapter adapter = new PatientAdapter(list, getAssets(), new CustomItemClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//                Intent in = new Intent();
//                in.setClass(PatientList.this, PatientDetails.class);
//                int pos = recyclerView.getChildLayoutPosition(v);
//                in.putExtra("Name", list.get(position).getName());
//                in.putExtra("Gender", list.get(position).getGender());
//                in.putExtra("PatientDetails", list.get(position));
//                startActivity(in);
//
//            }
//        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapter);
//        loadValues();

        callProfileWebServices();
    }

    private void setupWindowAnimations() {
//        Fade fade = new Fade();
//        fade.setDuration(1000);
//        getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        slide.setDuration(500);
        getWindow().setEnterTransition(new Explode());
    }

    private Explode makeExplodeTransition(){
        Explode explode = new Explode();
        explode.setDuration(3000);
        explode.setInterpolator(new AnticipateOvershootInterpolator());
        return explode;
    }

    public void loadValues() {
        PatientDO patientDO;
        for (int i = 0; i < 5; i++) {
            patientDO = new PatientDO();
            patientDO.setName(value[i]);
            patientDO.setAge("28");
            if(value[i].equals("Latha") || value[i].equals("Suma")) {
                patientDO.setGender("Female");
                patientDO.setAge("32");
            }
            else
                patientDO.setGender("Male");
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
        try
        {
            Utils.hideProgress(dialog);
            Type listType = new TypeToken<List<PatientDO>>() {
            }.getType();
            list = new Gson().fromJson(webServiceDO.responseContent, listType);
            PatientAdapter adapter = new PatientAdapter(list, getAssets(), new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent in = new Intent();
                    in.setClass(PatientList.this, PatientDetails.class);
                    int pos = recyclerView.getChildLayoutPosition(v);
                    in.putExtra("Name", list.get(pos).getName());
                    in.putExtra("Gender", list.get(pos).getGender());
                    in.putExtra("PatientDetails", list.get(pos));
                    startActivity(in);

                }
            });
            recyclerView.setAdapter(adapter);
        }
        catch (Exception e)
        {
            e.getMessage();
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
