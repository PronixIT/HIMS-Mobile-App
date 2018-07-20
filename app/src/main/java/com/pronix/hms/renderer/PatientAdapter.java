package com.pronix.hms.renderer;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pronix.hms.models.PatientDO;
import com.pronix.hms.R;
import com.pronix.hms.common.CustomItemClickListener;
import com.pronix.hms.common.Utils;

import java.util.List;

/**
 * Created by ravi on 1/30/2018.
 */

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.MyViewHolder> {
    private List<PatientDO> patientList;
    Typeface customFont;
    AssetManager assetManager;
    CustomItemClickListener listener;
    boolean isImpPatients = false;

    public PatientAdapter(boolean isImpPatients, List<PatientDO> list, AssetManager assetManager, CustomItemClickListener listener) {
        this.patientList = list;
        this.assetManager = assetManager;
        this.listener = listener;
        this.isImpPatients = isImpPatients;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, gender, age, time;
        LinearLayout ll_Time;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_Name);
            gender = (TextView) view.findViewById(R.id.tv_Gender);
            age = (TextView) view.findViewById(R.id.tv_Age);
            time = (TextView) view.findViewById(R.id.tv_Time);
            customFont = Utils.getFontStyle(assetManager);
            ll_Time = (LinearLayout) view.findViewById(R.id.ll_Time);
            CardView cv = (CardView) view.findViewById(R.id.cv);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v,getAdapterPosition());
                }
            });

        }
    }

    @Override
    public PatientAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_view, parent, false);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.onItemClick(view, 1);
//
//            }
//        });

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PatientAdapter.MyViewHolder holder, int position) {
        PatientDO patientDO = patientList.get(position);
        holder.name.setText(patientDO.getFullName());
        holder.gender.setText(patientDO.getGender());
        holder.age.setText("Age: " + patientDO.getAge());
        holder.time.setText("Appointment :" + " 2pm - 3pm");
        holder.name.setTypeface(customFont);
        holder.gender.setTypeface(customFont);
        holder.age.setTypeface(customFont);
        holder.time.setTypeface(customFont);
        holder.ll_Time.setVisibility(isImpPatients ? View.GONE : View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }
}
