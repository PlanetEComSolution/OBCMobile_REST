package com.planet.obcmobiles.NEW_Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.planet.obcmobiles.Main4Activity;
import com.planet.obcmobiles.Model.CreateOTPResponse;
import com.planet.obcmobiles.Model.InsertRecord_Response;
import com.planet.obcmobiles.Model.Myspinner;
import com.planet.obcmobiles.Model.OTP_Reponse;
import com.planet.obcmobiles.R;
import com.planet.obcmobiles.Utility;
import com.planet.obcmobiles.network.API_Client;
import com.planet.obcmobiles.network.API_Interface;
import com.planet.obcmobiles.progress_dialog.Progress_Dialog;

import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Admin on 3/19/2018.
 */

public class Apply_Loan_New extends AppCompatActivity implements Animation.AnimationListener {
    NodeList nodelist;
    ProgressDialog pDialog;
    String mobile_str = "91";
    String msg = "Your OTP is  ";
    //String urll = "https://www.unicel.in/servxml/XML_parse_API.php?data=";
    ArrayList<Myspinner> list_state_id;
    ArrayList<Myspinner> list_city_id;
    ArrayList<Myspinner> list_branch_id;
    String shjjfsdgu = "";
    private String Name;
    private EditText mobile, otp;
    private Button send_otp, submit;
    private String str_mobile_no;
    private int startRange = 1000, endRange = 9999;
    private Random random;
    private String randum_no, str_otp;
    private AutoCompleteTextView state, city, branch;
    // Insert image URL
    private int catchhandl;
    private ArrayAdapter adapter;
    private ArrayList<String> list_state = new ArrayList<>();
    private String state_id = "0";
    private String city_id = "0";
    private String branch_id = "0";
    private WebView webview;
    private String str_name, str_email, str_product_name, str_mobile, str_state, str_city, str_branch, str_account_no;
//boolean OTP_Sent=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_loan_new);
        ImageView back = (ImageView) findViewById(R.id.back);
        mobile = (EditText) findViewById(R.id.mobile);
        send_otp = (Button) findViewById(R.id.send_otp);
        submit = (Button) findViewById(R.id.submit);
        otp = (EditText) findViewById(R.id.otp);
        webview = (WebView) findViewById(R.id.webview);
        webview.setWebViewClient(new CustomWebViewClient());
        random = new Random();
        Utility.LoanOTPSent(false,this);
        send_OTP();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateAllFields()) {
                    Utility.FailedOTPCounterReset(0,Apply_Loan_New.this);
                    if (isConnectingToInternet()) {
                        submit_Alldata_REST_API();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "No Internet Connection...", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Animation_image();
        if (isConnectingToInternet()) {

        } else {
            Toast.makeText(getApplicationContext(),
                    "No Internet Connection...", Toast.LENGTH_LONG).show();
        }

        Intent i = getIntent();
        if (i != null) {
            // Name = i.getStringExtra("Name");
            //  set_name.setText(Name);

        }
    }

    private void send_OTP() {
        send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Utility.CheckLoanOTPSentAlready(Apply_Loan_New.this)){
                    String msg="OTP already sent! Please check message box.";
                    Toast.makeText(Apply_Loan_New.this,msg,Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Utility.ApplyingLoanWitin5Minutes(Apply_Loan_New.this)){
                   String msg="Please try after 5 minutes as the previous application is in process!";
                   Toast.makeText(Apply_Loan_New.this,msg,Toast.LENGTH_SHORT).show();
                   return;
                }


                randum_no = random.nextInt((endRange - startRange) + startRange) + startRange + "";
               // Utility.setRandumNo(Apply_Loan_New.this, randum_no);
                str_mobile_no = mobile.getText().toString();
              //  String mobilee = mobile_str + str_mobile_no;
                //String msgg = msg + randum_no;
               // String url = Utility.URL_UNICEL + "<MESSAGE VER=\"1.2\"><USER USERNAME=\"obc-saathi\" PASSWORD=\"g)6Ei!2R\" DLR=\"0\"/><SMS TEXT=\"" + msgg + "\" ID=\"1\" VP=\"120\" INTL=\"1\"><ADDRESS FROM=\"Oriental Saathi\" TO=\"" + mobilee + "\" SEQ=\"1\"/></SMS></MESSAGE>";

                if (validateFields()) {
                 /*   webview.loadUrl(url);
                    Toast.makeText(getApplicationContext(),
                            "OTP Send to Your Mobile Number Please check", Toast.LENGTH_LONG).show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            randum_no = "";
                        }
                    }, 9000000);

*/
                    Create_OTP_REST_api();
                }

            }
        });

    }









    private void Create_OTP_REST_api() {
        showPopUpAfterDelay();

        Utility.LoanOTPSent(true,this);
        String mobile = mobile_str + str_mobile_no;
        String msgg = msg + randum_no;
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("mobile", mobile);
        hashMap.put("msg", msgg);

        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.Generate_otp(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OTP_Reponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("", e.getMessage());
                        /*String error = e.getMessage();
                        Toast.makeText(getApplicationContext(),
                                "Network Error....", Toast.LENGTH_LONG).show();*/
                        // Toast.makeText(Apply_Loan_New.this,"Error...."+e.getMessage(),Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onNext(OTP_Reponse login_model) {
                        /*  hideDialog();*/
                      //  Toast.makeText(Apply_Loan_New.this,"Success...."+login_model.getMsg(),Toast.LENGTH_LONG).show();

                    }
                });


    }
    private void showPopUpAfterDelay() {

        final ProgressDialog progressDialog = new ProgressDialog(Apply_Loan_New.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    progressDialog.dismiss();
                } catch (Exception e) {
                    e.getMessage();
                }

                Toast.makeText(getApplicationContext(),
                        "OTP Sent to Your Mobile Number Please check", Toast.LENGTH_LONG).show();


            }
        }, 5000);


    }

 /*   private void submit_data() {
        catchhandl = 0;
        final String NAMESPACE = Utility.NAMESPACE;
        final String URL = Utility.URL_OBC_SATHI_NEW;
        final String METHOD_NAME = "insertrecord";
        final String SOAP_ACTION = Utility.NAMESPACE + METHOD_NAME;// "http://tempuri.org/insertrecord";


        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("name", str_name);
        request.addProperty("email", str_email);
        request.addProperty("mobile", str_mobile);
        request.addProperty("product_name", str_product_name);
        request.addProperty("city_id", city_id);
        request.addProperty("state_id", state_id);
        request.addProperty("branch_id", branch_id);
        request.addProperty("acount_number", str_account_no);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // put all required data into a soap
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE httpTransport = new HttpTransportSE(URL);
        try {
            httpTransport.call(SOAP_ACTION, envelope);
            KvmSerializable ks = (KvmSerializable) envelope.bodyIn;

            for (int j = 0; j < ks.getPropertyCount(); j++) {
                ks.getProperty(j);
            }
            String recved = ks.toString();
            String u = recved.substring(recved.indexOf("["));
            JSONArray jsonArray = new JSONArray(u);


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                String  City_id = jsonObject.getString("City_id");
//                String  city_name = jsonObject.getString("city_name");

            }

        } catch (Exception e) {
            catchhandl++;
            e.printStackTrace();
        }
    }

*/

    private void submit_Alldata_REST_API() {
        Progress_Dialog.show_dialog(this);
        HashMap<String, String> hashMap = new HashMap<>();


        hashMap.put("name", str_name);
        hashMap.put("email", str_email);
        hashMap.put("mobile", str_mobile);
        hashMap.put("product_name", str_product_name);
        hashMap.put("city_id", city_id);
        hashMap.put("state_id", state_id);
        hashMap.put("branch_id", branch_id);
        hashMap.put("acount_number", str_account_no);



        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.InsertRecord(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InsertRecord_Response>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Progress_Dialog.hide_dialog();
                        String error = e.getMessage();
                        Toast.makeText(getApplicationContext(),
                                "Network Error....", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(InsertRecord_Response response) {

                        Progress_Dialog.hide_dialog();


                        if (response .getStatus().equals("1")) {
                            Toast.makeText(getApplicationContext(),
                                    "Submitted successfully!", Toast.LENGTH_LONG).show();
                            Intent web = new Intent().setClass(getApplicationContext(), Main4Activity.class);
                            startActivity(web);
                            finish();

                        } else {

                            Toast.makeText(getApplicationContext(),
                                    "Sorry, something went wrong there. Try again.", Toast.LENGTH_LONG).show();

                        }




                    }
                });


    }




    private void Animation_image() {
        Animation animation_blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        animation_blink.setAnimationListener(this);
        ImageButton button_anim = (ImageButton) findViewById(R.id.imageButton2);
        button_anim.startAnimation(animation_blink);
        double r = 5 / 400.0;
        double a = 1 + r;
        a = Math.pow(a, 3);
        double l = 10000 * a - 1;
        double m = Math.pow(1 + r, -1 / 3);
        double n = l / m;
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    private boolean validateFields() {
        str_mobile_no = mobile.getText().toString();
        if (str_mobile_no.trim().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(),
                    "Mobile Number Required", Toast.LENGTH_LONG).show();
            return false;
        }

        if (str_mobile_no.length() != 10) {
            Toast.makeText(getApplicationContext(),
                    "Please Enter Valid Mobile Number", Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }

    private boolean validateAllFields() {

        if (Utility.CheckFailedOTPLimitExceed(Apply_Loan_New.this)) {
            String msg = "You have entered wrong OTP 3 times!\n Please try after 1 hour";
            Toast.makeText(getApplicationContext(),
                    msg, Toast.LENGTH_LONG).show();

            return false;
        }

        str_mobile = mobile.getText().toString();
        if (str_mobile.trim().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(),
                    "Please Enter Mobile_Number", Toast.LENGTH_LONG).show();
            return false;
        }

        if (str_mobile.length() != 10) {
            Toast.makeText(getApplicationContext(),
                    "Please Enter Valid Mobile_Number", Toast.LENGTH_LONG).show();
            return false;
        }


        str_otp = otp.getText().toString();
        if (str_otp.trim().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(),
                    "Please Enter OTP", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!str_otp.trim().equalsIgnoreCase(randum_no)) {
            Utility.IncreaseFailedOTPCounter(Apply_Loan_New.this);
            Toast.makeText(getApplicationContext(),
                    "Please Enter Valid OTP", Toast.LENGTH_LONG).show();
            return false;
        }else {
            Utility.SaveLoanAppliedTime(this);
        }

        return true;
    }

    private boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) Apply_Loan_New.this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

   /* class submit_Alldata_api extends AsyncTask<Void, Void, Void> {
        private ProgressDialog Dialog = new ProgressDialog(Apply_Loan_New.this);

        @Override
        protected void onPreExecute() {
            Dialog.setMessage("Please wait...");
            Dialog.setCancelable(false);
            Dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            submit_data();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (catchhandl == 0) {
                try {
                    Toast.makeText(getApplicationContext(),
                            "Submit successfully", Toast.LENGTH_LONG).show();
                    Intent web = new Intent().setClass(getApplicationContext(), Main4Activity.class);
                    startActivity(web);
                    finish();

                } catch (Exception e) {
                }
            } else {

                Toast.makeText(getApplicationContext(),
                        "Sorry, something went wrong there. Try again.", Toast.LENGTH_LONG).show();

            }
            if (Dialog.isShowing()) {
                Dialog.dismiss();
            }
        }
    }

*/
}
