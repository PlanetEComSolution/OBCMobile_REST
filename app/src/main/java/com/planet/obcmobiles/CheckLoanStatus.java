package com.planet.obcmobiles;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.planet.obcmobiles.Model.LoanStatusResponse;
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

import java.util.HashMap;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CheckLoanStatus extends Activity {

    public String c;
    EditText t1;
    Button b1, home;
    int count;
    String receivedString;
    JSONArray jArray;
    String resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_check_loan_status);


        t1 = (EditText) findViewById(R.id.s);
        b1 = (Button) findViewById(R.id.register);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resp = null;
                c = t1.getText().toString();

                if (c.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter your Application no", Toast.LENGTH_LONG).show();
                } else {
                    //new Perform().execute();
                    fetch_LoanStatus_REST_API();
                }
            }
        });


    }

    /*public void userregisteration_web() {
        count = 0;
        final String NAMESPACE = "http://tempuri.org/";
        final String URL = "https://www.obcindia.co.in/obcnew/site/OBCComplaintsApp.asmx";
        final String SOAP_ACTION = "http://tempuri.org/LoanStatus";
        final String METHOD_NAME = "LoanStatus";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("LoanRefNo",c);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try
        {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            KvmSerializable ks = (KvmSerializable)envelope.bodyIn;
            for(int j=0;j<ks.getPropertyCount();j++)
            {
                ks.getProperty(j);
            }
            receivedString = ks.toString();
            System.out.println("hhhhhhhhhhhhhhhhhhhhhh"+receivedString);

        }
        catch (Exception e)
        {
            count++;
            e.printStackTrace();
        }
        try
        {

            String Jsonstring=receivedString;
            String news=Jsonstring.substring(Jsonstring.indexOf("["));
            String n1=news;
            jArray = new JSONArray(n1);

            for (int k = 0; k < (jArray.length()); k++)
            {
                JSONObject json_obj = jArray.getJSONObject(k);
                resp = json_obj.getString("LoanStatus");

            }
        }
        catch(Exception e)
        {
            count++;
            e.printStackTrace();
        }

    }
    public void internetAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CheckLoanStatus.this);
        alertDialogBuilder.setTitle("No Internet Connection");
        alertDialogBuilder
                .setMessage("Check Internet Connection!")
                .setCancelable(true)

                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        //   Complain.this.finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }*/
    public void SuccessAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                CheckLoanStatus.this);

        // set title
        alertDialogBuilder.setTitle("Your Loan Status");
        alertDialogBuilder
                .setMessage(resp)
                .setCancelable(true)

                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

   /* public void data() {
        final String NAMESPACE = Utility.NAMESPACE;
        final String URL = Utility.URL_OBC_SITE;//"http://121.241.255.225/obcnew/site/OBCComplaintsApp.asmx";
        final String METHOD_NAME = "LoanStatus";
        final String SOAP_ACTION = Utility.NAMESPACE + METHOD_NAME;

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("LoanRefNo", c);

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
            //Log.d("PIN_CODE", recved);
            String u = recved.substring(recved.indexOf("["));
            JSONArray jsonArray = new JSONArray(u);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                resp = jsonObject.getString("LoanStatus");
               // Log.d("BHANU_KITTU", resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private void fetch_LoanStatus_REST_API() {
        Progress_Dialog.show_dialog(this);
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("LoanRefNo", c);
        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.FetchLoanStatus(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<LoanStatusResponse>>() {
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
                    public void onNext(List<LoanStatusResponse> responseList) {

                        Progress_Dialog.hide_dialog();

                        if (responseList != null && responseList.size() > 0) {
                            resp = responseList.get(0).getLoanStatus();
                            SuccessAlertDialog();
                        } else {
                            Toast.makeText(CheckLoanStatus.this, "Please enter correct reference no.", Toast.LENGTH_SHORT).show();
                        }


                    }
                });


    }

  /*  public class Perform extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog = new ProgressDialog(CheckLoanStatus.this);

        @Override
        protected Void doInBackground(Void... params) {
            data();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog.isShowing()) {
                dialog.dismiss();
                if (resp == null) {
                    Toast.makeText(CheckLoanStatus.this, "Please enter correct reference no.", Toast.LENGTH_SHORT).show();
                } else {
                    SuccessAlertDialog();

                }
            }
        }

    }
*/
}

