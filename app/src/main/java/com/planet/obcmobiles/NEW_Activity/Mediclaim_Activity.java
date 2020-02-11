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
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.planet.obcmobiles.Model.BranchResponse;
import com.planet.obcmobiles.Model.CityResponse;
import com.planet.obcmobiles.Model.CreateOTPResponse;
import com.planet.obcmobiles.Model.InsertRecord_Response;
import com.planet.obcmobiles.Model.Myspinner;
import com.planet.obcmobiles.Model.OTP_Reponse;
import com.planet.obcmobiles.Model.StateResponse;
import com.planet.obcmobiles.R;
import com.planet.obcmobiles.Utility;
import com.planet.obcmobiles.network.API_Client;
import com.planet.obcmobiles.network.API_Interface;
import com.planet.obcmobiles.progress_dialog.Progress_Dialog;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Admin on 1/30/2018.
 */

public class Mediclaim_Activity extends AppCompatActivity implements Animation.AnimationListener {
    NodeList nodelist;
    ProgressDialog pDialog;
    String mobile_str = "91";
    String msg = "Your OTP is  ";
    //String urll = Utility.URL_UNICEL;
    ArrayList<Myspinner> list_state_id = new ArrayList<>();
    ArrayList<Myspinner> list_city_id = new ArrayList<>();
    ArrayList<Myspinner> list_branch_id = new ArrayList<>();
    String shjjfsdgu = "";
    private String Name;
    private EditText mobile, otp, name, email, product_name, account_no;
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

