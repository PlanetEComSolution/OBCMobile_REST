package com.planet.obcmobiles.NEW_Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;


import com.planet.obcmobiles.Adapter_new.CustomGrid_Adapter;
import com.planet.obcmobiles.R;

/**
 * Created by Admin on 11/4/2017.
 */

public class Product_And_Services_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branch_name);

        GridView grid;

        String[] gridColor = {

                "#000",
                "#008B8B",
                "#008B8B",
                "#008B8B",
                "#008B8B",
                "#008B8B","#008B8B",
        };
        String[] web = {
                "Branch Business",
                "Digital Banking",
                "Retail Credit",
                "MSME",
                "Priority Sector",
                "Recovery And Law",

        };
        int[] imageId = {
                R.drawable.general_banking,
                R.drawable.digital_banking,
                R.drawable.retail_credit,
                R.drawable.msme,
                R.drawable.priority_sector,
                R.drawable.recovery,

        };
        ImageView back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        CustomGrid_Adapter adapter = new CustomGrid_Adapter(this, web, imageId,gridColor);
        grid = (GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  String api_key = prefs.getStringValueForTag(Constants.API_KEY);
                if (position == 0) {

                    Intent i = new Intent(Product_And_Services_Activity.this, Recovery_and_Low_Activity.class);
                    i.putExtra("position","1");
                    i.putExtra("name","Branch Business");
                    startActivity(i);
                }else if(position == 1){
                    Intent i = new Intent(Product_And_Services_Activity.this, Recovery_and_Low_Activity.class);
                    i.putExtra("position","2");
                    i.putExtra("name","Digital Banking");
                    startActivity(i);
                }

                else if(position == 2){
                    Intent i = new Intent(Product_And_Services_Activity.this, Recovery_and_Low_Activity.class);
                    i.putExtra("position","3");
                    i.putExtra("name","Retail Credit");
                    startActivity(i);
                }
                else if(position == 3){
                    Intent i = new Intent(Product_And_Services_Activity.this, Recovery_and_Low_Activity.class);
                    i.putExtra("position","4");
                    i.putExtra("name","MSME");
                    startActivity(i);
                }
                else if(position == 4){
                    Intent i = new Intent(Product_And_Services_Activity.this, Recovery_and_Low_Activity.class);
                    i.putExtra("position","5");
                    i.putExtra("name","Priority Sector");
                    startActivity(i);
                }
                else if(position == 5){
                    Intent i = new Intent(Product_And_Services_Activity.this, Recovery_and_Low_Activity.class);
                    i.putExtra("position","6");
                    i.putExtra("name","Recovery & Law");
                    startActivity(i);
                }



            }
        });





//
//        CircleImageView retails =(CircleImageView)findViewById(R.id.retails);
//        CircleImageView branch =(CircleImageView)findViewById(R.id.branch);
//        CircleImageView digital_banking =(CircleImageView)findViewById(R.id.digital_banking);
//        CircleImageView msme =(CircleImageView)findViewById(R.id.msme);
//        CircleImageView priority =(CircleImageView)findViewById(R.id.priority);
//        CircleImageView recovery_and_low =(CircleImageView)findViewById(R.id.recovery_and_low);
//        ImageView back = (ImageView)findViewById(R.id.back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        branch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Product_And_Services_Activity.this, Branch_Business_Activity.class);
//                startActivity(i);
//            }
//        });
//        msme.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Product_And_Services_Activity.this, MSME_Activity.class);
//                startActivity(i);
//            }
//        });
//
//        priority.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Product_And_Services_Activity.this, Priority_Sector_Activity.class);
//                startActivity(i);
//            }
//        });
//        recovery_and_low.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Product_And_Services_Activity.this, Recovery_and_Low_Activity.class);
//                startActivity(i);
//            }
//        });
//        digital_banking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Product_And_Services_Activity.this, Digital_Banking_Activity.class);
//                startActivity(i);
//            }
//        });
//        retails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Product_And_Services_Activity.this, Retails_Credit_Activity.class);
//                startActivity(i);
//            }
//        });
//
//

    }
}
