package com.pronix.hms;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.CardView;
import android.transition.Fade;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.TextView;

import com.pronix.hms.common.Constants;
import com.pronix.hms.common.Utils;

/**
 * Created by ravi on 1/29/2018.
 */

public class MenuActiivty extends Activity implements View.OnClickListener {
    TextView tv_appointment, tv_financial, tv_ImpCases, tv_Profile, tv_DoctorName;
    CardView cv_Patient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setupWindowAnimations();
        initializeControls();
    }

    public void initializeControls() {
        tv_appointment = (TextView) findViewById(R.id.tv_Appointments);
        tv_financial = (TextView) findViewById(R.id.tv_Financials);
        tv_ImpCases = (TextView) findViewById(R.id.tv_ImpCases);
        tv_Profile = (TextView) findViewById(R.id.tv_Profile);
        cv_Patient = (CardView) findViewById(R.id.cv_Patient);
        Typeface customFont = Utils.getFontStyle(getAssets());
//                Typeface.createFromAsset(getAssets(), "fonts/opensansregular.ttf");
        tv_DoctorName = (TextView) findViewById(R.id.tv_DoctorName);
        tv_DoctorName.setTypeface(customFont);
        tv_DoctorName.setText("Doctor : " + Constants.userDetails.name);

        tv_financial.setTypeface(customFont);
        tv_appointment.setTypeface(customFont);
        tv_ImpCases.setTypeface(customFont);
        tv_Profile.setTypeface(customFont);
        Typeface robotoFont = Typeface.createFromAsset(getAssets(), "fonts/robotomedium.ttf");
    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(1000);
            getWindow().setEnterTransition(fade);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_Profile:
                Intent in1 = new Intent(this, ProfileActivity.class);
                startActivity(in1);
                break;
            case R.id.cv_Patient:
                Intent in = new Intent(this, Appointments.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(in,
                            ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                } else {
                    startActivity(in);
                }
                break;

            case R.id.cv_ImpCases:
                Intent impPatientIntent = new Intent(this, ImpCasesActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(impPatientIntent,
                            ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                } else {
                    startActivity(impPatientIntent);
                }
                break;
//            case R.id.ll_ImpCases:
//                TranslateAnimation anim = null;
//
//                if(((PercentRelativeLayout) findViewById(R.id.rl_Layout)).getVisibility() == View.GONE)
//                {
//                    ((PercentRelativeLayout) findViewById(R.id.rl_Layout)).setVisibility(View.VISIBLE);
//                    anim = new TranslateAnimation(0.0f, 0.0f, ((PercentRelativeLayout) findViewById(R.id.rl_Layout)).getHeight(), 0.0f);
//                }
//                else
//                {
//                    anim = new TranslateAnimation(0.0f, 0.0f, 0.0f, ((PercentRelativeLayout) findViewById(R.id.rl_Layout)).getHeight());
//                    anim.setAnimationListener(collapseListener);
//                }
//                anim.setDuration(300);
//                anim.setInterpolator(new AccelerateInterpolator(1.0f));
//                ((PercentRelativeLayout) findViewById(R.id.rl_Layout)).startAnimation(anim);
//                break;

            case R.id.cv_Financial:
                break;
        }

    }

    Animation.AnimationListener collapseListener = new Animation.AnimationListener() {
        public void onAnimationEnd(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }
    };

    public void run() {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        try {
                            // On complete call either onSignupSuccess or onSignupFailed
                            // depending on success
                            // onSignupFailed();
//                                TranslateAnimation anim = null;
//
//                                if(((PercentRelativeLayout) findViewById(R.id.rl_Layout)).getVisibility() == View.GONE)
//                                {
//                                    ((PercentRelativeLayout) findViewById(R.id.rl_Layout)).setVisibility(View.VISIBLE);
//                                    anim = new TranslateAnimation(0.0f, 0.0f, ((PercentRelativeLayout) findViewById(R.id.rl_Layout)).getHeight(), 0.0f);
//                                }
//                                else
//                                {
//                                    anim = new TranslateAnimation(0.0f, 0.0f, 0.0f, ((PercentRelativeLayout) findViewById(R.id.rl_Layout)).getHeight());
//                                    anim.setAnimationListener(collapseListener);
//                                }
//                                anim.setDuration(300);
//                                anim.setInterpolator(new AccelerateInterpolator(1.0f));
//                                ((PercentRelativeLayout) findViewById(R.id.rl_Layout)).startAnimation(anim);
                        } catch (Exception e) {

                        }
                    }
                }, 1000);
    }
}
