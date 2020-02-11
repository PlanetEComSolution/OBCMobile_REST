package com.planet.obcmobiles;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.planet.obcmobiles.NEW_Activity.Expand_Loan_Activity;
import com.planet.obcmobiles.NEW_Activity.Recovery_and_Low_Activity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Main3Activity extends AppCompatActivity {
    WebView wv1;
    boolean timeout = true;
    int Position;
    SweetAlertDialog alertDialog;
    ProgressBar progressBar;
    String url1 =Utility.URL_App_Housing_Loan;// "http://121.241.255.225/obcnew/site/App_Housing_Loan.aspx";
    String url2 =Utility.URL_App_Vehicle_Loan;// "http://121.241.255.225/obcnew/site/App_Vehicle_Loan.aspx";
    String url3 =Utility.URL_App_otherretailloan_Loans;// "http://121.241.255.225/obcnew/site/App_otherretailloan_Loans.aspx";
    String url4 =Utility.URL_App_Educations_Loan;// "http://121.241.255.225/obcnew/site/App_Educations_Loan.aspx";
    String url5 = Utility.URL_App_MSME_Loan;//"http://121.241.255.225/obcnew/site/App_MSME_Loan.aspx";



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        wv1= (WebView) findViewById(R.id.webview);
        ImageView back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        Intent intent = getIntent();
        String name = intent.getStringExtra("SELECTED_LOAN_NAME");
        Position = intent.getIntExtra("pos", 0);
        alertDialog = new SweetAlertDialog(Main3Activity.this, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Please Wait...");
        alertDialog.show();
        web_url(Position);

        try {
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                Window window = Main3Activity.this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Main3Activity.this.getResources().getColor(R.color.Green_primary));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    void webview_method(String URL) {

//        wv1.getSettings().setLoadsImagesAutomatically(true);
//        wv1.getSettings().setJavaScriptEnabled(true);
//        wv1.getSettings().setBuiltInZoomControls(false);
//        wv1.getSettings().setSupportZoom(false);
//        wv1.getSettings().setLoadWithOverviewMode(true);
//        wv1.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        wv1.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
//        wv1.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);
//        WebSettings webSettings = wv1.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setBuiltInZoomControls(true);
//        wv1.requestFocusFromTouch();
//        wv1.setWebViewClient(new WebViewClient());
//        wv1.setWebChromeClient(new WebChromeClient());
//        wv1.loadUrl(URL);
        alertDialog.dismiss();

        //progressBar.setVisibility(View.GONE);
       wv1.getSettings().setJavaScriptEnabled(true);
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setBuiltInZoomControls(false);
        wv1.getSettings().setSupportZoom(false);
        wv1.getSettings().setLoadWithOverviewMode(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);
        wv1.setWebChromeClient(new MyWebChromeClient());
        wv1.loadUrl(URL);
        wv1.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                timeout = false;
                progressBar.setVisibility(View.GONE);
                alertDialog.dismiss();
            }

        });
    }


    public void web_url(int position){
        if (position == 1) {
            webview_method(url1);
        }
        if (position == 2) {
            webview_method(url2);
        }
        if (position == 3) {
            webview_method(url4);
        }
        if (position == 4) {
            webview_method(url3);
        }
        if (position == 5) {
            webview_method(url5);
        } else {

        }
    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
            progressBar.setVisibility(View.VISIBLE);
            new AlertDialog.Builder(Main3Activity.this)
                    .setTitle("")
                    .setMessage(message)

                    .setPositiveButton("ok",
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do your stuff
                                    result.confirm();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }).setCancelable(false).create().show();
            return true;
        }
        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   final JsResult result) {
            new AlertDialog.Builder(Main3Activity.this)
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
            new AlertDialog.Builder(Main3Activity.this)
                    .setTitle("Alert !")
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
