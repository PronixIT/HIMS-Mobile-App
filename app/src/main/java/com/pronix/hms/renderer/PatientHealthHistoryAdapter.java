package com.pronix.hms.renderer;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pronix.hms.R;
import com.pronix.hms.common.Constants;
import com.pronix.hms.common.Utils;
import com.pronix.hms.models.PrescriptionsDO;

import java.util.ArrayList;
import java.util.List;

public class PatientHealthHistoryAdapter extends RecyclerView.Adapter<PatientHealthHistoryAdapter.MyViewHolder> {

    List<PrescriptionsDO> list;
    Context context;

    public PatientHealthHistoryAdapter(Context context, ArrayList<PrescriptionsDO> list)
    {
        this.list = list;
        this.context = context;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_Name, tv_Prescription, tv_BookingDate, tv_Hospital;

        public MyViewHolder(View view) {
            super(view);
            tv_Name = (TextView) itemView.findViewById(R.id.tv_PrcDoctorName);
            tv_Prescription = (TextView) itemView.findViewById(R.id.tv_Prescription);
            tv_BookingDate = (TextView) itemView.findViewById(R.id.tv_BookingDate);
            tv_Hospital = (TextView) itemView.findViewById(R.id.tv_Hospital);

        }
    }
    @Override
    public PatientHealthHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patienthistory_view, parent, false);
        return new PatientHealthHistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PatientHealthHistoryAdapter.MyViewHolder holder, int position) {
        PrescriptionsDO prescription = list.get(position);
        holder.tv_Name.setText(prescription.getFullName());
        holder.tv_Prescription.setText(prescription.getPrescription());
        holder.tv_BookingDate.setText(prescription.getDate());
        holder.tv_Hospital.setText(prescription.getHospitalName());
        Typeface custom = Utils.getFontStyle(context.getAssets());
        holder.tv_Name.setTypeface(custom);
        holder.tv_Prescription.setTypeface(custom);
        holder.tv_BookingDate.setTypeface(custom);
        holder.tv_Hospital.setTypeface(custom);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
