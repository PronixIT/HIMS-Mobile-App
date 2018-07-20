package com.pronix.hms;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pronix.hms.R;
import com.pronix.hms.common.Constants;
import com.pronix.hms.common.Utils;
import com.pronix.hms.models.PatientDO;
import com.pronix.hms.models.PrescriptionsDO;
import com.pronix.hms.models.WebServiceDO;
import com.pronix.hms.renderer.PatientHealthHistoryAdapter;
import com.pronix.hms.services.AsyncTask;
import com.pronix.hms.services.OnTaskCompleted;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MedicalHistoryFragment extends Fragment implements OnTaskCompleted {

    RecyclerView rv_MedicalHistory;
    Dialog dialog;
    String patientId;
    String[] names = {"Dr.Kumar Vamsi", "Dr. krishna Prasad", "Dr. Mohan Ranganath"};
    String[] hosptals = {"Appolo", "Yashoda", "Yashoda"};
    String[] pre = {"Amoxicillin standard 50 mg 1hr \n\nAmpicillin 50mg 1/2"
            , "Clindamycin 600mg po 1 hr befor \n\ncifadroxil 2g po"
            , "Metronidazol 250 - 500 mg 8hr \n\nPenicilin G 0.3-1.2 million 50-100% dose \n\nAmoxicillin  500mg 8 ph"};
    ArrayList<PrescriptionsDO> arrayList = new ArrayList<>();

    public MedicalHistoryFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_medicalhistory, container, false);
        patientId = getArguments().getString("PatientId");
        applyFontStyle((ViewGroup) getActivity().getWindow().getDecorView());
        rv_MedicalHistory = (RecyclerView) view.findViewById(R.id.rv_MedicalHistory);
//        calWebserviceMedicalHistory();
        getPrescriptionData();
        loadRecylerViewData(arrayList);

        // Inflate the layout for this fragment
        return view;
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
            Typeface custom = Utils.getFontStyle(getActivity().getAssets());
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
            progressDialog(getActivity());
            WebServiceDO webServiceDO = new WebServiceDO();
            JSONObject jsonObject = new JSONObject();
            webServiceDO.result = Constants.SENT;
            webServiceDO.request = "";
            new AsyncTask(getContext(), new OnTaskCompleted() {
                @Override
                public void onTaskCompletes(WebServiceDO webServiceDO) {
                    if(webServiceDO.result.equals(Constants.SUCCESS))
                    {
                        Type listType = new TypeToken<List<PrescriptionsDO>>() {
                        }.getType();
                        ArrayList<PrescriptionsDO> list = new Gson().fromJson(webServiceDO.responseContent, listType);
                        loadRecylerViewData(list);
                        Utils.hideProgress(dialog);
                    }
                    else
                    {
                        Utils.hideProgress(dialog);
                        Toast.makeText(getContext(), "Failed to refresh", Toast.LENGTH_SHORT).show();
                    }

                }
            },Constants.URLBase + "" + Constants.REQUEST_PATIENTHISTORY + "/" + patientId, "GET", null).execute(webServiceDO);
        } catch (Exception e) {
            e.getMessage();
            Utils.hideProgress(dialog);
        }
    }

    public void loadRecylerViewData(ArrayList<PrescriptionsDO> list)
    {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        rv_MedicalHistory.setLayoutManager(mLayoutManager);
        rv_MedicalHistory.setItemAnimator(new DefaultItemAnimator());
        PatientHealthHistoryAdapter adapter = new PatientHealthHistoryAdapter(getContext(), list);
        rv_MedicalHistory.setAdapter(adapter);
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

        }
        catch (Exception e)
        {
            e.getMessage();
        }

    }
}