    // getNode function
    private static String getNode(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
                .getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mediclaim_activity);
        TextView set_name = (TextView) findViewById(R.id.set_name);
        product_name = (EditText) findViewById(R.id.product_name);
        ImageView back = (ImageView) findViewById(R.id.back);
        mobile = (EditText) findViewById(R.id.mobile);
        send_otp = (Button) findViewById(R.id.send_otp);
        submit = (Button) findViewById(R.id.submit);
        otp = (EditText) findViewById(R.id.otp);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        account_no = (EditText) findViewById(R.id.account_no);
        state = (AutoCompleteTextView) findViewById(R.id.state);
        city = (AutoCompleteTextView) findViewById(R.id.city);
        branch = (AutoCompleteTextView) findViewById(R.id.branch);
        webview = (WebView) findViewById(R.id.webview);
        webview.setWebViewClient(new CustomWebViewClient());
        random = new Random();
        Utility.MediclaimOTPSent(false, this);
        send_OTP();
        state_click();
        city_click();
        branch_show();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateAllFields()) {
                    if (isConnectingToInternet()) {
                        //   new submit_Alldata_api().execute();
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
            // new state_api().execute();
            state_REST_API();

        } else {
            Toast.makeText(getApplicationContext(),
                    "No Internet Connection...", Toast.LENGTH_LONG).show();
        }

        Intent i = getIntent();
        if (i != null) {
            Name = i.getStringExtra("Name");
            set_name.setText(Name);
            product_name.setText(Name);
        }
    }

    private void send_OTP() {
        send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Utility.CheckMediclaimOTPSentAlready(Mediclaim_Activity.this)){
                    String msg="OTP already sent! Please check message box.";
                    Toast.makeText(Mediclaim_Activity.this,msg,Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Utility.ApplyingMediclaimWithin5Minutes(Mediclaim_Activity.this)){
                    String msg="Please try after 5 minutes as the previous application is in process!";
                    Toast.makeText(Mediclaim_Activity.this,msg,Toast.LENGTH_SHORT).show();
                    return;
                }

                randum_no = random.nextInt((endRange - startRange) + startRange) + startRange + "";
                str_mobile_no = mobile.getText().toString();
                // String mobilee = mobile_str + str_mobile_no;
                //  String msgg = msg + randum_no;
                //  String url = urll + "<MESSAGE VER=\"1.2\"><USER USERNAME=\"obc-saathi\" PASSWORD=\"g)6Ei!2R\" DLR=\"0\"/><SMS TEXT=\"" + msgg + "\" ID=\"1\" VP=\"120\" INTL=\"1\"><ADDRESS FROM=\"Oriental Saathi\" TO=\"" + mobilee + "\" SEQ=\"1\"/></SMS></MESSAGE>";
                if (validateFields()) {
                   /* webview.loadUrl(url);
                    Toast.makeText(getApplicationContext(),
                            "OTP Sent to Your Mobile Number Please check", Toast.LENGTH_LONG).show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            randum_no = "";
                        }
                    }, 9000000);*/

                    Create_OTP_REST_api();
                }

            }
        });

    }


    private void Create_OTP_REST_api() {
        showPopUpAfterDelay();
        Utility.MediclaimOTPSent(true,this);
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

                        /*String error = e.getMessage();
                        Toast.makeText(getApplicationContext(),
                                "Network Error....", Toast.LENGTH_LONG).show();*/
                    }

                    @Override
                    public void onNext(OTP_Reponse login_model) {
                        /*  hideDialog();*/
                    }
                });


    }

    private void showPopUpAfterDelay() {

        final ProgressDialog progressDialog = new ProgressDialog(Mediclaim_Activity.this);
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

    private void branch_show() {
        branch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                branch.setFocusable(false);
                branch.setFocusableInTouchMode(false);
                //  text.setClickable(false);
                if (!branch.getText().toString().equals(""))
                    adapter.getFilter().filter(null);
                branch.showDropDown();
                return false;
            }
        });
        branch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < list_branch_id.size(); i++) {
                    Myspinner myspinner = list_branch_id.get(i);
                    String State_name = myspinner.getSpinnerText();

                    if (State_name.equals(branch.getText().toString().trim())) {
                        branch_id = myspinner.getid();
                        break;
                    }
                }

                //Toast.makeText(Mediclaim_Activity.this,"Clicked " + branch_id,Toast.LENGTH_LONG).show();
            }
        });


    }

    private void city_click() {

        city.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                city.setFocusable(false);
                city.setFocusableInTouchMode(false);
                //  text.setClickable(false);
                if (!city.getText().toString().equals(""))
                    adapter.getFilter().filter(null);
                city.showDropDown();
                return false;
            }
        });

        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < list_city_id.size(); i++) {
                    Myspinner myspinner = list_city_id.get(i);
                    String State_name = myspinner.getSpinnerText();

                    if (State_name.equals(city.getText().toString().trim())) {
                        city_id = myspinner.getid();
                        break;
                    }

                    branch.setText("");
                }
                if (isConnectingToInternet()) {
                    //new branch_api().execute();
                    branch_REST_API();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "No Internet Connection...", Toast.LENGTH_LONG).show();
                }
                // Toast.makeText(Mediclaim_Activity.this,"Clicked " + city_id,Toast.LENGTH_LONG).show();
            }
        });


    }

    private void state_click() {

        state.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                state.setFocusable(false);
                state.setFocusableInTouchMode(false);
                //  text.setClickable(false);
                if (!state.getText().toString().equals(""))
                    adapter.getFilter().filter(null);
                state.showDropDown();
                return false;
            }

        });
        state.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < list_state_id.size(); i++) {
                    Myspinner myspinner = list_state_id.get(i);
                    String State_name = myspinner.getSpinnerText();

                    if (State_name.equals(state.getText().toString().trim())) {
                        state_id = myspinner.getid();
                        break;
                    }
                    city.setText("");
                    branch.setText("");
                }
                if (isConnectingToInternet() == true) {
                    //   new city_api().execute();
                    city_REST_API();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "No Internet Connection...", Toast.LENGTH_LONG).show();
                }
                //  Toast.makeText(Mediclaim_Activity.this,"Clicked " + state_id,Toast.LENGTH_LONG).show();
            }
        });
    }

    /*private void product() {
        catchhandl = 0;
        final String NAMESPACE = Utility.NAMESPACE;
        final String URL = Utility.URL_OBC_SATHI_NEW;// "http://121.241.255.225/obcnew/Sathi_webServices.asmx";
        final String METHOD_NAME = "GetState";
        final String SOAP_ACTION = Utility.NAMESPACE + METHOD_NAME;

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


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

            list_state = new ArrayList<>();
            list_state_id = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String state_id = jsonObject.getString("state_id");
                String state_name = jsonObject.getString("state_name");

                // list_state.add(state_name);
                list_state_id.add(new Myspinner(state_name, state_id));

            }

        } catch (Exception e) {
            catchhandl++;
            e.printStackTrace();
        }
    }
*/
    private void state_REST_API() {
        Progress_Dialog.show_dialog(this);
        HashMap<String, String> hashMap = new HashMap<>();

        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.FetchState(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<StateResponse>>() {
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
                    public void onNext(List<StateResponse> responseList) {

                        Progress_Dialog.hide_dialog();

                        list_state_id.clear();
                        for (StateResponse stateResponse : responseList) {
                            list_state_id.add(new Myspinner(stateResponse.getState_name(), stateResponse.getState_id()));
                        }

                        try {
                            adapter = new ArrayAdapter(Mediclaim_Activity.this, R.layout.simple_dropdown_item, list_state_id);
                            state.setAdapter(adapter);
                        } catch (Exception e) {
                        }


                    }
                });


    }

