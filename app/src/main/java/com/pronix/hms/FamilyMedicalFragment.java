package com.pronix.hms;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pronix.hms.common.Utils;

public class FamilyMedicalFragment extends Fragment {

    public FamilyMedicalFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        applyFontStyle((ViewGroup) getActivity().getWindow().getDecorView());
        return inflater.inflate(R.layout.family_history, container, false);
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
            else if (view instanceof RadioButton)
                ((RadioButton) view).setTypeface(custom);
            else if (view instanceof ViewGroup)
                applyFontStyle((ViewGroup) view);


        }

    }


}
