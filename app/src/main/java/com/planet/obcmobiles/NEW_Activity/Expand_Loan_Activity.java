package com.planet.obcmobiles.NEW_Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.planet.obcmobiles.Loan_Expandable_Adapter;
import com.planet.obcmobiles.Main3Activity;
import com.planet.obcmobiles.Model.ProductResponse;
import com.planet.obcmobiles.R;
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

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class Expand_Loan_Activity extends AppCompatActivity implements Animation.AnimationListener {
    private ExpandableListView expListView;
    private List<String> listDataHeader=new ArrayList<>();
    private List<String> listDataChild=new ArrayList<>();
    private String position, SubHeadingName, Description, position_spn, Name_spn, Name, hide = "";
    private int catchhandl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_expand);
        expListView = (ExpandableListView) findViewById(R.id.exp);
        ImageView back = (ImageView) findViewById(R.id.back);
        TextView set_name = (TextView) findViewById(R.id.set_name);

        Button apply_loan = (Button) findViewById(R.id.apply_loan);
        Button apply_now = (Button) findViewById(R.id.apply_now);
        Animation_image();
        Intent i = getIntent();
        if (i != null) {
            position = i.getStringExtra("position_main");
            Name = i.getStringExtra("Name");
            position_spn = i.getStringExtra("position");
            Name_spn = i.getStringExtra("name");
            hide = i.getStringExtra("hide");

            set_name.setText(Name);
            if (Name.equalsIgnoreCase("Oriental Home Loan Scheme") || Name.equalsIgnoreCase("Oriental Car/Vehicle Loan Scheme For General Public") || Name.equalsIgnoreCase("Oriental Education Loan Scheme") || Name.equalsIgnoreCase("MSME"))
                apply_loan.setVisibility(View.VISIBLE);
        } else {
            apply_loan.setVisibility(View.GONE);

        }

        if (Name.equalsIgnoreCase("Life insurance by Oriental bank") || Name.equalsIgnoreCase("OBC SBI Credit Card")
                || Name.equalsIgnoreCase("Oriental Mediclaim Policy") || Name.equalsIgnoreCase("PM Social Security Schemes") || Name.equalsIgnoreCase("Mutual Fund")) {
            apply_now.setVisibility(View.VISIBLE);
        } else {
           apply_now.setVisibility(View.GONE);

        }
        apply_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String url = "http://webapp.obcindia.co.in/content/oriental-bank-mediclaim";
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);

                Intent intent = new Intent(Expand_Loan_Activity.this, Mediclaim_Activity.class);
                intent.putExtra("Name", Name);
                startActivity(intent);
            }
        });


        apply_loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Name.equalsIgnoreCase("Oriental Home Loan Scheme")) {
                    Intent intent = new Intent(Expand_Loan_Activity.this, Main3Activity.class);
                    intent.putExtra("pos", 1);
                    startActivity(intent);
                } else if (Name.equalsIgnoreCase("Oriental Car/Vehicle Loan Scheme For General Public")) {
                    Intent intent = new Intent(Expand_Loan_Activity.this, Main3Activity.class);
                    intent.putExtra("pos", 2);
                    startActivity(intent);
                } else if (Name.equalsIgnoreCase("Oriental Education Loan Scheme")) {
                    Intent intent = new Intent(Expand_Loan_Activity.this, Main3Activity.class);
                    intent.putExtra("pos", 3);
                    startActivity(intent);
                } else if (Name.equalsIgnoreCase("MSME")) {
                    Intent intent = new Intent(Expand_Loan_Activity.this, Main3Activity.class);
                    intent.putExtra("pos", 5);
                    startActivity(intent);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(Expand_Loan_Activity.this, Recovery_and_Low_Activity.class);
//                i.putExtra("position",position_spn);
//                i.putExtra("name",Name_spn);
//                startActivity(i);
                finish();
            }
        });


        if (isConnectingToInternet() ) {
         //   new Product_Services().execute();
            Product_Services_REST_api();
        } else {
            Toast.makeText(getApplicationContext(),
                    "No Internet Connection...", Toast.LENGTH_LONG).show();
        }


        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {


                return false;
            }
        });


        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

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
        });


        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
