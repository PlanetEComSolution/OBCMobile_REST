package com.planet.obcmobiles;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.planet.obcmobiles.Model.LoanLevel0_Response;
import com.planet.obcmobiles.Model.LoanLevel1_Response;
import com.planet.obcmobiles.Model.LoanLevel2_Response;
import com.planet.obcmobiles.Model.LoanLevel3_Response;
import com.planet.obcmobiles.network.API_Client;
import com.planet.obcmobiles.network.API_Interface;
import com.planet.obcmobiles.progress_dialog.Progress_Dialog;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoanIntrest extends Activity {
    final android.os.Handler mHandler = new android.os.Handler();
    Spinner Particulars, period, Amount, Interest;
    Button cal, home;
    String[] Rate = new String[1];
    ArrayList<String> name=new ArrayList<>(), time=new ArrayList<>(),
            amount=new ArrayList<>(), rate=new ArrayList<>();
    String LOAN_LEVELS_URL = "LoanLevel0";
    String a;
    String partclur_selected_item, partclur_selected_item1, partclur_selected_item2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_loan);
        Particulars = (Spinner) findViewById(R.id.particularSpinner);
        period = (Spinner) findViewById(R.id.repspinner);
        Amount = (Spinner) findViewById(R.id.amountspinner);
        home = (Button) findViewById(R.id.homeeee);
        Interest = (Spinner) findViewById(R.id.rateofinterestspinner);
        cal = (Button) findViewById(R.id.cal);
        //Spinnerdata();
        // new Spinner_Asynk().execute();
        LoanLevel0_REST_api();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoanIntrest.this, Home.class);
                startActivity(i);
                finish();
            }
        });

        Particulars.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {
                    LOAN_LEVELS_URL = "LoanLevel1";
                    partclur_selected_item = Particulars.getSelectedItem().toString();
                   // Log.d("SPINNER1", " >>>" + partclur_selected_item);
                    // new Spinner_Asynk().execute();
                    LoanLevel1_REST_api();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    //int b= Interest.getSelectedItemPosition();
                    //if(b==0){
                    // Toast.makeText(LoanIntrest.this, "Please Select Interest Rate", Toast.LENGTH_SHORT).show();
                    //}else {
                    a = Interest.getSelectedItem().toString();
                    Intent intent = new Intent(LoanIntrest.this, EMI.class);
                    intent.putExtra("LOAN_value", a);
                    intent.putExtra("LOAN_name", partclur_selected_item);
                    intent.putExtra("LOAN_amount", partclur_selected_item2);

                    startActivity(intent);
                    // }
                } catch (Exception e) {

                    Toast.makeText(LoanIntrest.this, "Please Select Interest Rate", Toast.LENGTH_SHORT).show();
                }
            }
        });

        period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {
                    LOAN_LEVELS_URL = "LoanLevel2";
                    partclur_selected_item1 = period.getSelectedItem().toString();
                    //Log.d("SPINNER1", partclur_selected_item1 + "   >>>" + partclur_selected_item);
                    LOAN_LEVELS_URL = "LoanLevel2";

                    //  new Spinner_Asynk().execute();
                    LoanLevel2_REST_api();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Amount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {
                    LOAN_LEVELS_URL = "LoanLevel3";
                    partclur_selected_item2 = Amount.getSelectedItem().toString();
                   // Log.d("SPINNER1", partclur_selected_item1 + "   >>>" + partclur_selected_item2);
                    LOAN_LEVELS_URL = "LoanLevel3";

                    //  new Spinner_Asynk().execute();
                    LoanLevel3_REST_api();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

/*
    public void first_spinner() {
        ArrayAdapter<String> particulasadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, name);
        Particulars.setAdapter(particulasadapter);
    }

    public void period_spinner() {
        ArrayAdapter<String> timeadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, time);
        period.setAdapter(timeadapter);
    }

    public void amt_spinner() {

        ArrayAdapter<String> loanadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, amount);
        Amount.setAdapter(loanadapter);
    }

    public void rate_spinner() {
        ArrayAdapter<String> Rateadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rate);
        Interest.setAdapter(Rateadapter);
    }
*/


    /*public void Spinner_data() {

        final String NAMESPACE = Utility.NAMESPACE;
        final String URL = Utility.URL_OBC_CMS;//"http://121.241.255.225/obcnew/site/OBC_CMS_App.asmx";
        final String METHOD_NAME = LOAN_LEVELS_URL;
        final String SOAP_ACTION = Utility.NAMESPACE + METHOD_NAME;// "http://tempuri.org/insertrecord";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        if (LOAN_LEVELS_URL.equals("LoanLevel1")) {
            request.addProperty("loantypename", partclur_selected_item);
           // Log.d("MY_CHECK", LOAN_LEVELS_URL + "   >>" + partclur_selected_item);
        }
        if (LOAN_LEVELS_URL.equals("LoanLevel2")) {
            request.addProperty("loantypename", partclur_selected_item);
            request.addProperty("repaymentperiod", partclur_selected_item1);
            //Log.d("MY_CHECK", LOAN_LEVELS_URL + "   >>" + partclur_selected_item);
        }
        if (LOAN_LEVELS_URL.equals("LoanLevel3")) {
            request.addProperty("loantypename", partclur_selected_item);
            request.addProperty("repaymentperiod", partclur_selected_item1);
            request.addProperty("loanamount", partclur_selected_item2);
            //Log.d("MY_CHECK", LOAN_LEVELS_URL + "   >>" + partclur_selected_item + "  >>" + partclur_selected_item2);
        }
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
                JSONObject obj = jsonArray.getJSONObject(i);
                if (LOAN_LEVELS_URL.equals("LoanLevel3")) {
                    String rate_value = obj.getString("rate");
                    rate.add(rate_value);
                    String c = rate.get(i + 1);
                    //Log.d("MY_CHECK", c);
                }
                if (LOAN_LEVELS_URL.equals("LoanLevel2")) {
                    String amount_value = obj.getString("amount");
                    amount.add(amount_value);
                    String c = amount.get(i + 1);
                   // Log.d("MY_CHECK", c);
                }
                if (LOAN_LEVELS_URL.equals("LoanLevel1")) {
                    String loan_time = obj.getString("period");
                    time.add(loan_time);
                    String b = time.get(i + 1);
                    //Log.d("MY_CHECK", b);
                }
                if (LOAN_LEVELS_URL.equals("LoanLevel0")) {
                    String duration = obj.getString("loantype");
                    name.add(duration);
                    String a = name.get(i + 1);
                   // Log.d("MY_CHECK", a);
                }

            }
            int a = time.size();
            int b = name.size();
            //Log.d("MY_CHECK", "time" + a + "name  >>>" + b);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private void LoanLevel0_REST_api() {
        Progress_Dialog.show_dialog(this);
        HashMap<String, String> hashMap = new HashMap<>();


        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.LoanLevel0(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<LoanLevel0_Response>>() {
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
                    public void onNext(List<LoanLevel0_Response> responseList) {
                        name.clear();
                        name.add("---Please Select---");

                        if (responseList != null && responseList.size() > 0) {
                            for (LoanLevel0_Response loanLevel_response : responseList) {
                                String duration = loanLevel_response.getVR_LoanType_Name();
                                name.add(duration);
                            }
                            ArrayAdapter<String> particulasadapter = new ArrayAdapter<String>(LoanIntrest.this, android.R.layout.simple_spinner_item, name);
                            Particulars.setAdapter(particulasadapter);
                        } else {
/*
                            Toast.makeText(getApplicationContext(),
                                    "Sorry, something went wrong there. Try again.", Toast.LENGTH_LONG).show();
*/

                        }
                        Progress_Dialog.hide_dialog();
                    }
                });


    }

    private void LoanLevel1_REST_api() {
        Progress_Dialog.show_dialog(this);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("loantypename", partclur_selected_item);

        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.LoanLevel1(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<LoanLevel1_Response>>() {
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
                    public void onNext(List<LoanLevel1_Response> responseList) {
                        time.clear();
                        time.add("---Please Select---");


                        if (responseList != null && responseList.size() > 0) {
                            for (LoanLevel1_Response loanLevel_response : responseList) {
                                String loan_time = loanLevel_response.getPeriod();
                                time.add(loan_time);
                            }

                            ArrayAdapter<String> timeadapter = new ArrayAdapter<String>(LoanIntrest.this, android.R.layout.simple_spinner_item, time);
                            period.setAdapter(timeadapter);


                        } else {
/*
                            Toast.makeText(getApplicationContext(),
                                    "Sorry, something went wrong there. Try again.", Toast.LENGTH_LONG).show();
*/

                        }
                        Progress_Dialog.hide_dialog();
                    }
                });


    }

    private void LoanLevel2_REST_api() {
        Progress_Dialog.show_dialog(this);
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("loantypename", partclur_selected_item);
        hashMap.put("repaymentperiod", partclur_selected_item1);

        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.LoanLevel2(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<LoanLevel2_Response>>() {
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
                    public void onNext(List<LoanLevel2_Response> responseList) {
                        amount.clear();
                        amount.add("---Please Select---");
                        if (responseList != null && responseList.size() > 0) {
                            for (LoanLevel2_Response loanLevel_response : responseList) {
                                String amount_value = loanLevel_response.getAmount();
                                amount.add(amount_value);

                            }
                            ArrayAdapter<String> loanadapter = new ArrayAdapter<String>(LoanIntrest.this, android.R.layout.simple_spinner_item, amount);
                            Amount.setAdapter(loanadapter);
                        } else {
/*
                            Toast.makeText(getApplicationContext(),
                                    "Sorry, something went wrong there. Try again.", Toast.LENGTH_LONG).show();
*/

                        }
                        Progress_Dialog.hide_dialog();
                    }
                });


    }

    private void LoanLevel3_REST_api() {

        Progress_Dialog.show_dialog(this);
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("loantypename", partclur_selected_item);
        hashMap.put("repaymentperiod", partclur_selected_item1);
        hashMap.put("loanamount", partclur_selected_item2);


        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.LoanLevel3(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<LoanLevel3_Response>>() {
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
                    public void onNext(List<LoanLevel3_Response> responseList) {
                        rate.clear();
                        if (responseList != null && responseList.size() > 0) {
                            for (LoanLevel3_Response loanLevel_response : responseList) {
                                String rate_value = loanLevel_response.getRate();
                                rate.add(rate_value);
                            }

                            ArrayAdapter<String> Rateadapter = new ArrayAdapter<String>(LoanIntrest.this, android.R.layout.simple_spinner_item, rate);
                            Interest.setAdapter(Rateadapter);


                        } else {
                         /*
                            Toast.makeText(getApplicationContext(),
                                    "Sorry, something went wrong there. Try again.", Toast.LENGTH_LONG).show();
                        */

                        }
                        Progress_Dialog.hide_dialog();
                    }
                });


    }


  /*  class Spinner_Asynk extends AsyncTask<Void, Void, Void> {
        SweetAlertDialog alertDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog = new SweetAlertDialog(LoanIntrest.this, SweetAlertDialog.PROGRESS_TYPE);
            alertDialog.setTitleText("Please Wait...");
            alertDialog.show();
            name = new ArrayList<String>();
            time = new ArrayList<String>();
            amount = new ArrayList<>();
            rate = new ArrayList<>();
            name.add("---Please Select---");
            time.add("---Please Select---");
            amount.add("---Please Select---");

        }

        @Override
        protected Void doInBackground(Void... params) {
            Spinner_data();
            final Runnable mUpdateResults2 = new Runnable() {
                public void run() {
                    if (LOAN_LEVELS_URL.equals("LoanLevel3")) {
                        //Log.d("MY_CHECK", "four RUN");
                        rate_spinner();
                    }
                    if (LOAN_LEVELS_URL.equals("LoanLevel2")) {
                       // Log.d("MY_CHECK", "third RUN");
                        amt_spinner();
                    }
                    if (LOAN_LEVELS_URL.equals("LoanLevel1")) {
                        //Log.d("MY_CHECK", "second RUN");
                        period_spinner();
                    }
                    if (LOAN_LEVELS_URL.equals("LoanLevel0")) {
                       // Log.d("MY_CHECK", "first RUN");
                        first_spinner();
                    }

                }
            };
            Thread t2 = new Thread() {
                public void run() {
                    mHandler.post(mUpdateResults2);
                }
            };
            t2.start();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();

            }
        }
    }*/
}