package com.pronix.hms.renderer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pronix.hms.InpatientPatientDetails;
import com.pronix.hms.R;
import com.pronix.hms.common.CustomItemClickListener;
import com.pronix.hms.common.Utils;
import com.pronix.hms.models.PatientDO;

import java.util.List;

public class InpatientAdapter extends RecyclerView.Adapter<InpatientAdapter.MyViewHolder> {

    private List<PatientDO> patientList;
    Typeface customFont;
    AssetManager assetManager;
    CustomItemClickListener listener;
    Context context;


    public InpatientAdapter(Context context, List<PatientDO> list, AssetManager assetManager, CustomItemClickListener listener) {
        this.patientList = list;
        this.assetManager = assetManager;
        this.listener = listener;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, gender, age, tv_Status;
        Button but_Start;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_Name);
            gender = (TextView) view.findViewById(R.id.tv_Gender);
            age = (TextView) view.findViewById(R.id.tv_Age);

            customFont = Utils.getFontStyle(assetManager);
            CardView cv = (CardView) view.findViewById(R.id.cv);
            but_Start = (Button) view.findViewById(R.id.but_Start);
            tv_Status = (TextView) view.findViewById(R.id.tv_Status);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, getAdapterPosition());
                }
            });

        }
    }

    @NonNull
    @Override
    public InpatientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inpatient_adapterview, parent, false);


        return new InpatientAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InpatientAdapter.MyViewHolder holder, int position) {
        final PatientDO patientDO = patientList.get(position);
        holder.name.setText(patientDO.getFullName());
        holder.gender.setText(patientDO.getGender());
        holder.age.setText("Age: " + patientDO.getAge());
        holder.name.setTypeface(customFont);
        holder.gender.setTypeface(customFont);
        holder.age.setTypeface(customFont);
        holder.tv_Status.setText("");
        if(position == 0)
        {
            holder.tv_Status.setText("Discharged \n25/05/2018");
            holder.tv_Status.setVisibility(View.VISIBLE);
        }
        if(position == 1) {
            holder.but_Start.setVisibility(View.VISIBLE);
            holder.tv_Status.setVisibility(View.GONE);
        }
        holder.but_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, InpatientPatientDetails.class);
                in.putExtra("PatientDetails", patientDO);
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }
}
