package com.planet.obcmobiles;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Suggestion extends Activity {
    EditText firstname,mobile_no,email_no,subject,companyname;
    Button  register,home;
    Spinner Customertype;
    int  catchhandling;
    JSONArray jArray;


    String first,mobil,semail,ssubject,scompanyname,scompany,scompanyType,receivedString,responsestatus ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        firstname=(EditText)findViewById(R.id.firstname);
        mobile_no=(EditText)findViewById(R.id.mobile_no);
        email_no=(EditText)findViewById(R.id.email_no);
        subject=(EditText)findViewById(R.id.subject);
        companyname=(EditText)findViewById(R.id.companyname);
        register=(Button)findViewById(R.id.register);
        Customertype=(Spinner)findViewById(R.id.Customertype);


        home= (Button) findViewById(R.id.home);
      /*  home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Suggestion.this,Home.class);
                startActivity(in);
                finish();
            }
        });*/

        Spinnerdata();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data();

            }
        });
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }



    public void Spinnerdata()
    {
        String []company={"-- Select Customer Type --","Public","Staff","Senior Citizen","Minor"};
        ArrayAdapter<String>  adapterdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, company);
        Customertype.setAdapter(adapterdapter);
    }


    public void data()
    {


        first=firstname.getText().toString();
        mobil=mobile_no.getText().toString();
        semail=email_no.getText().toString();
        ssubject=subject.getText().toString();
        scompanyname=companyname.getText().toString();
        scompanyType=Customertype.getSelectedItem().toString();


        if(scompanyType.contains("Select Customer Type"))
        {
            Toast.makeText(getApplicationContext(), "Please select Customer Type ", Toast.LENGTH_LONG).show();
        }

        else if(first.length()==0)
        {
            Toast.makeText(getApplicationContext(), "Please enter first name", Toast.LENGTH_LONG).show();
        }

        else if(isValidEmailAddress(semail)==false)
        {
            Toast.makeText(getApplicationContext(), "Please enter correct EmailId", Toast.LENGTH_LONG).show();
        }

        else if((mobil.length()==0)||(mobil.length()!=10))
        {
            Toast.makeText(getApplicationContext(), "Please enter mobile", Toast.LENGTH_LONG).show();
        }

        else if(ssubject.length()==0)
        {
            Toast.makeText(getApplicationContext(), "Please enter subject ", Toast.LENGTH_LONG).show();
        }
        else if(scompanyname.length()==0)
        {
            Toast.makeText(getApplicationContext(), "Please enter comments", Toast.LENGTH_LONG).show();
        }
        else
        {
            if(isConnectingToInternet()==true) {
                new performBackgroundTask().execute();
            }
            else
            {
                Toast.makeText(getApplicationContext(),
                        "No Internet Connection....", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class performBackgroundTask extends AsyncTask<Void, Void, Void>
    {
        private ProgressDialog Dialog = new ProgressDialog(Suggestion.this);
        @Override
        protected void onPreExecute()
        {
            Dialog.setMessage("Please wait...");
            Dialog.show();
        }
        @Override
        protected void onPostExecute(Void unused)
        {
            try
            {
                if(Dialog.isShowing())
                {
                    Dialog.dismiss();
                    if(responsestatus.equalsIgnoreCase("1")) {
                        SuccessAlertDialog();
                        firstname.setText("");
                        mobile_no.setText("");
                        email_no.setText("");
                        subject.setText("");
                        companyname.setText("");
                        Customertype.setSelection(0);
                    }

                }
                else
                {
                    internetAlertDialog();
                }
            }
            catch(Exception e)
            {
                Toast.makeText(getApplicationContext(),
                        "Network Error....", Toast.LENGTH_LONG).show();
                Dialog.dismiss();
            }
        }
        @Override
        protected Void doInBackground(Void... params)
        {
           // userregisteration_web();
            return null;
        }
    }

  /*  public void userregisteration_web()
    {
        catchhandling=0;



        final String NAMESPACE = Utility.NAMESPACE;
        final String URL = Utility.URL_OBC_SITE;
        final String METHOD_NAME = "SuggestionInsert";
        final String SOAP_ACTION =Utility.NAMESPACE+METHOD_NAME;// "http://tempuri.org/insertrecord";




        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("VR_Name", first );
        request.addProperty("EmailId", semail);
        request.addProperty("MobileNo", mobil);
        request.addProperty("Subject", ssubject);
        request.addProperty("Remarks", scompanyname);
        request.addProperty("CustomerType",scompanyType);

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
            catchhandling++;
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
                responsestatus = json_obj.getString("success");

            }
        }
        catch(Exception e)
        {
            catchhandling++;
            e.printStackTrace();
        }

    }
*/
    public void internetAlertDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Suggestion.this);
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
    }


    public boolean isConnectingToInternet()
    {
        ConnectivityManager connectivity = (ConnectivityManager)Suggestion.this
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
                Suggestion.this);

        // set title
        alertDialogBuilder.setTitle("Successfully Posted!");

        // set dialog message
        alertDialogBuilder
                .setMessage("Success")
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