/*
    private void city_background() {
        catchhandl = 0;
        final String NAMESPACE = Utility.NAMESPACE;
        final String URL = Utility.URL_OBC_SATHI_NEW;// "http://121.241.255.225/obcnew/Sathi_webServices.asmx";
        final String METHOD_NAME = "GetCity";
        final String SOAP_ACTION = Utility.NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("state_Id", state_id);


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

            list_state = new ArrayList<>();
            list_city_id = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String City_id = jsonObject.getString("City_id");
                String city_name = jsonObject.getString("city_name");

                // list_state.add(state_name);
                list_city_id.add(new Myspinner(city_name, City_id));

            }

        } catch (Exception e) {
            catchhandl++;
            e.printStackTrace();
        }
    }*/

    private void city_REST_API() {
        Progress_Dialog.show_dialog(this);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("state_Id", state_id);

        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.FetchCity(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CityResponse>>() {
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
                    public void onNext(List<CityResponse> responseList) {

                        Progress_Dialog.hide_dialog();

                        list_city_id.clear();
                        for (CityResponse cityResponse : responseList) {
                            list_city_id.add(new Myspinner(cityResponse.getCity_name(), cityResponse.getCity_id()));
                        }

                        try {
                            adapter = new ArrayAdapter(Mediclaim_Activity.this, R.layout.simple_dropdown_item, list_city_id);
                            city.setAdapter(adapter);

                        } catch (Exception e) {
                        }


                    }
                });


    }