//                // TODO Auto-generated method stub
//                Toast.makeText(
//                        getApplicationContext(),
//                        listDataHeader.get(groupPosition)
//                                + " : "
//                                + listDataChild.get(
//                                listDataHeader.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT)
//                        .show();
                return false;
            }
        });
    }

   /* private void product() {
        catchhandl = 0;
        final String NAMESPACE = Utility.NAMESPACE;
        final String URL = Utility.URL_OBC_SATHI;// "http://121.241.255.225/obcnew/Shathi_App.asmx";
        final String METHOD_NAME = "GetdatafromSub2Content";
        final String SOAP_ACTION = Utility.NAMESPACE + METHOD_NAME;


        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("HeadingId", position_spn);
        request.addProperty("SubHeadingId", position);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // put all required data into a soap
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);


        HttpTransportSE httpTransport = new HttpTransportSE(URL);
        try {
            httpTransport.call(SOAP_ACTION, envelope);
            KvmSerializable ks = (KvmSerializable) envelope.bodyIn;

//
//            Log.d("HTTP REQUEST ", httpTransport.requestDump);
//            Log.d("HTTP RESPONSE", httpTransport.responseDump);
//            String a = httpTransport.requestDump;
//            Object results = (Object) envelope.getResponse();
//            String   resultstring = results.toString();


            for (int j = 0; j < ks.getPropertyCount(); j++) {
                ks.getProperty(j);
            }
            String recved = ks.toString();
            String u = recved.substring(recved.indexOf("["));
            JSONArray jsonArray = new JSONArray(u);


            listDataHeader = new ArrayList<String>();
            listDataChild = new ArrayList<String>();
            List<String> file_name = new ArrayList<String>();
            List<String> page_link = new ArrayList<String>();
            List<String> descr = new ArrayList<String>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String SubHeadingId = jsonObject.getString("SubHeadingId");
                SubHeadingName = jsonObject.getString("SubHeadingName");
                String fileName = jsonObject.getString("fileName");
                String pagelink = jsonObject.getString("pagelink");
                Description = jsonObject.getString("Description");
                // file_name.add(fileName);
                // page_link.add(pagelink);
                listDataChild.add(Description);
                listDataHeader.add(SubHeadingName);
                //  listDataChild.addAll(descr);
                //  listDataChild.addAll(file_name);
                //   listDataChild.addAll(page_link);


            }

        } catch (Exception e) {
            catchhandl++;
            e.printStackTrace();
        }
    }
*/
    private void Product_Services_REST_api() {
        Progress_Dialog.show_dialog(this);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("HeadingId", position_spn);
        hashMap.put("SubHeadingId", position);

        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.FetchProduct(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ProductResponse>>() {
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
                    public void onNext(List<ProductResponse> responseList) {

                        Progress_Dialog.hide_dialog();

                        listDataHeader.clear();
                        listDataChild.clear();

                        if (responseList != null && responseList.size() > 0) {

                            for (ProductResponse productResponse : responseList) {
                                SubHeadingName = productResponse.getSubHeadingName();
                                Description = productResponse.getDescription();

                                listDataChild.add(Description);
                                listDataHeader.add(SubHeadingName);
                            }


                            try {
                                Loan_Expandable_Adapter listAdapter = new Loan_Expandable_Adapter(Expand_Loan_Activity.this, listDataHeader, listDataChild);
                                expListView.setAdapter(listAdapter);
                            } catch (Exception e) {
                            }


                        }else {

                            Toast.makeText(getApplicationContext(),
                                    "Sorry, something went wrong there. Try again.", Toast.LENGTH_LONG).show();

                        }




                    }
                });


    }

    private boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) Expand_Loan_Activity.this
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

    private void Animation_image() {
        Animation animation_blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        animation_blink.setAnimationListener(this);
        ImageButton button_anim = (ImageButton) findViewById(R.id.imageButton);
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

    /*class Product_Services extends AsyncTask<Void, Void, Void> {
        private ProgressDialog Dialog = new ProgressDialog(Expand_Loan_Activity.this);

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
                    // prepareListData();
                    Loan_Expandable_Adapter listAdapter = new Loan_Expandable_Adapter(Expand_Loan_Activity.this, listDataHeader, listDataChild);
                    expListView.setAdapter(listAdapter);

                    // coming_soon.setVisibility(View.GONE);


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
    }*/

}
