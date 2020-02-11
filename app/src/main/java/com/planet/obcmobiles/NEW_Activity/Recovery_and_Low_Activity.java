package com.planet.obcmobiles.NEW_Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.planet.obcmobiles.Adapter_new.Product_Services_Adapter;
import com.planet.obcmobiles.Loan_Expandable_Adapter;
import com.planet.obcmobiles.Model.Myspinner;
import com.planet.obcmobiles.Model.Product2_Response;
import com.planet.obcmobiles.Model.ProductResponse;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Admin on 11/9/2017.
 */

public class Recovery_and_Low_Activity extends AppCompatActivity implements Animation.AnimationListener {
    private Product_Services_Adapter adapter;
    private ArrayList<Product_model> arrayList=new ArrayList<>();
    Context context;
    private String position_str, item, str_pos, name;
    private Product_model item_list;
    //  private int  catchhandl;
    private RecyclerView recyclerView;
    private Spinner spn;

    private LinearLayoutManager layoutManager;
    private List categories=new ArrayList(), select;
    ArrayList<Myspinner> spnn;
    private TextView coming_soon;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recovery_and_low);
        Animation_image();
        ImageView back = (ImageView) findViewById(R.id.back);
        TextView product_name = (TextView) findViewById(R.id.product_name);
        coming_soon = (TextView)findViewById(R.id.coming_soon);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        arrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new Product_Services_Adapter(context, arrayList);
        recyclerView.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        context = getApplicationContext();

        Intent i = getIntent();
        if (i != null) {
            position_str = i.getStringExtra("position");
            name = i.getStringExtra("name");
            product_name.setText(name);
        }
        if (isConnectingToInternet() ) {
         //   new Product_Services().execute();
            Product_Services_REST_api();
        } else {
            Toast.makeText(getApplicationContext(),
                    "No Internet Connection...", Toast.LENGTH_LONG).show();
        }


        //  ArrayList<Product_model> product_model = prepareData();

        //   RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        // recyclerView.setLayoutManager(mLayoutManager);
        // recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        //  recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(Recovery_and_Low_Activity.this, Expand_Loan_Activity.class);
                        intent.putExtra("position_main", arrayList.get(position).getProduct_services_id());
                        intent.putExtra("Name", arrayList.get(position).getProduct_services());

                        intent.putExtra("position", position_str);
                        intent.putExtra("name", name);

                        startActivity(intent);
                       // finish();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


    }

//    private ArrayList<Product_model> prepareData() {
//        ArrayList<Product_model> android_version = new ArrayList<>();
//        for(int i=0;i<android_version_names.length;i++){
//            Product_model androidVersion = new Product_model();
//            androidVersion.setProduct_services(android_version_names[i]);
//
//            android_version.add(androidVersion);
//        }
//        return android_version;
//    }


 /*   class Product_Services extends AsyncTask<Void, Void, Void> {
        private ProgressDialog Dialog = new ProgressDialog(Recovery_and_Low_Activity.this);

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

            //   if(catchhandl==0){
            //try{
            adapter = new Product_Services_Adapter(context, arrayList);
            recyclerView.setAdapter(adapter);


            if(arrayList.isEmpty()){
                coming_soon.setVisibility(View.VISIBLE);
            }

//            if (spnn != null) {
//                try {
//
//                    ArrayAdapter<Myspinner> adapter = new ArrayAdapter<Myspinner>(Recovery_and_Low_Activity.this, android.R.layout.simple_spinner_dropdown_item, spnn);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spn.setAdapter(adapter);
//
////
////                    ArrayAdapter dataAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, select);
////                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////                    spn.setAdapter(dataAdapter);
//                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(),
//                            "Sorry, something went wrong there. Try again.", Toast.LENGTH_LONG).show();
//
//                }
          //  }
            //  }catch (Exception e){}
          *//*  }else{

                Toast.makeText(getApplicationContext(),
                        "Sorry, something went wrong there. Try again.", Toast.LENGTH_LONG).show();

            }*//*
            if (Dialog.isShowing()) {
                Dialog.dismiss();
            }
        }
    }

    private void product() {
        //    catchhandl=0;

        //private static final String SOAP_ADDRESS = "http://myStaticIP:portNo/CommunicationInterface.asmx"

        final String NAMESPACE = Utility.NAMESPACE;  //targetNamespace(NAME_SPACE)
        final String URL = Utility.URL_OBC_SATHI;//"http://121.241.255.225/obcnew/Shathi_App.asmx";
        final String METHOD_NAME = "GetDataSubHeading";

        final String SOAP_ACTION =Utility.NAMESPACE+METHOD_NAME;

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("HeadingId", position_str);


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
            categories = new ArrayList();
          //  spnn = new ArrayList<Myspinner>();
            //Myspinner spn = new Myspinner("---Select to Navigation---", "0");
           // spnn.add(spn);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                try {
                    String SubHeadingId = jsonObject.getString("SubHeadingId");
                    String SubHeadingName = jsonObject.getString("SubHeadingName");
                    item_list = new Product_model();


                    item_list.setProduct_services(SubHeadingName);
                    item_list.setProduct_services_id(SubHeadingId);
                    arrayList.add(item_list);

                } catch (Exception e) {
                }


            }

        } catch (Exception e) {
            //   catchhandl++;
            e.printStackTrace();
        }
    }
*/
    private void Product_Services_REST_api() {
        Progress_Dialog.show_dialog(this);
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("HeadingId", position_str);

        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.FetchProduct2(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Product2_Response>>() {
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
                    public void onNext(List<Product2_Response> responseList) {

                        Progress_Dialog.hide_dialog();

                        categories.clear();
                        arrayList.clear();
                        if (responseList != null && responseList.size() > 0) {

                            for (Product2_Response productResponse : responseList) {
                                item_list = new Product_model();
                                item_list.setProduct_services(productResponse.getSubHeadingName());
                                item_list.setProduct_services_id(productResponse.getSubHeadingId());
                                arrayList.add(item_list);
                                //Log.e("FetchProduct2",productResponse.getSubHeadingName());
                            }


                            try {
                                adapter = new Product_Services_Adapter(context, arrayList);
                                recyclerView.setAdapter(adapter);

                            } catch (Exception e) {
                            }


                        }else {

                            /*Toast.makeText(getApplicationContext(),
                                    "Sorry, something went wrong there. Try again.", Toast.LENGTH_LONG).show();
*/
                        }

                        if(arrayList.isEmpty()){
                            coming_soon.setVisibility(View.VISIBLE);
                        }


                    }
                });


    }

/*

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

*/

    private boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) Recovery_and_Low_Activity.this
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

//    @Override
//    public void onItemSelected(AdapterView parent, View view, int position, long id) {
//        item = parent.getItemAtPosition(position).toString();
//        int position_str1 = spn.getSelectedItemPosition();
//        str_pos = String.valueOf(position_str1);
//        Myspinner Spn_id = (Myspinner) spn.getSelectedItem();
//        String str_spn = Spn_id.getid();
//        if (str_pos.equalsIgnoreCase("0")) {
//
//        } else {
//            Intent intent = new Intent(Recovery_and_Low_Activity.this, Expand_Loan_Activity.class);
//
//
//            intent.putExtra("position_main", str_spn);
//            intent.putExtra("Name", spn.getSelectedItem().toString());
//
//            intent.putExtra("position", position_str);
//            intent.putExtra("name", name);
//            startActivity(intent);
//            finish();
//        }
//    }

//    public void onNothingSelected(AdapterView adapterView) {
//    }
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

}
