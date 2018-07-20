package com.pronix.hms;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by ravi on 4/2/2018.
 */

public class MedicalHistoryActivity extends Activity implements View.OnClickListener {
    CardView cv_List, cv_Expand;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicalhistory);
        initializeControls();
    }

    public void initializeControls()
    {
//        cv_List = (CardView) findViewById(R.id.cv_Card);
//        cv_List.setOnClickListener(this);
//        cv_Expand = (CardView) findViewById(R.id.cv_Expand);
    }

    @Override
    public void onClick(View view) {
        TranslateAnimation anim = null;
        switch (view.getId()) {
//            case R.id.cv_Card:
//                if(cv_List.getVisibility() == View.GONE)
//                {
//                    cv_Expand.setVisibility(View.VISIBLE);
//                    anim = new TranslateAnimation(0.0f, 0.0f, cv_Expand.getHeight(), 0.0f);
//                }
//                else
//                {
//                    anim = new TranslateAnimation(0.0f, 0.0f, 0.0f, cv_Expand.getHeight());
//                    anim.setAnimationListener(collapseListener);
//                }
//                anim.setDuration(300);
//                anim.setInterpolator(new AccelerateInterpolator(1.0f));
//                cv_Expand.startAnimation(anim);
//                break;
//        }
        }
    }

    Animation.AnimationListener collapseListener = new Animation.AnimationListener() {
        public void onAnimationEnd(Animation animation) {
            cv_Expand.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }
    };
}
