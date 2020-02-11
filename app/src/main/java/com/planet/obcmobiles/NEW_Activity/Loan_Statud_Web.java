package com.planet.obcmobiles.NEW_Activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.planet.obcmobiles.Main3Activity;
import com.planet.obcmobiles.R;
import com.planet.obcmobiles.Utility;

/**
 * Created by Admin on 2/16/2018.
 */

public class Loan_Statud_Web extends AppCompatActivity {

    private WebView webview;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_loan_web);

        webview = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        webview.setWebViewClient(new myWebClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(false);
        webview.getSettings().setSupportZoom(true);
        webview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webview.requestFocusFromTouch();
        webview.setWebChromeClient(new MyWebChromeClient());
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


      //  webview.loadUrl("http://121.241.255.225/obcnew/site/getolsleaddetails_mobile.aspx");
        webview.loadUrl(Utility.URL_OLS_DETAILS);
    }
    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            progressBar.setVisibility(View.GONE);
        }
    }

    // To handle "Back" key press event for WebView to go back to previous screen.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
            new AlertDialog.Builder(Loan_Statud_Web.this)
                    .setTitle("")
                    .setMessage(message)
                    .setPositiveButton("ok",
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do your stuff
                                    result.confirm();
                                }
                            }).setCancelable(false).create().show();
            return true;
        }
        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   final JsResult result) {
            new AlertDialog.Builder(Loan_Statud_Web.this)
                    .setTitle("")
                    .setMessage(message)
                    .setPositiveButton("ok",
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog,	int which) {
                                    // do your stuff
                                    result.confirm();
                                }
                            }).setCancelable(false).create().show();
            return true;
        }
        @Override
        public boolean onJsPrompt(WebView view, String url, String message,
                                  String defaultValue, final JsPromptResult result) {
            new AlertDialog.Builder(Loan_Statud_Web.this)
                    .setTitle("Alert!")
                    .setMessage(message)
                    .setPositiveButton("ok",
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog,	int which) {
                                    // do your stuff
                                    result.confirm();
                                }

                            }).setCancelable(false).create().show();
            return true;
        }
    }




}
