package com.pronix.hms.renderer;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pronix.hms.Prescription;
import com.pronix.hms.R;
import com.pronix.hms.common.Utils;
import com.pronix.hms.models.PrescriptionsDO;

import java.util.List;

public class PrescriptioinsAdapter extends RecyclerView.Adapter<PrescriptioinsAdapter.MyViewHolder> {

    List<PrescriptionsDO> list;
    Context context;

    public PrescriptioinsAdapter(Context context, List<PrescriptionsDO> list) {
        this.list = list;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_Name, tv_Prescription, tv_BookingDate;

        public MyViewHolder(View view) {
            super(view);
            tv_Name = (TextView) itemView.findViewById(R.id.tv_PrcDoctorName);
            tv_Prescription = (TextView) itemView.findViewById(R.id.tv_Prescription);
            tv_BookingDate = (TextView) itemView.findViewById(R.id.tv_BookingDate);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prescription_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PrescriptionsDO prescription = list.get(position);
        holder.tv_Name.setText(prescription.getFullName());
        holder.tv_Prescription.setText(prescription.getPrescription());
        holder.tv_BookingDate.setText((position == 0) ? "12-04-2108" : "05-04-2108");
        Typeface custom = Utils.getFontStyle(context.getAssets());
        holder.tv_Name.setTypeface(custom);
        holder.tv_Prescription.setTypeface(custom);
        holder.tv_BookingDate.setTypeface(custom);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
