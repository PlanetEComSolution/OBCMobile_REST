package com.planet.obcmobiles;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.planet.obcmobiles.Model.ContactUsHeader_Response;
import com.planet.obcmobiles.Model.ContactUs_Response;
import com.planet.obcmobiles.Model.WhatsNewImage_Response;
import com.planet.obcmobiles.NEW_Activity.Expand_Loan_Activity;
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

public class Expadaiable extends Activity {


    final android.os.Handler mHandler = new android.os.Handler();
    Context context;
    String caption, URl;
    String aa;
    ArrayList<ArrayList> lists = new ArrayList<>();
    ArrayList<String> l = new ArrayList<>();
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    ArrayList<String> listDataHeader = new ArrayList<>(), listDataHeader1 = new ArrayList<>();
    HashMap<String, List<String>> listDataChild;
    List<String> top250 = new ArrayList<String>();
    ArrayList<String> call = new ArrayList<>(), missed = new ArrayList<>(),
            msg = new ArrayList<>(), email = new ArrayList<>(), fuck = new ArrayList<>();

    TextView t1, holidays;

    ImageView imageView;
    int inc = 1;
    private String data1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expadaiable);
        try {
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                Window window = Expadaiable.this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Expadaiable.this.getResources().getColor(R.color.Green_primary));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        prepareListData();
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        t1 = (TextView) findViewById(R.id.lblListHeader);
        holidays = (TextView) findViewById(R.id.holidays);
        TextView whats_new = (TextView) findViewById(R.id.whats_new);
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        whats_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Expadaiable.this, Expand_Loan_Activity.class);
                intent.putExtra("position_main", "85");
                intent.putExtra("Name", "Oriental Bank Rewardz");
                intent.putExtra("position", "2");
                intent.putExtra("name", "Digital Banking");
                startActivity(intent);

            }
        });

        expListView.setDivider(null);
        if (!haveNetworkConnection()) {
            Toast.makeText(getApplicationContext(), "No Internet Connection Found", Toast.LENGTH_SHORT).show();
        } else {
            // new Contact_Data().execute();
           WhatsnewImage_REST_api();
        }


        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog alertDialog = new SweetAlertDialog(Expadaiable.this, SweetAlertDialog.WARNING_TYPE);
                alertDialog.setContentText("NEVER SHARE your Card Number, CVV, PIN, OTP, Internet Banking User ID or Password with anyone as it can lead to unauthorized access to your account.");
                alertDialog.setTitleText("BE Aware!");
                alertDialog.show();
            }
        });
        holidays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Expadaiable.this, FullscreenImageView.class));

            }
        });
        // prepareListData();
        // listAdapter = new com.planet.obcmobile.ExpandableListAdapter(Expadaiable.this, listDataHeader, listDataChild);
        // expListView.setAdapter(listAdapter);
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
               /* if (groupPosition == 4) {
                    final Dialog dialog = new Dialog(Expadaiable.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setContentView(R.layout.popup);
                    dialog.setCancelable(false);
                    ImageView imageView = (ImageView) dialog.findViewById(R.id.popup_image);
                    try {
                        Picasso.with(Expadaiable.this).load(URl).into(imageView);
                    } catch (Exception e) {

                    }
                    ImageButton button = (ImageButton) dialog.findViewById(R.id.imageButton3);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                        }
                    });
                    dialog.show();

                }else*/
                return false;
            }
        });
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (groupPosition == 3) {
                   // System.out.println("group position" + groupPosition);
                    String number = expListView.getItemAtPosition(childPosition + inc + 1 + 1 + 1).toString();
                    email(number);
                }
                if (groupPosition == 2) {
                   // System.out.println("group position" + groupPosition);
                    String number = expListView.getItemAtPosition(childPosition + inc + 1 + 1).toString();
                    if (number.contains(".")) {
                        String data[] = number.split("\\.");
                        String data1 = data[1].toString();
                        String data2 = data[0].toString();
                        message(data1, data2);
                    }
                }
                if (groupPosition == 1) {
                    //System.out.println("group position" + groupPosition);
                    String number_mis = expListView.getItemAtPosition(childPosition + inc + 1).toString();
                    if (number_mis.contains(".")) {
                        String data[] = number_mis.split("\\.");
                        data1 = data[1].toString();
                        String data2 = data[0].toString();
                        if (isPermissionGranted()) {
                            calling(data1);
                        }
                    }
                }
                if (groupPosition == 0) {

                    String number_call = expListView.getItemAtPosition(childPosition + inc).toString();
                    String data[] = number_call.split("\\.");
                    try {
                        String data1 = data[1].toString();


                        if (isPermissionGranted()) {
                            calling(data1);
                        }
                    } catch (Exception e) {
                    }
                }

