package com.planet.obcmobiles;

/**
 * Created by planet on 11/5/15.
 */


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


//import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.planet.obcmobiles.Model.ComplaintStatusResponse;
import com.planet.obcmobiles.Model.UUIDInsertResponse;
import com.planet.obcmobiles.network.API_Client;
import com.planet.obcmobiles.network.API_Interface;
import com.planet.obcmobiles.progress_dialog.Progress_Dialog;
import com.scottyab.rootbeer.RootBeer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.android.volley.VolleyLog.TAG;

public class Splash extends Activity {
  // String regId;
  //  private ProgressDialog progDailog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        CheckDeviceRootStatus();
      //  GCMRegistrar.checkDevice(this);
       // GCMRegistrar.checkManifest(this);
       // regId = GCMRegistrar.getRegistrationId(this);

        //System.out.println("-----my register ID-----" + regId);

       /* if (regId.equals("")) {
            // Registration is not present, register now with GCM
            GCMRegistrar.register(this, "418879595440");
        } else {
            if (GCMRegistrar.isRegisteredOnServer(this)) {
               // System.out.println("already registe");
            }

        }*/


        getTOKEN();
     /*   Intent intent = new Intent(Splash.this, Home.class);
        startActivity(intent);*/


        try {
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                Window window = Splash.this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Splash.this.getResources().getColor(R.color.Green_primary));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
/*    public void GCM_Register_Id() {

        final String NAMESPACE = Utility.NAMESPACE;
        final String URL = Utility.URL_OBC_SITE;
        final String METHOD_NAME = "UUIDInsert";
        final String SOAP_ACTION =Utility.NAMESPACE+METHOD_NAME;// "http://tempuri.org/insertrecord";




        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("UUID", regId);
    //    Log.d("REG_ID",regId);
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
               String a=obj.getString("success");
              //  Log.d("REG_ID",a);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private void GCM_Register_Id_REST_API(String Token) {

        HashMap<String, String> hashMap = new HashMap<>();


        hashMap.put("UUID", Token);
        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.UUIDInsert(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<UUIDInsertResponse>>() {
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
                    public void onNext(List<UUIDInsertResponse> responseList) {

                        Progress_Dialog.hide_dialog();

                        Intent intent = new Intent(Splash.this, Home.class);
                        startActivity(intent);
                        finish();


                    }
                });


    }

    private void getTOKEN(){
        Progress_Dialog.show_dialog(this);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;

                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        GCM_Register_Id_REST_API(token);

                    }

                });

    }


   /* class RegisterdId_post extends AsyncTask<Void,Void,Void>{
        SweetAlertDialog alertDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog = new SweetAlertDialog(Splash.this, SweetAlertDialog.PROGRESS_TYPE);
            alertDialog.setTitleText("Loading Please Wait...");
            alertDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
           GCM_Register_Id();
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
                Intent intent = new Intent(Splash.this, Home.class);
                startActivity(intent);
                finish();
            }
        }
        }
       */


    private void CheckDeviceRootStatus() {
        //Check device is rooted or not!
        RootBeer rootBeer = new RootBeer(Splash.this);
        if (rootBeer.isRootedWithoutBusyBoxCheck()) {
            //we found indication of root
            Toast.makeText(getApplicationContext(),
                    "This device is rooted!\nThis application can't be used on this device.", Toast.LENGTH_SHORT).show();
            finish();

        }



     /*   if (RootUtil.isDeviceRooted()) {
            //we found indication of root
            Toast.makeText(getApplicationContext(), "This device is rooted!\nThis application can't be used on this device.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }*/

//Check device is rooted or not!
    }
    }