/*
    private void branch_background() {
        catchhandl = 0;
        final String NAMESPACE = Utility.NAMESPACE;
        final String URL = Utility.URL_OBC_SATHI_NEW;// "http://121.241.255.225/obcnew/Sathi_webServices.asmx";
        final String METHOD_NAME = "GetBranchName";
        final String SOAP_ACTION = Utility.NAMESPACE + METHOD_NAME;


        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("state_id", state_id);
        request.addProperty("city_id", city_id);

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

            list_state = new ArrayList<>();
            list_branch_id = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String branch_id = jsonObject.getString("branch_id");
                String branch_name = jsonObject.getString("branch_name");

                // list_state.add(state_name);
                list_branch_id.add(new Myspinner(branch_name, branch_id));

            }

        } catch (Exception e) {
            catchhandl++;
            e.printStackTrace();
        }
    }*/


    private void branch_REST_API() {
        Progress_Dialog.show_dialog(this);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("state_id", state_id);
        hashMap.put("city_id", city_id);
        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.FetchBranch(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<BranchResponse>>() {
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
                    public void onNext(List<BranchResponse> responseList) {

                        Progress_Dialog.hide_dialog();

                        list_branch_id.clear();
                        for (BranchResponse branchResponse : responseList) {
                            list_branch_id.add(new Myspinner(branchResponse.getBranch_name(), branchResponse.getBranch_id()));
                        }

                        try {
                            adapter = new ArrayAdapter(Mediclaim_Activity.this, R.layout.simple_dropdown_item, list_branch_id);
                            branch.setAdapter(adapter);

                        } catch (Exception e) {
                        }


                    }
                });


    }





   /* private void submit_data() {
        catchhandl = 0;

        final String NAMESPACE = Utility.NAMESPACE;
        final String URL = Utility.URL_OBC_SATHI_NEW;// "http://121.241.255.225/obcnew/Sathi_webServices.asmx";
        final String METHOD_NAME = "insertrecord";
        final String SOAP_ACTION = Utility.NAMESPACE + METHOD_NAME;

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
    }*/


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


                        if (response.getStatus().equals("1")) {
                            Toast.makeText(getApplicationContext(),
                                    "Submitted successfully!", Toast.LENGTH_LONG).show();
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

        str_account_no = account_no.getText().toString();
        str_name = name.getText().toString();
        if (str_name.trim().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(),
                    "Please Enter Name", Toast.LENGTH_LONG).show();
            return false;
        }

        str_email = email.getText().toString();
        if (str_email.trim().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(),
                    "Please Enter Email_Id", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!isValidEmail(str_email)) {
            Toast.makeText(getApplicationContext(),
                    "Please Enter Valid Email_Id", Toast.LENGTH_LONG).show();
            return false;
        }

        str_mobile = mobile.getText().toString();
        if (str_mobile.trim().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(),
                    "Please Enter Mobile_Number", Toast.LENGTH_LONG).show();
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


        str_product_name = product_name.getText().toString();
        if (str_product_name.trim().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(),
                    "Please Enter Product Name", Toast.LENGTH_LONG).show();
            return false;
        }

        str_state = state.getText().toString();
        if (str_state.trim().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(),
                    "Please Select State Name", Toast.LENGTH_LONG).show();
            return false;
        }

        str_city = city.getText().toString();
        if (str_city.trim().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(),
                    "Please Select City Name", Toast.LENGTH_LONG).show();
            return false;
        }

        str_branch = branch.getText().toString();
        if (str_branch.trim().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(),
                    "Please Select Branch Name", Toast.LENGTH_LONG).show();
            return false;
        }

        str_otp = otp.getText().toString();
        if (str_otp.trim().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(),
                    "Please Enter OTP", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!str_otp.trim().equalsIgnoreCase(randum_no)) {
            Toast.makeText(getApplicationContext(),
                    "Please Enter Valid OTP", Toast.LENGTH_LONG).show();
            return false;
        }else {
            Utility.SaveMediclaimAppliedTime(this);
        }
        return true;
    }

    private boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) Mediclaim_Activity.this
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