//                if (groupPosition == 0) {
//
//                    Toast.makeText(getApplicationContext(), "No Internet Connection Found", Toast.LENGTH_SHORT).show();
//
//                }


                return true;

            }
        });


        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                                                 int previousGroup = -1;

                                                 @Override
                                                 public void onGroupExpand(int groupPosition) {
                                                     if (groupPosition != previousGroup)
                                                         expListView.collapseGroup(previousGroup);
                                                     previousGroup = groupPosition;

                                                 }
                                             }

        );

    }


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        // Adding child data
        listDataHeader.add("Call Us");
        listDataHeader.add("Missed Call Banking");
        listDataHeader.add("SMS Banking");
        listDataHeader.add("Mail Us");
        //    listDataHeader.add("Bank Holidays 2019");



       /*listDataChild.put(listDataHeader.get(0), call); // Header, Child data
        listDataChild.put(listDataHeader.get(1), missed);
        listDataChild.put(listDataHeader.get(2), msg);
        listDataChild.put(listDataHeader.get(3), email);*/
    }


    public void calling(String a) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + a));
        startActivity(callIntent);

    }

    public void message(String a, String b) {
        if (b.contains("*Available only on registered Mobile numbers")) {
           // Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
        } else {
            Intent callIntent = new Intent(Intent.ACTION_VIEW);
            callIntent.setData(Uri.parse("sms:" + a));
            if (b.contains("SMS"))
                callIntent.putExtra("sms_body", " ");
            if (b.contains("ACBAL"))
                callIntent.putExtra("sms_body", "ACBAL ");
            if (b.contains("STM"))
                callIntent.putExtra("sms_body", "STM ");
            if (b.contains("STOP"))
                callIntent.putExtra("sms_body", "STOP ");
            if (b.contains("BLOCK"))
                callIntent.putExtra("sms_body", "BLOCK ");
            if (callIntent.resolveActivity(Expadaiable.this.getPackageManager()) != null) {
                startActivity(callIntent);
            }
        }
    }

    public void email(String a) {
        String[] TO = {a};
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, TO);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Customer service");
        //intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
        startActivity(Intent.createChooser(intent, "Send Email"));

    }

  /*  public void contacts() {
        final String NAMESPACE = Utility.NAMESPACE;
        final String URL = Utility.URL_OBC_CMS;//"http://121.241.255.225/obcnew/site/OBC_CMS_App.asmx";
        final String METHOD_NAME = "ContactUs";
        final String SOAP_ACTION = Utility.NAMESPACE + METHOD_NAME;// "http://tempuri.org/insertrecord";


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
           // Log.d("CONTACTS", recved);
            String u = recved.substring(recved.indexOf("["));
            //Log.d("CONTACTS", u);
            JSONArray jsonArray = new JSONArray(u);
            call = new ArrayList<String>();
            msg = new ArrayList<String>();
            missed = new ArrayList<String>();
            email = new ArrayList<String>();
            fuck = new ArrayList<String>();
            lists = new ArrayList<>();
            l = new ArrayList();
            listDataChild = new HashMap<String, List<String>>();
            // missed.add("*Available only on registered Mobile numbers");
            // msg.add("*Available only on registered Mobile numbers");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String caption = obj.getString("Caption");
                String cat_name = obj.getString("category");

                if (cat_name.equals("Call Us")) {
                    call.add(caption);
                    // String a = call.get(i);
                    //Log.d("CONTACT TOP", "" + a + "A_DATA");
                }
                if (cat_name.equals("Missed Call Banking")) {
                    missed.add(caption);

                }
                if (cat_name.equals("SMS Banking")) {
                    msg.add(caption);
                }
                if (cat_name.equals("Mail Us")) {
                    email.add(caption);
                }
            }
            ArrayList<String> list_blank = new ArrayList<>();
            list_blank.add(caption);
            lists.add(call);
            lists.add(missed);
            lists.add(msg);
            lists.add(email);
            lists.add(list_blank);
            lists.add(fuck);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
    private void Contact_Data_REST_api() {
        //   Progress_Dialog.show_dialog(this);
        HashMap<String, String> hashMap = new HashMap<>();

        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.ContactUs(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ContactUs_Response>>() {
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
                    public void onNext(List<ContactUs_Response> responseList) {



                        call.clear();
                        msg.clear();
                        missed.clear();
                        email.clear();
                        fuck.clear();
                        lists.clear();
                        l.clear();
                        listDataChild = new HashMap<String, List<String>>();


                        if (responseList != null && responseList.size() > 0) {

                            for (ContactUs_Response contactUs_response : responseList) {
                                String caption = contactUs_response.getVR_ContactCaption();
                                String cat_name = contactUs_response.getVR_Category();
                               // Log.e("ContactUs",cat_name);
                                if (cat_name.equals("Call Us")) {
                                    call.add(caption);

                                }
                                if (cat_name.equals("Missed Call Banking")) {
                                    missed.add(caption);

                                }
                                if (cat_name.equals("SMS Banking")) {
                                    msg.add(caption);
                                }
                                if (cat_name.equals("Mail Us")) {
                                    email.add(caption);
                                }
                            }

                            ArrayList<String> list_blank = new ArrayList<>();
                            list_blank.add(caption);
                            lists.add(call);
                            lists.add(missed);
                            lists.add(msg);
                            lists.add(email);
                            lists.add(list_blank);
                            lists.add(fuck);


                            try {
                                for (int i = 0; i < listDataHeader.size(); i++) {
                                    listDataChild.put(listDataHeader.get(i), lists.get(i));
                                  //  Log.d("BHANU_DATA", "" + listDataChild.put(listDataHeader.get(i), lists.get(i)));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            listAdapter = new com.planet.obcmobiles.ExpandableListAdapter(Expadaiable.this, listDataHeader, listDataChild);
                            expListView.setAdapter(listAdapter);


                        } else {

                            Toast.makeText(getApplicationContext(),
                                    "Sorry, something went wrong there. Try again.", Toast.LENGTH_LONG).show();

                        }


                        Progress_Dialog.hide_dialog();


                    }
                });


    }


    private void Contact_Header_REST_api() {
        //   Progress_Dialog.show_dialog(this);
        HashMap<String, String> hashMap = new HashMap<>();

        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.ContactUsHeader(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ContactUsHeader_Response>>() {
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
                    public void onNext(List<ContactUsHeader_Response> responseList) {
                     //   Log.e("ContactUsHeader",responseList.toString());
                        listDataHeader1.clear();

                        if (responseList != null && responseList.size() > 0) {
                            for (ContactUsHeader_Response contactUsHeader_response : responseList) {
                                String headerName = contactUsHeader_response.getVR_Category();
                                listDataHeader1.add(headerName);
                            }


                        } else {

                            Toast.makeText(getApplicationContext(),
                                    "Sorry, something went wrong there. Try again.", Toast.LENGTH_LONG).show();

                        }


                        //     Progress_Dialog.hide_dialog();

                        Contact_Data_REST_api();

                    }
                });


    }


 /*   public void contactsHeader() {
        final String NAMESPACE = Utility.NAMESPACE;
        final String URL = Utility.URL_OBC_CMS;//"http://121.241.255.225/obcnew/site/OBC_CMS_App.asmx";
        final String METHOD_NAME = "ContactUsHeader";

        final String SOAP_ACTION = Utility.NAMESPACE + METHOD_NAME;// "http://tempuri.org/insertrecord";


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
            //Log.d("CONTACTS_HEADER", recved);
            String u = recved.substring(recved.indexOf("["));
           // Log.d("CONTACTS_HEADER", u);
            JSONArray jsonArray = new JSONArray(u);
            listDataHeader1 = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String headerName = jsonObject.getString("VR_Category");
                listDataHeader1.add(headerName);
                String l = listDataHeader1.get(i);
               // Log.d("CONTACTS_HEADER", l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

  /*  public void WhatsnewImage() {


        final String NAMESPACE = Utility.NAMESPACE;
        final String URL = Utility.URL_OBC_CMS;//"http://121.241.255.225/obcnew/site/OBC_CMS_App.asmx";
        final String METHOD_NAME = "WhatsNewImage";

        final String SOAP_ACTION = Utility.NAMESPACE + METHOD_NAME;// "http://tempuri.org/insertrecord";

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
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                caption = jsonObject.getString("Caption");
                URl = jsonObject.getString("URL");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
    private void WhatsnewImage_REST_api() {
        Progress_Dialog.show_dialog(this);
        HashMap<String, String> hashMap = new HashMap<>();

        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.WhatsNewImage(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<WhatsNewImage_Response>>() {
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
                    public void onNext(List<WhatsNewImage_Response> responseList) {

                       // Log.e("WhatsNewImage",responseList.toString());
                        if (responseList != null && responseList.size() > 0) {

                            caption = responseList.get(0).getCaption();
                            URl = responseList.get(0).getURL();

                        } else {

                            Toast.makeText(getApplicationContext(),
                                    "Sorry, something went wrong there. Try again.", Toast.LENGTH_LONG).show();

                        }


                        Contact_Header_REST_api();


                    }
                });


    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    calling(data1);
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

   /* class Contact_Data extends AsyncTask<Void, Void, Void> {
        SweetAlertDialog alertDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog = new SweetAlertDialog(Expadaiable.this, SweetAlertDialog.PROGRESS_TYPE);
            alertDialog.setTitleText("Please Wait...");
            alertDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            WhatsnewImage();
            contactsHeader();
            contacts();


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();

            }

            try {
                for (int i = 0; i < listDataHeader.size(); i++) {
                    listDataChild.put(listDataHeader.get(i), lists.get(i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            listAdapter = new com.planet.obcmobiles.ExpandableListAdapter(Expadaiable.this, listDataHeader, listDataChild);
            expListView.setAdapter(listAdapter);


        }
    }
*/

}