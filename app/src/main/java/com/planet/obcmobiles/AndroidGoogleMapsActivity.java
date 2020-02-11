package com.planet.obcmobiles;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.planet.obcmobiles.Model.NearestBranchesByPin_Response;
import com.planet.obcmobiles.Model.NearestBranches_Response;
import com.planet.obcmobiles.network.API_Client;
import com.planet.obcmobiles.network.API_Interface;
import com.planet.obcmobiles.progress_dialog.Progress_Dialog;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AndroidGoogleMapsActivity extends FragmentActivity
        implements AdapterView.OnItemClickListener, GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnInfoWindowClickListener, Animation.AnimationListener, OnMapReadyCallback {
    private static final long INTERVAL = 0;
    private static final long FASTEST_INTERVAL = 1;
    static String addr;
    private static String result;
    private static double latitude, longitude;
    //Location location;
    static private String first;
    final Handler mHandler = new Handler();
    // Google Map
    public GoogleMap googleMap;
    boolean isGPSEnabled = false;
    boolean check = true;
    SweetAlertDialog alertDialog;
    double amt, amt1, bmt, bmt1;
    View myContentsView;
    List<String> Atmname = new ArrayList<>();
    ArrayList<String> Atmaddress = new ArrayList<>();
    ArrayList<String> Atmdistance = new ArrayList<>();
    ArrayList<String> Atmtype = new ArrayList<>();
    LocationManager locationManager;
    ArrayList<String> branchname = new ArrayList<>();
    ArrayList<String> branchaddress = new ArrayList<>();
    ArrayList<String> branchdistance = new ArrayList<>();
    ArrayList<String> branchtype = new ArrayList<>();
    int logioc = 0;
    LocationRequest locationRequest;
    ArrayList<String> atmlatList = new ArrayList<>();
    ArrayList<String> atmlongList = new ArrayList<>();
    int null_data = -1;
    ArrayList<String> branchlatList = new ArrayList<>();
    ArrayList<String> branchlongList = new ArrayList<>();
    ArrayList<String> branchaddress_list = new ArrayList<>();
    int check_value;
    int buttonValue = 0;
    int flag = 10;
    int aaa;
    String pin_code;
    String pin_lati, pin_longi, pin_type, pin_name_data;
    ArrayList<String> pin_Ltude = new ArrayList<>();
    ArrayList<String> pin_Long = new ArrayList<>();
    ArrayList<String> pin_B_Ltude = new ArrayList<>();
    ArrayList<String> pin_B_Long = new ArrayList<>();
    ArrayList<String> pin_name = new ArrayList<>();
    ArrayList<String> pin_B_name = new ArrayList<>();
    String atm_indx, bran_indx, bran_index_name;
    ArrayList<String> myAtmbranch = new ArrayList<String>();
    ArrayList<String> mydistance = new ArrayList<String>();
    ArrayList<String> myAtmadddress = new ArrayList<String>();
    ArrayList<String> lat_total = new ArrayList<>(), longi_total = new ArrayList<>();
    String atm_longi_index, branch_longi_index;
    double pinbranchl_LAT, pinbranch_longi;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    boolean isFromDetailActivity = false;
    private Button button, header_button;
    private Button btn;
    private Marker marker1, marker2;
    private LatLng latLng;
    private Button branch, atm;
    private JSONArray jsonArray;
    private Intent i;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        branch = (Button) findViewById(R.id.button_branch);
        atm = (Button) findViewById(R.id.button_ATM);
        btn = (Button) findViewById(R.id.button_home);
        button = (Button) findViewById(R.id.muliple);
        button.setVisibility(View.GONE);
        header_button = (Button) findViewById(R.id.tital_button);
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = AndroidGoogleMapsActivity.this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(AndroidGoogleMapsActivity.this.getResources().getColor(R.color.Green_primary));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**/

        initializeMapNew();

        /**/

/** Pin code Functionalty with INTENT **/
        Intent intent_pin = getIntent();
        check_value = intent_pin.getIntExtra("CHECK", 1);
        if (check_value == 0) {
            pin_code = intent_pin.getStringExtra("PIN_NUMBER");
            branch.setVisibility(View.GONE);
            atm.setVisibility(View.GONE);
            // Log.d("Bhanu Value", check_value + " " + pin_code);
            //pincode(pin_code);
        } else {
            branch.setVisibility(View.VISIBLE);
            atm.setVisibility(View.VISIBLE);
        }

        header_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AndroidGoogleMapsActivity.this, Home.class);
                startActivity(i);
                finish();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AndroidGoogleMapsActivity.this, Home.class);
                startActivity(i);
                AndroidGoogleMapsActivity.this.finish();

            }
        });


        i = getIntent();
        flag = i.getIntExtra("value", 1);
        aaa = i.getIntExtra("value2", 4);
        isFromDetailActivity = i.getBooleanExtra("isFromDetailActivity", false);

        /**/
        if (isFromDetailActivity) {
            try {
                atmlatList = i.getStringArrayListExtra("ATM_LAT_LIST");
                atmlongList = i.getStringArrayListExtra("ATM_LONG_LIST");
                Atmaddress = i.getStringArrayListExtra("ATM_ADDRESS");
                branchlatList = i.getStringArrayListExtra("BRANCH_LAT_LIST");
                branchlongList = i.getStringArrayListExtra("BRANCH_LONG_LIST");
                myAtmbranch = i.getStringArrayListExtra("b");
                mydistance = i.getStringArrayListExtra("d");
                myAtmadddress = i.getStringArrayListExtra("ad");
                Atmname = i.getStringArrayListExtra("ATM_NAME");
                Atmdistance = i.getStringArrayListExtra("ATM_DISTANCE");
                branchname = i.getStringArrayListExtra("BRANCH_NAME");
                branchdistance = i.getStringArrayListExtra("BRANCH_DISTANCE");
                branchaddress = i.getStringArrayListExtra("BRANCH_ADDRESS");

            } catch (Exception e) {
            }
        }
        /**/


        if (aaa == 2) {
            //bhanu
            Intent intent = getIntent();
            String ool = intent.getStringExtra("BRANCH");
            String oll = intent.getStringExtra("BRANCH1");
            final String ooll = intent.getStringExtra("BRANCH_ADDRESS_POSITION");

            bmt = Double.parseDouble(ool);
            bmt1 = Double.parseDouble(oll);

            final Runnable mUpdateResults = new Runnable() {
                public void run() {

                    Branch(bmt, bmt1, ooll);
                }
            };
            Thread t = new Thread() {
                public void run() {

                    mHandler.post(mUpdateResults);
                }
            };
            t.start();
        }

        if (aaa == 0) {


            Intent intent = getIntent();

            String blat = intent.getStringExtra("BRANCH");
            String blong = intent.getStringExtra("BRANCH1");

            bmt = Double.parseDouble(blat);
            bmt1 = Double.parseDouble(blong);

            String alat = intent.getStringExtra("ATM");
            String along = intent.getStringExtra("ATM1");
            amt = Double.parseDouble(alat);
            amt1 = Double.parseDouble(along);


            final Runnable mUpdateResults = new Runnable() {
                public void run() {
                    //Branch(bmt, bmt1);
                    //ATM(amt, amt1);

                }
            };

            Thread t = new Thread() {
                public void run() {

                    mHandler.post(mUpdateResults);
                }
            };
            t.start();
        }

        if (flag == 0) {

            atmlatList = i.getStringArrayListExtra("ATM_LAT_LIST");
            atmlongList = i.getStringArrayListExtra("ATM_LONG_LIST");
            branchlatList = i.getStringArrayListExtra("BRANCH_LAT_LIST");
            branchlongList = i.getStringArrayListExtra("BRANCH_LONG_LIST");


            final Runnable mUpdateResults = new Runnable() {
                public void run() {

                    for (int j = 0; j < atmlatList.size(); j++) {

                        String l1 = atmlatList.get(j);
                        String l2 = atmlongList.get(j);

                        double dl1 = Double.parseDouble(l1);
                        double dl2 = Double.parseDouble(l2);


                        // ATM(dl1, dl2, address);


                        String bl1 = branchlatList.get(j);
                        String bl2 = branchlongList.get(j);


                        double branchl1 = Double.parseDouble(bl1);
                        double branchl2 = Double.parseDouble(bl2);

                        // Branch(branchl1, branchl2,branch_addr);


                    }

                }
            };

            Thread t = new Thread() {
                public void run() {

                    mHandler.post(mUpdateResults);
                }
            };
            t.start();


        }
        if (flag == 2) {


            final Runnable mUpdateResults = new Runnable() { //handler for ui update on map, for multiple location

                public void run() {

                    for (int j = 0; j < branchlatList.size(); j++) {

                        String bl1 = branchlatList.get(j);
                        String bl2 = branchlongList.get(j);
                        String br_address = branchaddress.get(j);

                        double branchl1 = Double.parseDouble(bl1);
                        double branchl2 = Double.parseDouble(bl2);

                        Branch(branchl1, branchl2, br_address);


                    }

                }
            };

            Thread t = new Thread() {
                public void run() {
                    mHandler.post(mUpdateResults);
                }
            };
            t.start();
        }


        if (flag == 3) {


            final Runnable mUpdateResults = new Runnable() {
                public void run() {

                    for (int j = 0; j < atmlatList.size(); j++) {

                        String l1 = atmlatList.get(j);
                        String l2 = atmlongList.get(j);
                        String address_atm = Atmaddress.get(j);

                        double dl1 = Double.parseDouble(l1);
                        double dl2 = Double.parseDouble(l2);


                        ATM(dl1, dl2, address_atm);
                    }
                }
            };

            Thread t = new Thread() {
                public void run() {
                    mHandler.post(mUpdateResults);
                }
            };
            t.start();
        }


       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (latitude == 0 && longitude == 0) {
                    SweetAlertDialog alertDialog = new SweetAlertDialog(AndroidGoogleMapsActivity.this, SweetAlertDialog.ERROR_TYPE);
                    alertDialog.setTitleText("Error");
                    alertDialog.setContentText("GPS not working! Please check your network.");
                    alertDialog.show();

                } else {
                    Intent i = new Intent(AndroidGoogleMapsActivity.this, AllDetails.class);

                    i.putExtra("value1", buttonValue);
                    i.putStringArrayListExtra("b", myAtmbranch);
                    i.putStringArrayListExtra("d", mydistance);
                    i.putStringArrayListExtra("ad", myAtmadddress);
                    i.putStringArrayListExtra("ATM_LAT", atmlatList);
                    i.putStringArrayListExtra("ATM_LONG", atmlongList);
                    i.putStringArrayListExtra("ATM_NAME", (ArrayList<String>) Atmname);
                    i.putStringArrayListExtra("ATM_ADDRESS", Atmaddress);
                    i.putStringArrayListExtra("ATM_DISTANCE", Atmdistance);
                    i.putStringArrayListExtra("BRANCH_LAT", branchlatList);
                    i.putStringArrayListExtra("BRANCH_LONG", branchlongList);
                    i.putStringArrayListExtra("BRANCH_NAME", branchname);
                    i.putStringArrayListExtra("BRANCH_ADDRESS", branchaddress);
                    i.putStringArrayListExtra("BRANCH_DISTANCE", branchdistance);

                    startActivity(i);
                    button.setBackgroundColor(Color.WHITE);
                    button.setTextColor(Color.BLACK);
                    btn.setTextColor(Color.WHITE);
                    branch.setTextColor(Color.WHITE);
                    atm.setTextColor(Color.WHITE);
                    atm.setBackgroundColor(Color.parseColor("#4bb134"));
                    branch.setBackgroundColor(Color.parseColor("#4bb134"));
                }
            }
        });*/
        //new MAP().execute();


        try {

            if (ActivityCompat.checkSelfPermission(AndroidGoogleMapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestLocationPermission();

            } else {
                // Toast.makeText(AndroidGoogleMapsActivity.this,"Permission granted",Toast.LENGTH_SHORT).show();
                initilizeMap();
            }
            //      initilizeMap();

        } catch (Exception e) {

            e.printStackTrace();
        }

        if (aaa == 3) {
            //bhanu
            Intent intent = getIntent();
            String ool = intent.getStringExtra("ATM");
            String oll = intent.getStringExtra("ATM1");
            final String ooll = intent.getStringExtra("ATM_ADDRESS_POSITION");

            amt = Double.parseDouble(ool);
            amt1 = Double.parseDouble(oll);

            final Runnable mUpdateResults = new Runnable() {
                public void run() {

                    ATM(amt, amt1, ooll);
                }
            };
            Thread t = new Thread() {
                public void run() {

                    mHandler.post(mUpdateResults);
                }
            };
            t.start();
        }
      /*  if (isConnectingToInternet() == false)
        {
            SweetAlertDialog alertDialog = new SweetAlertDialog(AndroidGoogleMapsActivity.this, SweetAlertDialog.ERROR_TYPE);
            alertDialog.setTitleText("Error");
            alertDialog.setContentText("GPS not working! Please check your network.");
            alertDialog.show();
        }
        else
        {
            new AsynkData().execute();
        }*/

        branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (latitude == 0 && longitude == 0) {
                    SweetAlertDialog alertDialog = new SweetAlertDialog(AndroidGoogleMapsActivity.this, SweetAlertDialog.ERROR_TYPE);
                    alertDialog.setTitleText("Error");
                    alertDialog.setContentText("GPS not working! Please check your network.");
                    alertDialog.show();

                } else {
                    buttonValue = 2;
                    Intent i = new Intent(AndroidGoogleMapsActivity.this, AllDetails.class);
                    i.putExtra("value1", buttonValue);
                    i.putStringArrayListExtra("b", myAtmbranch);
                    i.putStringArrayListExtra("d", mydistance);
                    i.putStringArrayListExtra("ad", myAtmadddress);
                    i.putStringArrayListExtra("ATM_LAT", atmlatList);
                    i.putStringArrayListExtra("ATM_LONG", atmlongList);
                    i.putStringArrayListExtra("ATM_NAME", (ArrayList<String>) Atmname);
                    i.putStringArrayListExtra("ATM_ADDRESS", Atmaddress);
                    i.putStringArrayListExtra("ATM_DISTANCE", Atmdistance);
                    i.putStringArrayListExtra("BRANCH_LAT", branchlatList);
                    i.putStringArrayListExtra("BRANCH_LONG", branchlongList);
                    i.putStringArrayListExtra("BRANCH_NAME", branchname);
                    i.putStringArrayListExtra("BRANCH_ADDRESS", branchaddress);
                    i.putStringArrayListExtra("BRANCH_DISTANCE", branchdistance);
                    startActivity(i);
                    finish();
                }
                /*else {
                    buttonValue = 2;
                    googleMap.clear();
                    isConnectingToInternet();

                    String bl1 = branchlatList.get(0);
                    String bl2 = branchlongList.get(0);

                    double a = Double.parseDouble(bl1);
                    double b = Double.parseDouble(bl2);

                    Branch(a, b);
                    new AsynkData().execute();


                }*/
                branch.setBackgroundColor(Color.WHITE);
                branch.setTextColor(Color.BLACK);
                atm.setTextColor(Color.WHITE);
                button.setTextColor(Color.WHITE);
                btn.setTextColor(Color.WHITE);

                atm.setBackgroundColor(Color.parseColor("#4bb134"));
                button.setBackgroundColor(Color.parseColor("#4bb134"));

            }
        });

        atm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!isConnectingToInternet()) {

                }
                if (latitude == 0 && longitude == 0) {
                    SweetAlertDialog alertDialog = new SweetAlertDialog(AndroidGoogleMapsActivity.this, SweetAlertDialog.ERROR_TYPE);
                    alertDialog.setTitleText("Error");
                    alertDialog.setContentText("GPS not working! Please check your network.");
                    alertDialog.show();

                } else {
                    buttonValue = 3;
                    Intent i = new Intent(AndroidGoogleMapsActivity.this, AllDetails.class);
                    i.putExtra("value1", buttonValue);
                    i.putStringArrayListExtra("b", myAtmbranch);
                    i.putStringArrayListExtra("d", mydistance);
                    i.putStringArrayListExtra("ad", myAtmadddress);
                    i.putStringArrayListExtra("ATM_LAT", atmlatList);
                    i.putStringArrayListExtra("ATM_LONG", atmlongList);
                    i.putStringArrayListExtra("ATM_NAME", (ArrayList<String>) Atmname);
                    i.putStringArrayListExtra("ATM_ADDRESS", Atmaddress);
                    i.putStringArrayListExtra("ATM_DISTANCE", Atmdistance);
                    i.putStringArrayListExtra("BRANCH_LAT", branchlatList);
                    i.putStringArrayListExtra("BRANCH_LONG", branchlongList);
                    i.putStringArrayListExtra("BRANCH_NAME", branchname);
                    i.putStringArrayListExtra("BRANCH_ADDRESS", branchaddress);
                    i.putStringArrayListExtra("BRANCH_DISTANCE", branchdistance);
                    startActivity(i);
                    finish();
                }

                /* else {

                    buttonValue = 3;
                    googleMap.clear();
                    String l1 = atmlatList.get(0);
                    String l2 = atmlongList.get(0);
                    double c = Double.parseDouble(l1);
                    double d = Double.parseDouble(l2);
                    ATM(c, d);
                    new AsynkData().execute();

                }*/
                atm.setBackgroundColor(Color.WHITE);
                atm.setTextColor(Color.BLACK);
                button.setTextColor(Color.WHITE);
                btn.setTextColor(Color.WHITE);
                branch.setTextColor(Color.WHITE);
                branch.setBackgroundColor(Color.parseColor("#4bb134"));
                button.setBackgroundColor(Color.parseColor("#4bb134"));


            }
        });

       /* home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConnectingToInternet() == false) {

                } else {

                    buttonValue = 0;
                    googleMap.clear();

                    String l1 = atmlatList.get(0);
                    String l2 = atmlongList.get(0);
                    double c = Double.parseDouble(l1);
                    double d = Double.parseDouble(l2);
                    ATM(c, d);

                    String bl1 = branchlatList.get(0);
                    String bl2 = branchlongList.get(0);

                    double a = Double.parseDouble(bl1);
                    double b = Double.parseDouble(bl2);
                    System.out.println("chut" + a);
                    System.out.println("chut" + b);
                    Branch(a, b);

                    new AsynkData().execute();
                }


            }
        });*/

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void drawline(double mlatitude, double mlongitude) {
        try {
            LatLng CurrentlatLng = new LatLng(latitude, longitude);
            Polyline line = googleMap.addPolyline(new PolylineOptions().add(CurrentlatLng).add(new LatLng(mlatitude, mlongitude)).width(5).color(Color.parseColor("#3e8e3e")));

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        // Do stuff with the map here!
        googleMap = gMap;
       // Log.e("onMapReady", gMap.isIndoorEnabled() + "");
        try {
            // Enabling MyLocation Layer of Google Map
            gMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            e.getMessage();
        }
    }

    public void initilizeMap() {

        //   googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment)).getMapAsync(AndroidGoogleMapsActivity.this);
        //   googleMap.setMyLocationEnabled(true);


        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
        //Log.e("mGoogleApiClient", "" + mGoogleApiClient.isConnected());

      /* locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the network location provider.
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    System.out.println("aaayyaaaaa 1>>>" + latitude + "aaaaaaaa 1" + longitude);
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };
locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);*/

        try {


            if (latitude == 0 && longitude == 0) {
                logioc = 9;
            } else {
                latLng = new LatLng(latitude, longitude);
            }
            if (latitude != 0 && longitude != 0 && check_value != 0) {
                logioc = 0;

            }


            //  new AsynkData().execute();

            /*if (check_value == 0) {
                NearestBranches_by_Pin_REST_api();
            } else {
                NearestBranches_by_Lat_Long_REST_api();
            }
          */


            /*Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, true);
            location = locationManager.getLastKnownLocation(bestProvider);
            locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
            onLocationChanged(location);*/
            hello();
            if (check_value == 0) {
                // googleMap.addMarker(new MarkerOptions().position(latLng).title(result)
                //.icon((BitmapDescriptorFactory.fromResource(R.drawable.blue))));
                // googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
            } else {
                googleMap.addMarker(new MarkerOptions().position(latLng).title(result).draggable(true)
                        .icon((BitmapDescriptorFactory.fromResource(R.drawable.blue))));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                if (aaa == 2 || aaa == 3) {

                } else {
                    //  Toast.makeText(AndroidGoogleMapsActivity.this, "Setup Markers on Map Please Wait...", Toast.LENGTH_LONG).show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // get current location address
    public void hello() {

        try {
            final Geocoder geocoder = new Geocoder(AndroidGoogleMapsActivity.this, Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("");
                }

                result = sb.toString();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Branch(double latitude, double longitude, String branch_address) {
        LatLng branchLatLng = new LatLng(latitude, longitude);

        // Log.d("branch add:", "branch add==" + branch_address);

        // getaddress(latitude, longitude);
        try {
            marker1 = googleMap.addMarker(new MarkerOptions().position(branchLatLng).title(branch_address)
                    .icon((BitmapDescriptorFactory.fromResource(R.drawable.branch))));
            marker1.showInfoWindow();
        } catch (Exception e) {
            e.getMessage();
        }
        if (false) {
            // if (aaa == 2) {
            try {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(latLng);
                builder.include(branchLatLng);
                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);
                CameraUpdate zout = CameraUpdateFactory.zoomBy(13);
                googleMap.animateCamera(zout);
                googleMap.moveCamera(cu);
            } catch (Exception e) {
                e.getMessage();
            }
        } else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        }


        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        drawline(latitude, longitude);
    }

    public void ATM(double latitude, double longitude, String atm_address) {

        LatLng atmlatlng = new LatLng(latitude, longitude);
        // getaddress(latitude, longitude);
        marker2 = googleMap.addMarker(new MarkerOptions().position(atmlatlng).title(atm_address)
                .icon((BitmapDescriptorFactory.fromResource(R.drawable.atmm))));
        marker2.showInfoWindow();
        if (false) {
            //if (aaa == 3) {
            try {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(latLng);
                builder.include(atmlatlng);
                LatLngBounds bounds = builder.build();

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);
                CameraUpdate zout = CameraUpdateFactory.zoomBy(13);
                googleMap.animateCamera(zout);
                googleMap.moveCamera(cu);
            } catch (Exception e) {

            }
        } else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        }

        drawline(latitude, longitude);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

//    public void Call(double a, double b) {
//
//
//        final String NAMESPACE = Utility.NAMESPACE;
//        final String URL = Utility.URL_OBC_SATHI_LOCATOR;// "http://121.241.255.225/obcnew/site/OBCLocatorApp.asmx";
//        final String METHOD_NAME = "NearestBranches";
//        final String SOAP_ACTION = Utility.NAMESPACE + METHOD_NAME;
//
//        String a1 = Double.toString(a);
//        String b1 = Double.toString(b);
//        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//        request.addProperty("latitude", a1);
//        request.addProperty("longitude", b1);
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // put all required data into a soap
//        envelope.dotNet = true;
//        envelope.setOutputSoapObject(request);
//
//        HttpTransportSE httpTransport = new HttpTransportSE(URL);
//        try {
//            httpTransport.call(SOAP_ACTION, envelope);
//            KvmSerializable ks = (KvmSerializable) envelope.bodyIn;
//            for (int j = 0; j < ks.getPropertyCount(); j++) {
//                ks.getProperty(j);
//            }
//            String st_recive = ks.toString();
//
//            String u = st_recive.substring(st_recive.indexOf("["));
//           // Log.d("json string", u);
//            jsonArray = new JSONArray(u);
//
//            Atmname = new ArrayList<>();
//            Atmaddress = new ArrayList<>();
//            Atmdistance = new ArrayList<>();
//
//            branchname = new ArrayList<>();
//            branchaddress = new ArrayList<>();
//            branchdistance = new ArrayList<>();
//
//
//            /////** requerment by punnet Sir   *///
//
//            for (int k = 0; k < jsonArray.length(); k++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(k);
//                // e=new String[2];
//
//                String atmname = jsonObject.getString("type_name");
//                String atmaddress = jsonObject.getString("address");
//                String atmdistance = jsonObject.getString("branchdistance");
//               // Log.d("ATM   newwwwwwww>>", atmname + ">>" + atmaddress);
//
//                myAtmbranch.add(atmname);
//                mydistance.add(atmaddress);
//                myAtmadddress.add(atmdistance);
//
//
//            }
//
//
//            for (int i = 0; i <= 4; i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                // e=new String[2];
//
//                String atmname = jsonObject.getString("type_name");
//                String atmaddress = jsonObject.getString("address");
//                String atmlati = jsonObject.getString("Latitude");
//                String atmlongi = jsonObject.getString("Longitude");
//                String atmdistance = jsonObject.getString("branchdistance");
//                //Log.d("ATM>>", atmname + ">>" + atmaddress);
//                Atmname.add(atmname);
//                Atmaddress.add(atmaddress);
//                Atmdistance.add(atmdistance);
//                atmlatList.add(atmlati);
//                atmlongList.add(atmlongi);
//                String g = Atmaddress.get(i);
//                //Log.d("BATM>>", g + ">>");
//            }
//
//
//            for (int j = 5; j <= 9; j++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(j);
//                //e=new String[2];
//
//                String name = jsonObject.getString("type_name");
//                String address = jsonObject.getString("address");
//                String blati = jsonObject.getString("Latitude");
//                String blongi = jsonObject.getString("Longitude");
//                String distance = jsonObject.getString("branchdistance");
//               // Log.d("Branch>>", name + ">>" + address);
//
//
//                branchname.add(name);
//                branchaddress.add(address);
//                branchdistance.add(distance);
//                branchlatList.add(blati);
//                branchlongList.add(blongi);
//
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }

/*
    public void pincode(String pin) {

        final String NAMESPACE = Utility.NAMESPACE;
        final String URL = Utility.URL_OBC_SATHI_LOCATOR;// "http://121.241.255.225/obcnew/site/OBCLocatorApp.asmx";

        final String METHOD_NAME = "FindbyPincode";
        final String SOAP_ACTION = Utility.NAMESPACE + METHOD_NAME;


        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("pincode", pin);

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
            pin_Ltude = new ArrayList<>();
            pin_Long = new ArrayList<>();
            pin_B_Ltude = new ArrayList<>();
            pin_B_Long = new ArrayList<>();
            lat_total = new ArrayList<>();
            longi_total = new ArrayList<>();
            pin_name = new ArrayList<>();
            pin_B_name = new ArrayList<>();
            pin_Ltude.clear();
            pin_Long.clear();
            pin_B_Long.clear();
            pin_B_Ltude.clear();

            for (int loop = 0; loop < jsonArray.length(); loop++) {
                JSONObject jsonObject = jsonArray.getJSONObject(loop);
                pin_lati = jsonObject.getString("Latitude");
                pin_longi = jsonObject.getString("Longitude");
                pin_type = jsonObject.getString("stype");
                pin_name_data = jsonObject.getString("name");
                lat_total.add(pin_lati);
                longi_total.add(pin_longi);
                if (pin_type.contains("atm")) {

                    pin_Ltude.add(pin_lati);
                    pin_Long.add(pin_longi);
                    pin_name.add(pin_name_data);

                }
                if (pin_type.contains("branch")) {
                    pin_B_Ltude.add(pin_lati);
                    pin_B_Long.add(pin_longi);
                    pin_B_name.add(pin_name_data);
                }

            }
            final Runnable mUpdateResults2 = new Runnable() {
                public void run() {

                    for (int atm = 0; atm < pin_Ltude.size(); atm++) {
                        atm_indx = pin_Ltude.get(atm);
                        atm_longi_index = pin_Long.get(atm);

                        for (int bran = 0; bran < pin_B_Ltude.size(); bran++) {
                            bran_indx = pin_B_Ltude.get(bran);
                            branch_longi_index = pin_B_Long.get(bran);
                            bran_index_name = pin_B_name.get(bran);
                            if (atm_indx.equals(bran_indx) && atm_longi_index.equals(branch_longi_index)) {
                                pinbranchl_LAT = Double.parseDouble(bran_indx);
                                pinbranch_longi = Double.parseDouble(branch_longi_index);

                                PIN_BRANCH_ATM(pinbranchl_LAT, pinbranch_longi, bran_index_name);
                                pin_B_Ltude.remove(bran);
                                pin_B_Long.remove(bran);
                                pin_Ltude.remove(atm);
                                pin_Long.remove(atm);
                                pin_name.remove(atm);
                                pin_B_name.remove(bran);
                                atm = 0;
                            }
                        }
                    }


                    for (int j = 0; j < pin_Ltude.size(); j++) {


                        String l1 = pin_Ltude.get(j);
                        String l2 = pin_Long.get(j);
                        String place_name = pin_name.get(j);

                        double dl1 = Double.parseDouble(l1);
                        double dl2 = Double.parseDouble(l2);


                        PIN_ATM(dl1, dl2, place_name);
                    }

                    for (int k = 0; k < pin_B_Ltude.size(); k++) {
                        String bl1 = pin_B_Ltude.get(k);
                        String bl2 = pin_B_Long.get(k);
                        String branch_name = pin_B_name.get(k);
                        double branchl1 = Double.parseDouble(bl1);
                        double branchl2 = Double.parseDouble(bl2);

                        PIN_Branch(branchl1, branchl2, branch_name);
                    }
                }
            };
            Thread t2 = new Thread() {
                public void run() {
                    mHandler.post(mUpdateResults2);
                }
            };
            t2.start();

        } catch (
                Exception e
        ) {
            e.printStackTrace();
        }

    }
*/

    private void NearestBranches_by_Lat_Long_REST_api() {

        Progress_Dialog.show_dialog(this);
        HashMap<String, String> hashMap = new HashMap<>();

        String a1 = Double.toString(latitude);
        String b1 = Double.toString(longitude);
        hashMap.put("latitude", a1);
        hashMap.put("longitude", b1);

        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.NearestBranches(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<NearestBranches_Response>>() {
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
                    public void onNext(final List<NearestBranches_Response> responseList) {

                        Atmname.clear();
                        Atmaddress.clear();
                        Atmdistance.clear();
                        branchname.clear();
                        branchaddress.clear();
                        branchdistance.clear();

                        if (responseList != null && responseList.size() > 0) {

                           /* final Runnable r = new Runnable() {
                                public void run() {*/

                            for (NearestBranches_Response nearestBranches_response : responseList) {
                                String name = nearestBranches_response.getType_name();
                                String address = nearestBranches_response.getAddress();
                                String distance = nearestBranches_response.getBranchdistance();
                                String lat = nearestBranches_response.getLatitude();
                                String lng = nearestBranches_response.getLongitude();
                                myAtmbranch.add(name);
                                mydistance.add(distance);
                                myAtmadddress.add(address);

                                if (nearestBranches_response.getStype().equalsIgnoreCase("atm")) {
                                    Atmname.add(name);
                                    Atmaddress.add(address);
                                    Atmdistance.add(distance);
                                    atmlatList.add(lat);
                                    atmlongList.add(lng);
                                    try {
                                        double c = Double.parseDouble(lat);
                                        double d = Double.parseDouble(lng);
                                        ATM(c, d, address);
                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }

                                } else if (nearestBranches_response.getStype().equalsIgnoreCase("branch")) {

                                    branchname.add(name);
                                    branchaddress.add(address);
                                    branchdistance.add(distance);
                                    branchlatList.add(lat);
                                    branchlongList.add(lng);

                                    try {
                                        double a = Double.parseDouble(lat);
                                        double b = Double.parseDouble(lng);
                                        Branch(a, b, address);
                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }

                                }


                            }



                              /*  }};
                            mHandler.postDelayed(r, 1000);*/


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

    private void NearestBranches_by_Pin_REST_api() {

        Progress_Dialog.show_dialog(this);
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("pincode", pin_code);
        API_Interface apiService = API_Client.getClient().create(API_Interface.class);
        apiService.FindbyPincode(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<NearestBranchesByPin_Response>>() {
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
                    public void onNext(List<NearestBranchesByPin_Response> responseList) {

                        lat_total.clear();
                        longi_total.clear();
                        pin_name.clear();
                        pin_B_name.clear();
                        pin_Ltude.clear();
                        pin_Long.clear();
                        pin_B_Long.clear();
                        pin_B_Ltude.clear();


                        if (responseList != null && responseList.size() > 0) {
                            try {
                                for (NearestBranchesByPin_Response nearestBranches_response : responseList) {

                                    pin_lati = nearestBranches_response.getLatitude();
                                    pin_longi = nearestBranches_response.getLongitude();
                                    pin_type = nearestBranches_response.getStype();
                                    pin_name_data = nearestBranches_response.getName();
                                    lat_total.add(pin_lati);
                                    longi_total.add(pin_longi);
                                    if (pin_type.contains("atm")) {

                                        pin_Ltude.add(pin_lati);
                                        pin_Long.add(pin_longi);
                                        pin_name.add(pin_name_data);

                                    }
                                    if (pin_type.contains("branch")) {
                                        pin_B_Ltude.add(pin_lati);
                                        pin_B_Long.add(pin_longi);
                                        pin_B_name.add(pin_name_data);
                                    }
                                }
                            } catch (Exception e) {
                                e.getMessage();
                            }


                            for (int atm = 0; atm < pin_Ltude.size(); atm++) {
                                atm_indx = pin_Ltude.get(atm);
                                atm_longi_index = pin_Long.get(atm);

                                for (int bran = 0; bran < pin_B_Ltude.size(); bran++) {
                                    bran_indx = pin_B_Ltude.get(bran);
                                    branch_longi_index = pin_B_Long.get(bran);
                                    bran_index_name = pin_B_name.get(bran);
                                    if (atm_indx.equals(bran_indx) && atm_longi_index.equals(branch_longi_index)) {
                                        pinbranchl_LAT = Double.parseDouble(bran_indx);
                                        pinbranch_longi = Double.parseDouble(branch_longi_index);

                                        PIN_BRANCH_ATM(pinbranchl_LAT, pinbranch_longi, bran_index_name);
                                        pin_B_Ltude.remove(bran);
                                        pin_B_Long.remove(bran);
                                        pin_Ltude.remove(atm);
                                        pin_Long.remove(atm);
                                        pin_name.remove(atm);
                                        pin_B_name.remove(bran);
                                        atm = 0;
                                    }
                                }
                            }


                            for (int j = 0; j < pin_Ltude.size(); j++) {
                                String l1 = pin_Ltude.get(j);
                                String l2 = pin_Long.get(j);
                                String place_name = pin_name.get(j);
                                double dl1 = Double.parseDouble(l1);
                                double dl2 = Double.parseDouble(l2);

                                PIN_ATM(dl1, dl2, place_name);
                            }

                            for (int k = 0; k < pin_B_Ltude.size(); k++) {
                                String bl1 = pin_B_Ltude.get(k);
                                String bl2 = pin_B_Long.get(k);
                                String branch_name = pin_B_name.get(k);
                                double branchl1 = Double.parseDouble(bl1);
                                double branchl2 = Double.parseDouble(bl2);

                                PIN_Branch(branchl1, branchl2, branch_name);
                            }


                        } else {
                         /*
                            Toast.makeText(getApplicationContext(),
                                    "Sorry, something went wrong there. Try again.", Toast.LENGTH_LONG).show();
                        */

                        }
                        Progress_Dialog.hide_dialog();

                        if ((pin_Ltude.size() == 0 || pin_Long.size() == 0) && (pin_B_Ltude.size() == 0 || pin_B_Long.size() == 0)) {
                            Toast.makeText(AndroidGoogleMapsActivity.this, "No data found for this PIN Code", Toast.LENGTH_SHORT).show();
                        }


                    }
                });


    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("AndroidGoogleMaps Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (!isFromDetailActivity) {
            if (mCurrentLocation != null) {
                latitude = mCurrentLocation.getLatitude();
                longitude = mCurrentLocation.getLongitude();
               // Log.e("my lat_long", latitude + "," + longitude);

                latLng = new LatLng(latitude, longitude);
               /* final Runnable r = new Runnable() {
                    public void run() {*/
                        if (check_value == 0) {
                            NearestBranches_by_Pin_REST_api();
                        } else {
                            NearestBranches_by_Lat_Long_REST_api();
                        }
                /*    }};
                mHandler.postDelayed(r, 10000);*/

            } else {
               // Log.e("my lat_long", "null");
            }


        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

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

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) AndroidGoogleMapsActivity.this
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

  /*  public void getaddress(double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i));
                    // sb.append(address.);
                }
                addr = sb.toString();


            }
        } catch (IOException i) {
            i.printStackTrace();
        }

    }
*/

    public void PIN_Branch(double latitude, double longitude, String name) {

        LatLng branchLatLng = new LatLng(latitude, longitude);
        //getaddress(latitude, longitude);
        marker1 = googleMap.addMarker(new MarkerOptions().position(branchLatLng).title(name)
                .icon((BitmapDescriptorFactory.fromResource(R.drawable.branch))));
        marker1.showInfoWindow();
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(branchLatLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));

    }

    public void PIN_ATM(double latitude, double longitude, String name) {

        LatLng atmlatlng = new LatLng(latitude, longitude);
        //getaddress(latitude, longitude);
        marker2 = googleMap.addMarker(new MarkerOptions().position(atmlatlng).title(name)
                .icon((BitmapDescriptorFactory.fromResource(R.drawable.atmm))));
        marker2.showInfoWindow();
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(atmlatlng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));


    }

    public void PIN_BRANCH_ATM(double latitude, double longitude, String name) {

        LatLng atmlatlng = new LatLng(latitude, longitude);
        //getaddress(latitude, longitude);
        marker2 = googleMap.addMarker(new MarkerOptions().position(atmlatlng).title(name)
                .icon((BitmapDescriptorFactory.fromResource(R.drawable.bank_atm))));
        marker2.showInfoWindow();
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(atmlatlng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));


    }

    void requestLocationPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(AndroidGoogleMapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            initilizeMap();


        } else {
            Toast.makeText(AndroidGoogleMapsActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();

            finish();
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void initializeMapNew() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
       // Log.e("mGoogleApiClient", "" + mGoogleApiClient.isConnected());
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment)).getMapAsync(AndroidGoogleMapsActivity.this);
        createLocationRequest();
    }

  /*  class AsynkData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (logioc == 9 || check_value == 0) {
                alertDialog = new SweetAlertDialog(AndroidGoogleMapsActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                alertDialog.setTitleText("Please Wait...");
                alertDialog.show();
            }

        }

        @Override
        protected Void doInBackground(Void... params) {

            if (check_value == 0) {

                pincode(pin_code);
            } else {

                Call(latitude, longitude);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (check_value == 0) {
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }
            if (logioc == 9 && check_value != 0) {
                alertDialog.dismiss();
                initilizeMap();
                if (latitude != 0 && longitude != 0) {
                    alertDialog.dismiss();
                }

            } else {
                try {
                    if (check_value == 0) {
                        if ((pin_Ltude.size() == 0 || pin_Long.size() == 0) && (pin_B_Ltude.size() == 0 || pin_B_Long.size() == 0)) {
                            Toast.makeText(AndroidGoogleMapsActivity.this, "No data found for this PIN Code", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (buttonValue == 3 || buttonValue == 2 || flag == 2 || flag == 3 || aaa == 2 || aaa == 3 || aaa == 0) {

                    } else {
                        try {
                            String l1 = atmlatList.get(0);
                            String l2 = atmlongList.get(0);
                            String atm_Address = Atmaddress.get(0);
                            double c = Double.parseDouble(l1);
                            double d = Double.parseDouble(l2);
                            ATM(c, d, atm_Address);
                            String bl1 = branchlatList.get(0);
                            String bl2 = branchlongList.get(0);
                            String branch_Address = branchaddress.get(0);
                            double a = Double.parseDouble(bl1);
                            double b = Double.parseDouble(bl2);

                            Branch(a, b, branch_Address);
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                } catch (Exception e) {

                }
            }
        }

    }
*/
}