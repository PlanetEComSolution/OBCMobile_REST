package com.planet.obcmobiles;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class OnlineLone extends Activity {
    EditText name,email,mobile,location,pin,remarks;
    TextView t1;
    Button submit,home;

    Spinner companyType,request;


    JSONArray jArray;
    ArrayAdapter<String>  adapterdapter, companyAdapter;
    String responsestatus, receivedString;
    int  catchhandling;


    String ename,emobile,eemail,elocation,epin,eremarks,ecompanytype,erequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_lone);

        name=(EditText)findViewById(R.id.firstname);
        mobile=(EditText)findViewById(R.id.mobile_no);
        email=(EditText)findViewById(R.id.email_no);
        location=(EditText)findViewById(R.id.location);
        pin=(EditText)findViewById(R.id.pin);
        remarks=(EditText)findViewById(R.id.Remarks);
        companyType=(Spinner)findViewById(R.id.Customertype);
        request=(Spinner)findViewById(R.id.request);
        submit=(Button)findViewById(R.id.register);
        t1=(TextView)findViewById(R.id.checkStatus);





        Spinnerdata();
        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data();
                    }
                }
        );
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(OnlineLone.this, CheckLoanStatus.class);
                startActivity(in);
            }
        });
    }

    public void Spinnerdata()
    {
        String []company={"-- Select Customer Type --","Public","Staff","Senior Citizen","Minor"};
        String []pro={"-- Request for Loan --","Housing","Vehicle","Education","Other Retail Loan","MSME"};
    companyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pro);
        request.setAdapter(companyAdapter);
      adapterdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, company);
        companyType.setAdapter(adapterdapter);
    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public void data()
    {
        ename=name.getText().toString();
        emobile=mobile.getText().toString();
        eemail=email.getText().toString();
        elocation=location.getText().toString();
        epin=pin.getText().toString();
        eremarks=remarks.getText().toString();
        ecompanytype=companyType.getSelectedItem().toString();
        erequest=request.getSelectedItem().toString();

        if(ecompanytype.contains("Select Customer Type"))
        {
            Toast.makeText(getApplicationContext(), "Please select Customer Type ", Toast.LENGTH_LONG).show();
        }
        else if(erequest.contains("Request for Loan "))
        {
            Toast.makeText(getApplicationContext(), "Please select loan type", Toast.LENGTH_LONG).show();
        }

        else if(ename.length()==0)
        {
            Toast.makeText(getApplicationContext(), "Please enter first name", Toast.LENGTH_LONG).show();
        }

        else if(isValidEmailAddress(eemail)==false)
        {
            Toast.makeText(getApplicationContext(), "Please enter correct EmailId", Toast.LENGTH_LONG).show();
        }

        else if(emobile.length()<=9)
        {
            Toast.makeText(getApplicationContext(), "Please enter mobile number", Toast.LENGTH_LONG).show();
        }

        else if(elocation.length()==0)
        {
            Toast.makeText(getApplicationContext(), "Please enter location ", Toast.LENGTH_LONG).show();
        }
        else if(epin.length()<=5)
        {
            Toast.makeText(getApplicationContext(), "Please enter correct pin", Toast.LENGTH_LONG).show();
        }
        else
        {
            if(isConnectingToInternet()==true) {
             //   new performBackgroundTask1().execute();
            }
            else
            {
                Toast.makeText(getApplicationContext(),
                        "No Internet Connection....", Toast.LENGTH_LONG).show();
            }
        }
    }




    public void internetAlertDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OnlineLone.this);
        alertDialogBuilder.setTitle("No Internet Connection");
        alertDialogBuilder
                .setMessage("Check Internet Connection!")
                .setCancelable(true)

                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public boolean isConnectingToInternet()
    {
        ConnectivityManager connectivity = (ConnectivityManager) OnlineLone.this
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



    public void SuccessAlertDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                OnlineLone.this);
        alertDialogBuilder.setTitle("Successfully Posted!");

        alertDialogBuilder
                .setMessage("Your loan Application Reference no."+ responsestatus)
                .setCancelable(true)

                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                        //      Complain.this.finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }


}
