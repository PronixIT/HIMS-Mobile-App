package com.pronix.hms.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.pronix.hms.BaseActivity;
import com.pronix.hms.MainActivity;
import com.pronix.hms.R;

import org.w3c.dom.Text;

/**
 * Created by ravi on 1/11/2018.
 */

public class Utils {

    public static void showalert(Context context, String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public static void hideProgress(android.app.AlertDialog progressBar)
    {
        if(progressBar.isShowing())
            progressBar.dismiss();

    }

    public static void hideProgress(Dialog dialog)
    {
        if(dialog.isShowing())
            dialog.dismiss();

    }

    public static String getQuotedString(String value)
    {
        return "'"+value+"'";
    }

    public static void showConfirmationAlert(final Context context, String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((BaseActivity) context).handleResponse("OK");
            }
        });
        builder.show();
    }

    public static void customeAlertDialog(Context context, String title, String message, boolean isCancelButton)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialog_view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView tv_Title = (TextView) dialog.findViewById(R.id.tv_Title);
        TextView tv_Message = (TextView) dialog.findViewById(R.id.tv_Message);
        Button but_Ok = (Button) dialog.findViewById(R.id.but_Ok);
        Button but_Cancel = (Button) dialog.findViewById(R.id.but_Cancel);
        tv_Title.setText(title);
        tv_Message.setText(message);
        but_Cancel.setVisibility(isCancelButton ? View.GONE : View.VISIBLE);
        but_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        but_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    public static String isNull(String value)
    {
        if(value == null)
        {
            return "";
        }
        return value;
    }

    public static Typeface getFontStyle(AssetManager assetManager)
    {
        return Typeface.createFromAsset(assetManager, "fonts/aller_rg.ttf");
    }


}
