package com.pronix.hms;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;

import it.sephiroth.android.library.easing.Back;
import it.sephiroth.android.library.easing.EasingManager;

/**
 * Created by ravi on 1/25/2018.
 */

public class LoginActivity extends Activity{
    CardView singupButton = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        singupButton = (CardView) findViewById(R.id.singup_button);
        singupButton.setVisibility(View.VISIBLE);
        showSingupButton();
    }

    private void showSingupButton() {



        singupButton.setVisibility(View.VISIBLE);
        singupButton.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        findViewById(R.id.singup_container).setVisibility(View.VISIBLE);

        final float scale = getResources().getDisplayMetrics().density;
        final int curr_singup_margin = -singupButton.getWidth();
        final int target_singup_margin = (int) (-35 * scale + 0.5f);

        EasingManager manager = new EasingManager(new EasingManager.EasingCallback() {

            @Override
            public void onEasingValueChanged(double value, double oldValue) {
                int diff_margin = curr_singup_margin - target_singup_margin;
                int margin = target_singup_margin + (int) (diff_margin - (diff_margin * value));

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) singupButton.getLayoutParams();
                layoutParams.setMargins(0, 0, margin, 0);
                singupButton.requestLayout();


            }

            @Override
            public void onEasingStarted(double value) {
                int diff_margin = curr_singup_margin - target_singup_margin;
                int margin = target_singup_margin + (int) (diff_margin - (diff_margin * value));

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) singupButton.getLayoutParams();
                layoutParams.setMargins(0, 0, margin, 0);
                singupButton.requestLayout();

            }

            @Override
            public void onEasingFinished(double value) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) singupButton.getLayoutParams();
                layoutParams.setMargins(0, 0, target_singup_margin, 0);
                singupButton.requestLayout();

            }
        });

        manager.start(Back.class, EasingManager.EaseType.EaseInOut-, 0, 1, 2600);
    }
}
