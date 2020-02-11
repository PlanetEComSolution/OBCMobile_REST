package com.planet.obcmobiles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Compound_intrest_Calc extends AppCompatActivity {
    private EditText princi, inter, final_emi;
    private Button calc, home, refrsh;
    private Spinner spinner;
    String selected_item;
    String amount, interest, intr_value;
    int minday, maxday;
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compound_intrest__calc);
        try {
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                Window window = Compound_intrest_Calc.this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Compound_intrest_Calc.this.getResources().getColor(R.color.Green_primary));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        princi = (EditText) findViewById(R.id.amt_editText);
        spinner = (Spinner) findViewById(R.id.spinner_id);
        calc = (Button) findViewById(R.id.cal);
        inter = (EditText) findViewById(R.id.intr_editText);
        home = (Button) findViewById(R.id.button_home);
        refrsh = (Button) findViewById(R.id.reset_button);
        final_emi = (EditText) findViewById(R.id.emi_edit);
        final_emi.setKeyListener(null);
        Intent i = getIntent();
        intr_value = i.getStringExtra("value");
        minday = i.getIntExtra("MIN_DAY", 0);
        maxday = i.getIntExtra("MAX_DAY", 0);
        if (intr_value != null) {
            inter.setText(intr_value);
            inter.setKeyListener(null);
        }


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_item = spinner.getItemAtPosition(position).toString();
                int a = spinner.getSelectedItemPosition();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        refrsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                princi.setText(null);
                final_emi.setText(null);
                spinner.setSelection(0);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Compound_intrest_Calc.this, Home.class);
                startActivity(i);
                finish();
            }
        });

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (princi.getText().length() == 0) {
                    princi.setError("Please Enter a valid Amount");
                }
                else if (inter.getText().length() == 0) {
                    inter.setError("Please Enter a valid Interest Rate");
                }
                else if (spinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getApplicationContext(), "Please Select Months...", Toast.LENGTH_SHORT).show();
                } else {
                    amount = princi.getText().toString();
                    interest = inter.getText().toString();
                    a = spinner.getSelectedItemPosition();
                    int item = Integer.parseInt(selected_item);
                   // Log.d("TAG", "INTEREST " + interest + " " + intr_value + " " + item);

                    calculate();
                    hideSoftKeyboard(Compound_intrest_Calc.this);
                }
            }
        });
    }

    void calculate() {

        if (see(Integer.parseInt(spinner.getSelectedItem().toString()), validation(minday, maxday)) == false) {
            Toast.makeText(Compound_intrest_Calc.this, "Please select correct value, According to your selection", Toast.LENGTH_SHORT).show();
            spinner.setSelection(0);
            final_emi.setText(null);

        } else {

            try {
                int loanAmount = Integer.parseInt(amount);
                double rateOfInterest = Double.parseDouble(interest);
                double final_rateofIntrest = rateOfInterest / 400.0;
                double result = 1 + final_rateofIntrest;
                result = Math.pow(result, a);
                result = result * loanAmount;
                double ans = Math.round(result);
                String answer = Double.toString(ans);
                final_emi.setText(answer);
            } catch (Exception e) {
                princi.setError("Please Enter a valid Amount");
            }

        }
    }


    public boolean see(int num, List<Integer> list) {
        boolean result = false;
        for (int i = 0; i < list.size(); i++) {
            int nnn = list.get(i);


            if (num == nnn) {
                result = true;
            }
        }
        return result;
    }


    public List<Integer> validation(int min, int max) {

        if (max == 179) {
            max = max + 1;
        }
        if (max == 269) {
            max = max + 1;
        }
        List<Integer> data = new ArrayList<>();
        if ((max - min) < 45) {
            data.add(3);

        } else {
            int min_day_count = min / 30;
            int max_day_count = max / 30;

            for (int i = min_day_count; i <= max_day_count; i++) {
                if (i > max_day_count) {
                } else {
                    data.add(i);
                    i = i + 2;
                }
            }
        }
        return data;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
