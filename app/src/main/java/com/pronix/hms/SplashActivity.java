package com.pronix.hms;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.w3c.dom.Text;

/**
 * Created by ravi on 1/29/2018.
 */

public class SplashActivity extends Activity {

    ShimmerFrameLayout shimmerFrameLayout;
    TextView tv_Header, tv_HeaderParagraph;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container1);
        shimmerFrameLayout.startShimmerAnimation();
        tv_Header = (TextView) findViewById(R.id.tv_Header);
        tv_HeaderParagraph = (TextView) findViewById(R.id.tv_HeaderPara);
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/angelina.TTF");
        Typeface lobsterFont = Typeface.createFromAsset(getAssets(), "fonts/opensansregular.ttf");
        tv_HeaderParagraph.setTypeface(customFont);
        tv_Header.setTypeface(lobsterFont);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent();
                in.setClass(SplashActivity.this, LoginActivity.class);
                startActivity(in);
                finish();

            }
        },5000);
    }
}
