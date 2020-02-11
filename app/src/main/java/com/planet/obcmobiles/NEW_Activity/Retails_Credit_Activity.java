package com.planet.obcmobiles.NEW_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.planet.obcmobiles.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11/4/2017.
 */

public class Retails_Credit_Activity extends AppCompatActivity   implements AdapterView.OnItemSelectedListener{
    private Spinner spn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retail_);

        ImageView back = (ImageView)findViewById(R.id.back);
        Button home_loan =(Button)findViewById(R.id.home_loan);
        spn= (Spinner)findViewById(R.id.spn);
        Spinner_Data();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        home_loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Retails_Credit_Activity.this, Expand_Loan_Activity.class);
                startActivity(i);
            }
        });
    }

    private void Spinner_Data() {

        List categories = new ArrayList();
        categories.add("--Select--");
        categories.add("Home Loan");
        categories.add("Home Top Up");
        categories.add("Auto Loan");
        categories.add("Personal Loan");

        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

    }
    public void onNothingSelected(AdapterView adapterView) {
    }


}
