package com.mmbadi.sportsbuzz.utills;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

public class Utills {


    public AlertDialog createAlertDialog(Context context, String title, CharSequence message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        try {

            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });


        } catch (Exception e) {
        }

        return alertDialog;
    }
}
