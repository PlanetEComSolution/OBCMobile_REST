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
import android.view.Window;
import android.view.WindowManager;
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

public class Complain extends Activity
{
    EditText name,email,mobile,subject,comments;
    Spinner companyType,product;
    TextView checkStatus;
    Button submit,home;
    JSONArray jArray;
    String responsestatus;
    String cname,cemail,cmobile,csubject,ccomments,ccompanyType,cproduct, receivedString;
    int  catchhandling;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.complanints);
        checkStatus=(TextView)findViewById(R.id.checkStatus);
        name=(EditText)findViewById(R.id.firstname);
        mobile=(EditText)findViewById(R.id.mobile_no);
        email=(EditText)findViewById(R.id.email_no);
        subject=(EditText)findViewById(R.id.subject);
        comments=(EditText)findViewById(R.id.companyname);
        companyType=(Spinner)findViewById(R.id.Customertype);
        product=(Spinner)findViewById(R.id.product);
        submit=(Button)findViewById(R.id.register);

     /*   home= (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Complain.this,Home.class);
                startActivity(in);
                finish();
            }
        });*/
        Spinnerdata();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data();

            }
        });
        checkStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(Complain.this,Coustomer.class);
                startActivity(in);
            }
        });
    }

    public void Spinnerdata()
   {
    String []company={"-- Select Customer Type --","Public","Staff","Senior Citizen","Minor"};
       String []pro={"-- Select Product --","Saving Account","Current Account","Term Deposit","Housing Loan","Vechicle Loan",
       "Education Loan","Other Retail Loan","ATM Related","Technology related","Demat","Govt.Schemes","NRI Schemes", "Customer Service",
       "General Banking","Miscellaneous"};
       ArrayAdapter<String>  companyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pro);
       product.setAdapter(companyAdapter);
       ArrayAdapter<String>  adapterdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, company);
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


        cname=name.getText().toString();
        cemail=email.getText().toString();
        cmobile=mobile.getText().toString();
        csubject=subject.getText().toString();
        ccomments=comments.getText().toString();
        ccompanyType=companyType.getSelectedItem().toString();
        cproduct=product.getSelectedItem().toString();

        if(ccompanyType.contains("Select Customer Type"))
        {
            Toast.makeText(getApplicationContext(), "Please select Customer Type ", Toast.LENGTH_LONG).show();
        }
        else if(cproduct.contains("Select Product"))
        {
            Toast.makeText(getApplicationContext(), "Please select product", Toast.LENGTH_LONG).show();
        }

        else if(cname.length()==0)
        {
            Toast.makeText(getApplicationContext(), "Please enter first name", Toast.LENGTH_LONG).show();
        }

        else if(isValidEmailAddress(cemail)==false)
        {
            Toast.makeText(getApplicationContext(), "Please enter correct EmailId", Toast.LENGTH_LONG).show();
        }

        else if((cmobile.length()==0)||(cmobile.length()!=10))
        {
            Toast.makeText(getApplicationContext(), "Please enter correct mobile no.", Toast.LENGTH_LONG).show();
        }

        else if(csubject.length()==0)
        {
            Toast.makeText(getApplicationContext(), "Please enter subject ", Toast.LENGTH_LONG).show();
        }
        else if(ccomments.length()==0)
        {
            Toast.makeText(getApplicationContext(), "Please enter comments", Toast.LENGTH_LONG).show();
        }
        else
        {
            if(isConnectingToInternet()==true) {
              //  new performBackgroundTask1().execute();
            }
            else
            {
                Toast.makeText(getApplicationContext(),
                        "No Internet Connection....", Toast.LENGTH_LONG).show();
            }
        }
    }

  /*  public class performBackgroundTask1 extends AsyncTask<Void, Void, Void>
    {
        private ProgressDialog Dialog = new ProgressDialog(Complain.this);
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
                    SuccessAlertDialog();
                    name.setText("");
                    mobile.setText("");
                    email.setText("");
                    subject.setText("");
                    comments.setText("");
                   companyType.setSelection(0);
                    product.setSelection(0);
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
            userregisteration_web();
            return null;
        }
    }*/

  /*  public void userregisteration_web()
    {
        catchhandling=0;
        final String NAMESPACE = Utility.NAMESPACE;

        final String URL =Utility.URL_OBC_SITE;// "http://121.241.255.225/obcnew/site/OBCComplaintsApp.asmx";
        final String METHOD_NAME = "ComplaintInsert";
        final String SOAP_ACTION =Utility.NAMESPACE+METHOD_NAME;

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("VR_Name", cname );
        request.addProperty("EmailId", cemail);
        request.addProperty("MobileNo", cmobile);
        request.addProperty("Subject", csubject);
        request.addProperty("Comments", ccomments);
        request.addProperty("CustomerType", ccompanyType);
        request.addProperty("Product", cproduct);


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
                 responsestatus = json_obj.getString("Complaintnumber");

            }
        }
        catch(Exception e)
        {
            catchhandling++;
            e.printStackTrace();
        }

    }*/

    public void internetAlertDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Complain.this);
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
        ConnectivityManager connectivity = (ConnectivityManager)Complain.this
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
                Complain.this);

        // set title
        alertDialogBuilder.setTitle("Successfully Posted!");

        // set dialog message
        alertDialogBuilder
                .setMessage("Your Compaint Reference no  "+ responsestatus)
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
