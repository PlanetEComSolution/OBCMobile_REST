package com.planet.obcmobiles;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.Handler;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Complaint_webview extends AppCompatActivity {
    SweetAlertDialog alertDialog;
    android.os.Handler mHandler;
    boolean timeout = true;
    WebView wv1;
    String url =Utility.URL_APP_COMPLAINT;// "http://121.241.255.225/obcnew/site/App_Complain_Online.aspx";
    // "https://www.obcindia.co.in/obcnew/site/App_Housing_Loan.aspx";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        wv1 = (WebView) findViewById(R.id.webview);
        alertDialog = new SweetAlertDialog(Complaint_webview.this, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Please Wait...");
        alertDialog.show();
        webview_method();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (wv1.canGoBack() == true) {
                        wv1.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    void webview_method() {
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.getSettings().setBuiltInZoomControls(false);
        wv1.getSettings().setSupportZoom(false);
        wv1.getSettings().setLoadWithOverviewMode(true);
        wv1.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        wv1.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);

         wv1.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);
        WebSettings webSettings = wv1.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        wv1.requestFocusFromTouch();

        wv1.setWebViewClient(new WebViewClient());
        wv1.setWebChromeClient(new WebChromeClient());
        wv1.loadUrl(url);
        alertDialog.dismiss();

        //   wv1.
      //  wv1.loadUrl(url);
     /*   wv1.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                timeout = false;

            }

        });*/

    }
}


