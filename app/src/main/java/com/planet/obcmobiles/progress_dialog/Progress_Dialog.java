package com.planet.obcmobiles.progress_dialog;

import android.app.ProgressDialog;
import android.content.Context;

public class Progress_Dialog {
    private static ProgressDialog progressDialog = null;

    public static void show_dialog(Context context) {
        try {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
            }
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        } catch (Exception e) {
            //Log.e("pDialog", e.getMessage());
        }
    }

    public static void hide_dialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
           // Log.e("pDialog", e.getMessage());
        }
    }

}
