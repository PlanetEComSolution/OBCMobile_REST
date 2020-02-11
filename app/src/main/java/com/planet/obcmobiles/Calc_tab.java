package com.planet.obcmobiles;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.PopupWindow;
import android.widget.TabHost;

public class Calc_tab extends TabActivity {
    boolean customTitleSupported;

    PopupWindow pw;
    public static Activity ctx_main;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_calc_tab);
        ctx_main=Calc_tab.this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        @SuppressWarnings("deprecation")
        TabHost tabHost = getTabHost();
        TabHost.TabSpec tabSpec;
        Intent complain = new Intent().setClass(getApplicationContext(), Interest.class);
        tabSpec = tabHost.newTabSpec("act").setIndicator("DEPOSIT").setContent(complain);
        complain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        tabHost.addTab(tabSpec);

        Intent Suggestion = new Intent().setClass(getApplicationContext(), LoanIntrest.class);
        tabSpec = tabHost.newTabSpec("Rule").setIndicator("LOANS").setContent(Suggestion);
        Suggestion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        tabHost.addTab(tabSpec);

        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            tabHost.getTabWidget().getChildAt(i).getLayoutParams().height /=2;

        }
    }
    public void click_titleback(View view)
    {
        quitapplication();
    }

    public void quitapplication()
    {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Quit");
        alert.setMessage("Do you want to quit?");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                finish();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {

            }
        });
        alert.show();
    }
    public  void disablekeyboard(AutoCompleteTextView e_search, Activity ctx)
    {
        try
        {
            InputMethodManager inputMethodManager = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(e_search.getWindowToken(), 0);
        }
        catch(Exception e)
        {
            Log.i("not hide", e.toString());
        }
    }


}
