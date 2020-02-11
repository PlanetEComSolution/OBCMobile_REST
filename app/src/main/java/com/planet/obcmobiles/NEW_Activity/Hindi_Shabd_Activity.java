package com.planet.obcmobiles.NEW_Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.planet.obcmobiles.Adapter_new.Product_Services_Adapter;
import com.planet.obcmobiles.Model.FetchExcel_Response;
import com.planet.obcmobiles.Model.Product2_Response;
import com.planet.obcmobiles.Model.Product_model;
import com.planet.obcmobiles.R;
import com.planet.obcmobiles.Sqlite.Databaseutill;
import com.planet.obcmobiles.Sqlite.GetData;
import com.planet.obcmobiles.Utility;
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
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Admin on 9/6/2017.
 */

public class Hindi_Shabd_Activity extends AppCompatActivity {
    Map<String, String> map = new HashMap<String, String>();
    private int  catchhandl;
    private Databaseutill db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hindi_shabd_activity);

        ImageView back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        if (isConnectingToInternet() == true) {
//
//            new Get_Data().execute();
//
//        } else {
//            Toast.makeText(getApplicationContext(), "No Internet Connection Found", Toast.LENGTH_SHORT).show();
//        }

        final EditText edt =(EditText)findViewById(R.id.word);
        final TextView txt =(TextView)findViewById(R.id.result);
        final TextView eng_word =(TextView)findViewById(R.id.eng_word);
        final TextView word_no =(TextView)findViewById(R.id.word_no);
        Button btn =(Button)findViewById(R.id.search);
        ImageView sync =(ImageView)findViewById(R.id.sync);
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isConnectingToInternet()) {

                    try{
                        db = Databaseutill.getDBAdapterInstance(Hindi_Shabd_Activity.this);
                        GetData gett= new  GetData(db, getApplication());
                        gett.Delete_record_CRGOPEN();

                    }catch (Exception e){
                    }

                   // new Get_Data().execute();
                    FirmListdata_REST_api();


                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wordd = edt.getText().toString();


                if(!wordd.equalsIgnoreCase("")){
                try{
                    Databaseutill db;
                    GetData get;
                    db = Databaseutill.getDBAdapterInstance(getApplication());
                    get = new GetData(db, getApplication());
                    String word = edt.getText().toString();
                    String upperString = word.substring(0,1).toUpperCase() + word.substring(1);
                    String hindi= get.Search(word);

                    if(hindi.equalsIgnoreCase("")){
                        word_no.setVisibility(View.VISIBLE);
                        txt.setVisibility(View.GONE);
                        edt.setText("");
                        eng_word.setText(upperString);
                       // eng_word.setVisibility(View.GONE);

                    }else{
                        txt.setText(hindi);
                        eng_word.setText(upperString);
                        edt.setText("");
                        txt.setVisibility(View.VISIBLE);
                        eng_word.setVisibility(View.VISIBLE);
                        word_no.setVisibility(View.GONE);
                    }


                   // ArrayList<HashMap<String,String>> listData=get.getSync_Data();

                }catch ( Exception e){
                }
                }else{

                    Toast.makeText(getApplicationContext(), "Please Enter Word", Toast.LENGTH_SHORT).show();
                }
//
//                String word = edt.getText().toString();
//                txt.setText(map.get(word));
//
//                map.put("Abandon", "परित्याग करना/ छोड़ देना");
//                map.put("Abandonment of claim", "दावे का परित्याग");
//                map.put("Abatement", "कमी / कटौती / छू ट");
//                map.put("Abatement of duty", "शुल्क में कमी");
            }
        });
    }

  /*   class Get_Data extends AsyncTask<Void, Void, Void> {
        private ProgressDialog Dialog = new ProgressDialog(Hindi_Shabd_Activity.this);
        @Override
        protected void onPreExecute()
        {
            Dialog.setMessage("Please wait...");
            Dialog.setCancelable(false);
            Dialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            FirmListdata();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(catchhandl==0){

            }else{
                Toast.makeText(getApplicationContext(),
                        "Network Error...Please Try Again", Toast.LENGTH_LONG).show();
                Dialog.dismiss();


            }
            if (Dialog.isShowing()) {
                Dialog.dismiss();

            }
        }
    }

    private void FirmListdata() {
        catchhandl=0;
        final String NAMESPACE = Utility.NAMESPACE;
        final String URL = Utility.URL_OBC_SATHI_1;//"http://121.241.255.225/obcnew/site/OBC_OnlineApp.asmx";
        final String METHOD_NAME = "FatchExcelRecord";
        final String SOAP_ACTION =Utility.NAMESPACE+METHOD_NAME;

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
          //  list2=new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String Id   = jsonObject.getString("Id");
                String  EnglishWord  =jsonObject.getString("EnglishWord");
                String  HindiWord  =jsonObject.getString("HindiWord");
                String  createdDate  =jsonObject.getString("createdDate");

                try {
                    Databaseutill db;
                    GetData get;
                    db = Databaseutill.getDBAdapterInstance(Hindi_Shabd_Activity.this);
                    get = new GetData(db, Hindi_Shabd_Activity.this);
                    get.setSync_Data(Id,EnglishWord,HindiWord,createdDate);
                    ArrayList<HashMap<String, String>> listData = get.getSync_Data();

                } catch (Exception e) {

                }




            }

        } catch (Exception e) {
            catchhandl++;
            e.printStackTrace();
        }



    }
*/
    private void FirmListdata_REST_api() {
     //   Progress_Dialog.show_dialog(this);
      final   ProgressDialog  progressDialog = new ProgressDialog(Hindi_Shabd_Activity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }

        HashMap<String, String> hashMap = new HashMap<>();

        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.FatchExcelRecord(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<FetchExcel_Response>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                       // Progress_Dialog.hide_dialog();
                        try {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        } catch (Exception e1) {
                            // Log.e("pDialog", e.getMessage());
                        }


                        String error = e.getMessage();
                        Toast.makeText(getApplicationContext(),
                                "Network Error....", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<FetchExcel_Response> responseList) {




                        if (responseList != null && responseList.size() > 0) {

                            for (FetchExcel_Response productResponse : responseList) {
                                String  Id   =productResponse.getId();
                                String  EnglishWord  =productResponse.getEnglishWord();
                                String  HindiWord  =productResponse.getHindiWord();
                                String  createdDate  =productResponse.getCreatedDate();
                                try {
                                    Databaseutill db;
                                    GetData get;
                                    db = Databaseutill.getDBAdapterInstance(Hindi_Shabd_Activity.this);
                                    get = new GetData(db, Hindi_Shabd_Activity.this);
                                    get.setSync_Data(Id,EnglishWord,HindiWord,createdDate);
                                    ArrayList<HashMap<String, String>> listData = get.getSync_Data();

                                } catch (Exception e) {

                                }
                            }





                        }else {

                            Toast.makeText(getApplicationContext(),
                                    "Sorry, something went wrong there. Try again.", Toast.LENGTH_LONG).show();

                        }


                     //   Progress_Dialog.hide_dialog();

                        try {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        } catch (Exception e) {
                            // Log.e("pDialog", e.getMessage());
                        }


                    }
                });


    }

    private boolean isConnectingToInternet()
    {
        ConnectivityManager connectivity = (ConnectivityManager)Hindi_Shabd_Activity.this
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





}
