package com.planet.obcmobiles;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.planet.obcmobiles.Adapter_new.Dashboard_Adapter;
import com.planet.obcmobiles.NEW_Activity.Apply_Loan_New;
import com.planet.obcmobiles.NEW_Activity.Bank_App_Activity;
import com.planet.obcmobiles.NEW_Activity.Complain_Segetions_Activity;
import com.planet.obcmobiles.NEW_Activity.Hindi_Shabd_Activity;
import com.planet.obcmobiles.NEW_Activity.Loan_Status_Activity;
import com.planet.obcmobiles.NEW_Activity.Product_And_Services_Activity;
import com.planet.obcmobiles.NEW_Activity.Whats_New_Activity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Home extends AppCompatActivity implements Animation.AnimationListener , GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener ,LocationListener {
   private Button btn_atm, btn_emi, btn_suggestion, btn_intrest, btn_alert, btn_contact;
   private   Animation animation_blink;
   private   Context context;
   private   ImageButton button_anim;
   private boolean network_enabled = false;
   private boolean gps_enabled = false;
    boolean doubleBackToExitPressedOnce = false;
    private GridView grid;

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    Location mCurrentLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branch_business);

      /*  if (isConnectingToInternet() ) {
            new checkVersionUpdate().execute();
        } else {
            Toast.makeText(getApplicationContext(),
                    "No Internet Connection....", Toast.LENGTH_LONG).show();
        }*/


        if (isConnectingToInternet()) {

            APIGetVersion();
        } else {
            Toast.makeText(getApplicationContext(),
                    "No Internet Connection....", Toast.LENGTH_SHORT).show();
        }


        try {
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                Window window = Home.this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Home.this.getResources().getColor(R.color.Green_primary));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))

        {
            ActivityCompat.requestPermissions(Home.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

        animation_blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        animation_blink.setAnimationListener(this);
        button_anim = (ImageButton) findViewById(R.id.imageButton2);
        button_anim.startAnimation(animation_blink);
        double r = 5 / 400.0;
        double a = 1 + r;
        a = Math.pow(a, 3);
        double l = 10000 * a - 1;
        double m = Math.pow(1 + r, -1 / 3);
        double n = l / m;
       /* a=10000*a;
double lo=Math.round(a*100)/100;

       double lo=  10000 * 1+r/3 - 1 / (1-(1+r)-(1/3));*/
       // System.out.println("answer:-" + n + "      " + n + "          " + n);
        application.getInstance().trackScreenView("Home Activity");


        String[] gridColor = {

                "#000",
                "#008B8B",
                "#008B8B",
                "#008B8B",
                "#008B8B",
                "#008B8B","#008B8B","#008B8B","#008B8B"
        };
        String[] web = {
                "Find a Branch/ATM",
                "Interest Rates",
                "Apply Loan",
                "Product And Services",
                "EMI Calculator",
                "Complaints/Suggestions",
                "Contact Us",
                "बैंकिंग शब्दावली",
                "Bank's Mobile Application",
                "Employee Corner",
                "Loan Status",
                "What's New",

        };
        int[] imageId = {
                R.drawable.place,
                R.drawable.interest,
                R.drawable.rename,
                R.drawable.product_services,
                R.drawable.calculator,
                R.drawable.note,
                R.drawable.cont_img,
                R.drawable.dicsn_img,
                R.drawable.phone,
                R.drawable.employees,
                R.drawable.status,
                R.drawable.whatsnew,

        };

        Dashboard_Adapter adapter = new Dashboard_Adapter(this, web, imageId,gridColor);
        grid = (GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Find a Branch/ATM
                if (position == 0) {
                    ATM_Find();
                    //Interest Rates
                } else if (position == 1) {
                    Intent i = new Intent(Home.this, Calc_tab.class);
                    startActivity(i);
                    //Apply Loan
                } else if (position == 2) {
                    Intent web = new Intent().setClass(getApplicationContext(), Apply_Loan_New.class);
                    startActivity(web);
                    //Product And Services
                } else if (position == 3) {
                    Intent i = new Intent(Home.this, Product_And_Services_Activity.class);
                    startActivity(i);
                    //EMI Calculator
                } else if (position == 4) {
                    Intent i = new Intent(Home.this, EMI.class);
                    startActivity(i);
                    //Complaints/Suggestions
                } else if (position == 5) {
                    Intent i = new Intent(Home.this, Complain_Segetions_Activity.class);
                    startActivity(i);
                }
                //Contact Us
                else if (position == 6) {
                    Intent i = new Intent(Home.this, Expadaiable.class);
                    startActivity(i);
                    //बैंकिंग शब्दावली
                } else if (position == 7) {
                    Intent i = new Intent(Home.this, Hindi_Shabd_Activity.class);
                    startActivity(i);
                    //Bank's Mobile Application
                } else if (position == 8) {
                    Intent i = new Intent(Home.this, Bank_App_Activity.class);
                    startActivity(i);
                    //Employee Corner
                } else if (position == 9) {
                  //  Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.webelearning.obcindia.co.in:8081/obc_ecircular/"));
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.elearning.obcindia.co.in:8082/obc_ecircular/"));
                    startActivity(browserIntent);
                    //Loan Status
                } else if (position == 10) {
                    Intent i = new Intent(Home.this, Loan_Status_Activity.class);
                    startActivity(i);
                    //What's New
                } else if (position == 11) {
                    Intent i = new Intent(Home.this, Whats_New_Activity.class);
                    startActivity(i);
                }
            }
        });


        LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (gps_enabled && !haveNetworkConnection()) {
            final AlertDialog.Builder builder =
                    new AlertDialog.Builder(Home.this);
            final String action = Settings.ACTION_WIRELESS_SETTINGS;
            final String message = "Your Internet Connection seems to be disabled, do you want to enable it?";

            builder.setMessage(message)
                    .setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    Home.this.startActivity(new Intent(action));
                                    d.dismiss();
                                }
                            })
                    .setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    Home.this.finish();
                                }
                            })
                    .setCancelable(false);
            builder.create().show();
        }

        if (!gps_enabled && !haveNetworkConnection()) {
           // System.out.println(gps_enabled);
            displayPromptForEnablingGPS(Home.this);
        }


    }

    private void ATM_Find() {

        final Dialog dialog = new Dialog(Home.this);
        dialog.setContentView(R.layout.cstm_dialog);
        dialog.setTitle("Choose Your Locator");
        TextView gps = (TextView) dialog.findViewById(R.id.textView22);
        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Home.this,"Coming Soon...", Toast.LENGTH_LONG).show();


                if (gps_enabled==true && Build.VERSION.SDK_INT >= 23)
                {
                    Intent i = new Intent(Home.this, AndroidGoogleMapsActivity.class);
                    startActivity(i);
                    dialog.dismiss();

                }
                else
                {
                    if (!gps_enabled && haveNetworkConnection()) {
                        final AlertDialog.Builder builder =
                                new AlertDialog.Builder(Home.this);
                        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
                        final String message = "Your GPS Connection seems to be disabled, do you want to enable it?";

                        builder.setMessage(message)
                                .setPositiveButton("YES",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface d, int id) {
                                                Home.this.startActivity(new Intent(action));
                                                d.dismiss();
                                            }
                                        })
                                .setNegativeButton("NO",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface d, int id) {
                                                d.dismiss();
                                            }
                                        })
                                .setCancelable(false);
                        builder.create().show();
                    } else {
                        Intent i = new Intent(Home.this, AndroidGoogleMapsActivity.class);
                        startActivity(i);
                        dialog.dismiss();
                    }
                }
            }
        });

        TextView pin = (TextView) dialog.findViewById(R.id.textView23);
        pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Home.this,"Coming Soon...", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Home.this, Pin_code.class);
                startActivity(i);
                dialog.dismiss();
            }
        });
        dialog.show();
    }








    public static void displayPromptForEnablingGPS(final Activity activity) {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_SETTINGS;
        final String message = "Your GPS and Internet Connection seems to be disabled, do you want to enable it?";

        builder.setMessage(message)
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.startActivity(new Intent(action));
                                d.dismiss();
                            }
                        })
                .setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.finish();
                            }
                        })
                .setCancelable(false);
        builder.create().show();
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        if (animation == animation_blink) {
            Toast.makeText(getApplicationContext(), "Animation Stopped",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

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

    @Override
    protected void onRestart() {
        super.onRestart();
        LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
      /* if (!gps_enabled||!haveNetworkConnection()) {
            System.out.println(gps_enabled);
            displayPromptForEnablingGPS(Home.this);
        }*/

        if (gps_enabled && !haveNetworkConnection()) {
            final AlertDialog.Builder builder =
                    new AlertDialog.Builder(Home.this);
            final String action = Settings.ACTION_WIRELESS_SETTINGS;
            final String message = "Your Internet Connection seems to be disabled, do you want to enable it?";

            builder.setMessage(message)
                    .setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    Home.this.startActivity(new Intent(action));
                                    d.dismiss();
                                }
                            })
                    .setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    Home.this.finish();
                                }
                            })
                    .setCancelable(false);
            builder.create().show();
        }

        if (!gps_enabled && !haveNetworkConnection()) {
           // System.out.println(gps_enabled);
            displayPromptForEnablingGPS(Home.this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    //    LocationServices.FusedLocationApi.requestLocationUpdates(
     //           mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(getApplicationContext(), "Please Click Back Again to Exit", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
                if(doubleBackToExitPressedOnce==true){
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }

        }, 1000);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {

        }
        else
        {
            Toast.makeText(this, "LOCATION permission was NOT granted.", Toast.LENGTH_SHORT).show();

        }
    }





    /*private class checkVersionUpdate extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(Home.this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            try {
                progressDialog.show();
            } catch (Exception e) {
                e.getMessage();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            String val = "0.0";

            final String NAMESPACE = "https://tempuri.org/" + "";
            final String URL = "https://www.exhibitpower2.com/WebService/techlogin_service.asmx";
            final String SOAP_ACTION ="https://tempuri.org/GetVersion";
            final String METHOD_NAME = "GetVersion";
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            try {

                final String appPackageName = getPackageName();
                String address = "https://play.google.com/store/apps/details?id=" + appPackageName;
                // String address = "https://itunes.apple.com/in/app/reliance-mutualfund/id691879143?mt=8";


                request.addProperty("address", address);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // put all required data into a soap
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransport = new HttpTransportSE(URL);
                httpTransport.call(SOAP_ACTION, envelope);
                KvmSerializable ks = (KvmSerializable) envelope.bodyIn;
                for (int j = 0; j < ks.getPropertyCount(); j++) {
                    ks.getProperty(j);
                }
                String recved = ks.toString();

                if (recved.contains("GetVersionResult")) {
                    val = recved.substring(recved.indexOf("=") + 1, recved.indexOf(";"));
                }

                //  val = "1.1";//test
            } catch (Exception e) {
                e.getMessage();
            }
            return val;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            String ver = result;
            super.onPostExecute(result);
            try {
                if (progressDialog != null && progressDialog.isShowing()) {

                    try {
                        progressDialog.dismiss();
                    } catch (Exception e) {
                        e.getMessage();
                    }

                }
            } catch (Exception e) {
                e.getMessage();
            }

            try {
                PackageManager manager = Home.this.getPackageManager();
                PackageInfo info = manager.getPackageInfo(Home.this.getPackageName(), 0);
                String versionName = info.versionName;
                String nversionName = ver;
                if (nversionName == null) {
                    nversionName = "0";
                }
                Double retuenvl = Double.parseDouble(nversionName);
                if (Double.parseDouble(versionName) < retuenvl) {
                    // if (true) {
                    try {
                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Home.this, SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText("New Update Available");
                        sweetAlertDialog.setContentText("Please update to the latest version!");
                        sweetAlertDialog.setConfirmText("Update");
                        sweetAlertDialog.setCancelable(false);
                        sweetAlertDialog.setCanceledOnTouchOutside(false);
                        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                                *//**//*
                                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                }
                                finish();
                                *//**//*
                                }
                        })
                                .show();
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }

            } catch (Exception err) {
                err.getMessage();
            }

             *//*   if (new ConnectionDetector(MainActivity.this).isConnectingToInternet()) {
                    new async_RunIncompleteTask().execute();
                }*//*
          //  SavedTimeSheetDialog();

        }
        }
*/
    private boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) Home.this
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

    private void APIGetVersion() {


        String address = "https://play.google.com/store/apps/details?id=" + getPackageName();

        String url = address;
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String nversionName = Utility.getVersion(response);

                try {
                    PackageManager manager = Home.this.getPackageManager();
                    PackageInfo info = manager.getPackageInfo(Home.this.getPackageName(), 0);
                    String versionName = info.versionName;
                    if (nversionName == null) {
                        nversionName = "0";
                    }
                    Double version_PlayStore = Double.parseDouble(nversionName);
                    Double version_app = Double.parseDouble(versionName);

                    if (version_app < version_PlayStore) {
                        // if (true) {
                        try {
                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Home.this, SweetAlertDialog.WARNING_TYPE);
                            sweetAlertDialog.setTitleText("New Update Available");
                            sweetAlertDialog.setContentText("Please update to the latest version!");
                            sweetAlertDialog.setConfirmText("Update");
                            sweetAlertDialog.setCancelable(false);
                            sweetAlertDialog.setCanceledOnTouchOutside(false);
                            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();

                                    /**/
                                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                    }
                                    finish();
                                    /**/
                                }
                            })
                                    .show();
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }

                } catch (Exception err) {
                    err.getMessage();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("err", "Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }
}