//    private class mobileXML extends AsyncTask<String, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            // Create a progressbar
//            pDialog = new ProgressDialog(Mediclaim_Activity.this);
//            // Set progressbar title
//            // pDialog.setTitle("Android Simple XML Parsing using DOM Tutorial");
//            // Set progressbar message
//            pDialog.setMessage("Loading...");
//            pDialog.setIndeterminate(false);
//            // Show progressbar
//            pDialog.show();
//        }
//
//        @Override
//        protected Void doInBackground(String... Url) {
////            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
////            try {
////    /* Parse the xml-data from our URL. */90
////                URL url = new URL(Url[0]);
////                InputStream inputStream = url.openStream();
////    /*Get Document Builder*/
////                DocumentBuilder builder = builderFactory.newDocumentBuilder();
////                Document dom = builder.parse(inputStream);
////
////
////                Element rootElement = dom.getDocumentElement();
////
////            } catch (Exception e) { }
//
//
//            try {
//                URL url = new URL(Url[0]);
//
//                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//                DocumentBuilder db = dbf.newDocumentBuilder();
//                Document doc = db.parse(new InputSource(url.openStream()));
//
//                //  doc.setXMLEncoding("ISO-8859-1");
//                doc.getDocumentElement().normalize();
//
//                nodelist = doc.getElementsByTagName("MESSAGEACK");
//
//
//            } catch (Exception e) {
//                //Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return null;
//
//        }
//
//        @Override
//        protected void onPostExecute(Void args) {
//            try {
//                for (int temp = 0; temp < nodelist.getLength(); temp++) {
//                    Node nNode = nodelist.item(temp);
//                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//                        Element eElement = (Element) nNode;
//
//                        Toast.makeText(getApplicationContext(),
//                                "OTP Send Your Mobile Number Please check", Toast.LENGTH_LONG).show();
//
//                        // Utility.setRandumNo(Mediclaim_Activity.this,"");
//                        // Set the texts into TextViews from item nodes
//                        // Get the title
//
////                    String ID= getNode("ID", eElement);
////                    String MID= getNode("MID", eElement);
//
////                    textview.setText(textview.getText() + "Title : "
////                            + getNode("title", eElement) + "\n" + "\n");
////                    // Get the description
////                    textview.setText(textview.getText() + "Description : "
////                            + getNode("description", eElement) + "\n" + "\n");
////                    // Get the link
////                    textview.setText(textview.getText() + "Link : "
////                            + getNode("link", eElement) + "\n" + "\n");
////                    // Get the date
////                    textview.setText(textview.getText() + "Date : "
////                            + getNode("date", eElement) + "\n" + "\n" + "\n"
////                            + "\n");
//                    }
//                }
//            } catch (Exception e) {
//            }
//            // Close progressbar
//            pDialog.dismiss();
//        }
//    }

/*
    class state_api extends AsyncTask<Void, Void, Void> {
        private ProgressDialog Dialog = new ProgressDialog(Mediclaim_Activity.this);

        @Override
        protected void onPreExecute() {
            Dialog.setMessage("Please wait...");
            Dialog.setCancelable(false);
            Dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            product();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (catchhandl == 0) {
                try {
                    adapter = new ArrayAdapter(Mediclaim_Activity.this, R.layout.simple_dropdown_item, list_state_id);
                    state.setAdapter(adapter);

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

    class city_api extends AsyncTask<Void, Void, Void> {
        private ProgressDialog Dialog = new ProgressDialog(Mediclaim_Activity.this);

        @Override
        protected void onPreExecute() {
            Dialog.setMessage("Please wait...");
            Dialog.setCancelable(false);
            Dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            city_background();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (catchhandl == 0) {
                try {
                    adapter = new ArrayAdapter(Mediclaim_Activity.this, R.layout.simple_dropdown_item, list_city_id);
                    city.setAdapter(adapter);

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

    class branch_api extends AsyncTask<Void, Void, Void> {
        private ProgressDialog Dialog = new ProgressDialog(Mediclaim_Activity.this);

        @Override
        protected void onPreExecute() {
            Dialog.setMessage("Please wait...");
            Dialog.setCancelable(false);
            Dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            branch_background();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (catchhandl == 0) {
                try {
                    adapter = new ArrayAdapter(Mediclaim_Activity.this, R.layout.simple_dropdown_item, list_branch_id);
                    branch.setAdapter(adapter);

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

    class submit_Alldata_api extends AsyncTask<Void, Void, Void> {
        private ProgressDialog Dialog = new ProgressDialog(Mediclaim_Activity.this);

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
