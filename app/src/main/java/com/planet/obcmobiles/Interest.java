package com.planet.obcmobiles;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.planet.obcmobiles.Model.Deposit_Response;
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

public class Interest extends AppCompatActivity {
    final android.os.Handler mHandler = new android.os.Handler();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> rate = new ArrayList<>();
    ArrayList<Integer> minday_list = new ArrayList<>();
    ArrayList<Integer> maxday_list = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        listView = (ListView) findViewById(R.id.listView2);
      //  new Contact_Data().execute();
        Deposit_REST_api();

        listView.refreshDrawableState();


    }

   /* public void contacts() {
        final String NAMESPACE = Utility.NAMESPACE;
        final String URL = Utility.URL_OBC_CMS;//"http://121.241.255.225/obcnew/site/OBC_CMS_App.asmx";
        final String METHOD_NAME = "Deposit";
        final String SOAP_ACTION = Utility.NAMESPACE + METHOD_NAME;// "http://tempuri.org/insertrecord";


        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        listView.refreshDrawableState();

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
            //Log.d("Interest", recved);
            String u = recved.substring(recved.indexOf("["));
            //Log.d("Interest", u);
            JSONArray jsonArray = new JSONArray(u);
            name = new ArrayList<String>();
            rate = new ArrayList<String>();
            minday_list = new ArrayList<>();
            maxday_list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String duration = obj.getString("duration");
                String rates = obj.getString("rate");
                String minday = obj.getString("MinDays");
                String maxday = obj.getString("MaxDays");
                int MIN_DAY = Integer.parseInt(minday);
                int MAX_DAY = Integer.parseInt(maxday);

                double mDay = Double.parseDouble(maxday);
                int result = (int) (mDay / 30);
                int find = (int) Math.round(result);
                name.add(duration);
                rate.add(rates);
                minday_list.add(MIN_DAY);
                maxday_list.add(MAX_DAY);

                String a = name.get(i);
                int aa = minday_list.get(i);
                int aaa = maxday_list.get(i);
               // Log.d("Interest", a + ">>>" + aa + ">>" + aaa + ">>" + result);
            }
            name.add(" ");
            rate.add(" ");
            minday_list.add(0);
            maxday_list.add(0);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

*/
    private void Deposit_REST_api() {
        Progress_Dialog.show_dialog(this);
        HashMap<String, String> hashMap = new HashMap<>();

        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.Deposit(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Deposit_Response>>() {
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
                    public void onNext(List<Deposit_Response> responseList) {

                        listView.refreshDrawableState();
                        name.clear();
                        rate.clear();
                        minday_list.clear();
                        maxday_list.clear();

                        if (responseList != null && responseList.size() > 0) {
                            for (Deposit_Response deposit_response : responseList) {
                              try {
                                  String duration = deposit_response.getVR_Duration();
                                  String rates = deposit_response.getVR_RateofInterest();
                                  String minday = deposit_response.getMinDays();
                                  String maxday = deposit_response.getMaxDays();
                                  int MIN_DAY = Integer.parseInt(minday);
                                  int MAX_DAY = Integer.parseInt(maxday);
                                  name.add(duration);
                                  rate.add(rates);
                                  minday_list.add(MIN_DAY);
                                  maxday_list.add(MAX_DAY);
                              }catch (Exception e){
                                  e.getMessage();
                              }

                            }

                            name.add(" ");
                            rate.add(" ");
                            minday_list.add(0);
                            maxday_list.add(0);


                        } else {

                            Toast.makeText(getApplicationContext(),
                                    "Sorry, something went wrong there. Try again.", Toast.LENGTH_LONG).show();

                        }

                        Adapter_intrst adapterIntrst = new Adapter_intrst(name, rate, minday_list, maxday_list, Interest.this);
                        adapterIntrst.notifyDataSetChanged();

                        listView.setAdapter(adapterIntrst);
                        adapterIntrst.notifyDataSetChanged();
                        listView.refreshDrawableState();

                        Progress_Dialog.hide_dialog();

                    }
                });


    }


   /* class Contact_Data extends AsyncTask<Void, Void, Void> {
        SweetAlertDialog alertDialog;
        Adapter_intrst adapterIntrst;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog = new SweetAlertDialog(Interest.this, SweetAlertDialog.PROGRESS_TYPE);
            alertDialog.setTitleText("Please Wait...");
            alertDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            contacts();
            adapterIntrst = new Adapter_intrst(name, rate, minday_list, maxday_list, Interest.this);
            adapterIntrst.notifyDataSetChanged();

            final Runnable mUpdateResults2 = new Runnable() {

                public void run() {
                    listView.setAdapter(adapterIntrst);
                    adapterIntrst.notifyDataSetChanged();
                    listView.refreshDrawableState();
                }
            };
            Thread t2 = new Thread() {
                public void run() {
                    mHandler.post(mUpdateResults2);
                    adapterIntrst.notifyDataSetChanged();
                    listView.refreshDrawableState();
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
    }
*/
}

