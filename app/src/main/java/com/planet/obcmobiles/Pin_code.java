package com.planet.obcmobiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Pin_code extends AppCompatActivity {

    EditText enter_pincode;
    Button submit_pin;
    String pin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);
        enter_pincode=(EditText)findViewById(R.id.pin_editText);

        try{ if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = Pin_code.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Pin_code.this.getResources().getColor(R.color.Green_primary));
        }
        }catch(Exception e){
            e.printStackTrace();
        }

        submit_pin= (Button) findViewById(R.id.btn_pin);
        submit_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enter_pincode.length()==6){
                    pin = enter_pincode.getText().toString();
                    Intent intent= new Intent(getApplicationContext(),AndroidGoogleMapsActivity.class);
                    intent.putExtra("CHECK",0);
                    intent.putExtra("PIN_NUMBER",pin);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(Pin_code.this, "Please enter a valid pin code", Toast.LENGTH_SHORT).show();
                    Toast toast=new Toast(Pin_code.this);
                    toast.setGravity(Gravity.CENTER,0,0);
                }


            }
        });

    }

}
