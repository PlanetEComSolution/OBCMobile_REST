package com.planet.obcmobiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Main4Activity extends AppCompatActivity {
    ArrayAdapter<String>  adapterdapter, companyAdapter;
    Button button;
    Spinner request;
    SweetAlertDialog alertDialog;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        request=(Spinner)findViewById(R.id.request);
        button = (Button) findViewById(R.id.register);
        String []pro={"-- Request for Loan --","Housing","Vehicle","Education","Other Retail Loan","MSME"};
        companyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pro);
        request.setAdapter(companyAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = new SweetAlertDialog(Main4Activity.this, SweetAlertDialog.PROGRESS_TYPE);
                alertDialog.setTitleText("Please Wait...");
                alertDialog.show();

                if (request.getSelectedItem().toString().contains("-- Request for Loan --")) {
                    Toast.makeText(Main4Activity.this, "Please Select Loan Type", Toast.LENGTH_SHORT).show();


                } else {

                        String name_cmper = (String) request.getSelectedItem();
                        int position = request.getSelectedItemPosition();
                       // Log.d("LOAN", name_cmper);
                        Intent intent = new Intent(Main4Activity.this, Main3Activity.class);
                        intent.putExtra("SELECTED_LOAN_NAME", name_cmper);
                        intent.putExtra("pos", position);
                        startActivity(intent);

                    }
                alertDialog.cancel();

            }


        });

        request.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                name = (String) request.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
