package com.planet.obcmobiles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EMI extends AppCompatActivity {
    boolean run = false;
    boolean sign = false;
    TextView textView, textView1,textView11, headr;
    private EditText princi, inter, period, final_emi;
    private Button calc, home, refrsh;
    String amount, interest, month;
    int depo_amt, depo_month;
    double depo_intr, rateOfInterest;
    String loan_value;
    int numberOfMonths;
    Long loanAmount;
    ArrayList<Integer> min_List= new ArrayList<>();;
    ArrayList<Integer> max_List= new ArrayList<>();;
    ArrayList<String> rate_selected= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi);
        try {
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                Window window = EMI.this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(EMI.this.getResources().getColor(R.color.Green_primary));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        textView = (TextView) findViewById(R.id.textView15);
        textView1 = (TextView) findViewById(R.id.textView19);
        textView11 = (TextView) findViewById(R.id.textView191);
        headr = (TextView) findViewById(R.id.textView21);
        princi = (EditText) findViewById(R.id.amt_editText);
        inter = (EditText) findViewById(R.id.intr_editText);
        period = (EditText) findViewById(R.id.period_editText);
        home = (Button) findViewById(R.id.button_home);
        refrsh = (Button) findViewById(R.id.reset_button);
        final_emi = (EditText) findViewById(R.id.emi_edit);
        final_emi.setKeyListener(null);
        calc = (Button) findViewById(R.id.cal);
        Intent i = getIntent();
        final String intr_value = i.getStringExtra("value");
        loan_value = i.getStringExtra("LOAN_value");
        final String loan_name = i.getStringExtra("LOAN_name");
        final String loan_amount = i.getStringExtra("LOAN_amount");
        final int intr_name = i.getIntExtra("value_name", 0);
        textView11.setVisibility(View.GONE);

       min_List= i.getIntegerArrayListExtra("MIN_DAY");
       max_List= i.getIntegerArrayListExtra("MAX_DAY");
        rate_selected= i.getStringArrayListExtra("RAATE");

        // final String intr_name = i.getStringExtra("value_name");
        // Toast.makeText(EMI.this, ""+intr_name, Toast.LENGTH_SHORT).show();
        if (loan_value != null) {
            textView11.setVisibility(View.VISIBLE);
            sign = true;
            inter.setText(loan_value);
            inter.setKeyListener(null);
        }

        if (intr_value != null) {
            run = true;
            textView11.setVisibility(View.VISIBLE);
            inter.setText(intr_value);
            inter.setKeyListener(null);
            period.setHint("(In No. of Days)");
            textView1.setText(" Maturity Value ");
            headr.setText("Interest Calculator");
            textView.setText("FDR Calculator with simple interest\n\nPlease enter principal deposit amount and press calculate to see the interest amount.");

        }

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!run) && (!sign)) {
                    if (princi.getText().length() == 0) {
                        princi.setError("Please Enter a valid Amount");
                    } else if (inter.getText().length() == 0) {
                        inter.setError("Please Enter a valid Interest Rate");
                    } else if (period.getText().length() == 0) {
                        period.setError("Please Enter a valid month");
                    } else {
                        String amount = princi.getText().toString();
                        String interest = inter.getText().toString();
                        String month = period.getText().toString();

                        int loanAmount = Integer.parseInt(amount);
                        double rateOfInterest = Double.parseDouble(interest);
                        int numberOfMonths = Integer.parseInt(month);

                        double temp = 1200;
                        double interestPerMonth = rateOfInterest / temp;
                        //System.out.println(interestPerMonth);

                        double onePlusInterestPerMonth = 1 + interestPerMonth;
                        //System.out.println(onePlusInterestPerMonth);

                        double powerOfOnePlusInterestPerMonth = Math.pow(onePlusInterestPerMonth, numberOfMonths);
                        //System.out.println(powerOfOnePlusInterestPerMonth);

                        double powerofOnePlusInterestPerMonthMinusOne = powerOfOnePlusInterestPerMonth - 1;
                        //System.out.println(powerofOnePlusInterestPerMonthMinusOne);

                        double divides = powerOfOnePlusInterestPerMonth / powerofOnePlusInterestPerMonthMinusOne;

                        double principleMultiplyInterestPerMonth = loanAmount * interestPerMonth;
                        //System.out.println(principleMultiplyInterestPerMonth);

                        double totalEmi = principleMultiplyInterestPerMonth * divides;

                        double finalValue = Math.round(totalEmi * 100.0) / 100.0;


                        String a = Double.toString(finalValue);
                        final_emi.setText(a);


                    }
                }
                View view = EMI.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                if (run) {
                    if (princi.getText().length() == 0) {
                        princi.setError("Please Enter a valid Amount");
                    } else if (inter.getText().length() == 0) {
                        inter.setError("Please Enter a valid Interest Rate");
                    } else if (period.getText().length() == 0) {
                        period.setError("Please Enter no. of days");
                    } else {
                        amount = princi.getText().toString();
                        interest = inter.getText().toString();
                        month = period.getText().toString();

                        depo_amt = Integer.parseInt(amount);
                        depo_intr = Double.parseDouble(interest);
                        depo_month = Integer.parseInt(month);

                        for(int i=0;i<min_List.size();i++){

                           int MINIMUM_DAY = min_List.get(i);
                           int MAXIMUM_DAY= max_List.get(i);
                            String rate =rate_selected.get(i);
                            if(intr_value.equals(rate) ){
                                if (depo_month >= MINIMUM_DAY && depo_month <= MAXIMUM_DAY) {
                                    FDR_claculation();
                                } else {
                                    period.setText(null);
                                    final_emi.setText(null);
                                    Toast.makeText(EMI.this, "Please enter a valid time period according to your Selection", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                       /*if (intr_name == 0 && depo_month >= 7 && depo_month <= 14) {
                            FDR_claculation();
                        } else if (intr_name == 1 && depo_month >= 15 && depo_month <= 30) {
                            FDR_claculation();
                        } else if (intr_name == 2 && depo_month >= 31 && depo_month <= 45) {
                            FDR_claculation();
                        } else if (intr_name == 3 && depo_month >= 46 && depo_month <= 90) {
                            FDR_claculation();
                        } else if (intr_name == 4 && depo_month >= 91 && depo_month <= 179) {
                            FDR_claculation();
                        } else if (intr_name == 5 && depo_month >= 180 && depo_month <= 269) {
                            FDR_claculation();
                        } else if (intr_name == 6 && depo_month >= 270 && depo_month <= 364) {
                            FDR_claculation();
                        } else if (intr_name == 7 && depo_month >= 365 && depo_month <= 729) {
                            FDR_claculation();
                        } else if (intr_name == 8 && depo_month >= 730 && depo_month <= 1094) {
                            FDR_claculation();
                        } else if (intr_name == 9 && depo_month >= 1095 && depo_month <= 1824) {
                            FDR_claculation();
                        } else if (intr_name == 10 && depo_month >= 1825 && depo_month <= 3650) {
                            FDR_claculation();
                        }  else if (intr_name == 11 && depo_month >= 3650) {
                            FDR_claculation();
                        } else {
                            period.setText(null);
                            final_emi.setText(null);
                            Toast.makeText(EMI.this, "Please enter a valid time period according to your Selection", Toast.LENGTH_LONG).show();
                        }*/
                    }
                }

                try{
                if (sign) {
                    if (princi.getText().length() == 0) {
                        princi.setError("Please Enter a valid Amount");
                    } else if (inter.getText().length() == 0) {
                        inter.setError("Please Enter a valid Interest Rate");
                    } else if (period.getText().length() == 0) {
                        period.setError("Please Enter a valid month");
                    } else {
                        String amount = princi.getText().toString();
                        String interest = inter.getText().toString();
                        String month = period.getText().toString();

                        loanAmount = Long.valueOf(amount);
                        rateOfInterest = Double.parseDouble(interest);
                        numberOfMonths = Integer.parseInt(month);
                        calculate_Loan();
                        if ((loan_name.contains("Car Loan")) && (numberOfMonths >= 1 && numberOfMonths <= 120) && (loanAmount >= 1 && loanAmount <= 10000000)) {
                            calculate_Loan();
                        }
                        else if ((loan_name.contains("Car Loan")) && (numberOfMonths >= 1 && numberOfMonths <= 120) && (loan_amount.equals("No upper limit"))) {
                            calculate_Loan();
                        }
                        else if ((loan_name.contains("Education Loan")) && (numberOfMonths >= 1 && numberOfMonths <= 180)&&(loanAmount >= 1 && loanAmount <= 400000)&&(!loan_amount.equals("upto Rs. 1.5Lac"))&&(loan_amount.equalsIgnoreCase("Upto 4.00 lacs"))) {
                            calculate_Loan();
                        }
                        else if ((loan_name.contains("Education Loan")) && (numberOfMonths >= 1 && numberOfMonths <= 180)&&(loan_amount.equals("Above 7.5 lacs"))&& (loanAmount >= 1 && loanAmount >= 750000)) {
                            calculate_Loan();
                        }
                        else if ((loan_name.contains("Education Loan")) && (numberOfMonths >= 1 && numberOfMonths <= 180)&&(loanAmount >= 1 && loanAmount >= 400000)&&(loanAmount >= 1 && loanAmount <= 750000)&&(loan_amount.equalsIgnoreCase("Above 4.00 lacs and less than 7.5 lacs"))) {
                            calculate_Loan();
                        }
                        else if ((numberOfMonths >= 1 && numberOfMonths <= 84)&&(loan_amount.equals("upto Rs. 1.5Lac"))&& (loanAmount >= 1 && loanAmount <= 150000)) {
                            calculate_Loan();
                        }

//                            if ((loan_amount.contains("Upto")) && (loanAmount >= 1 && loanAmount <= 400000)) {
//                                calculate_Loan();
//                            }
//                            else if ((loan_amount.contains("and")) && (loanAmount >= 400000 && loanAmount <= 750000)) {
//                                calculate_Loan();
//                            } else if ((loan_amount.equals("Above 7.5 lacs")) && loanAmount >= 750000) {
//                                calculate_Loan();
//                            } else if ((loan_amount.equalsIgnoreCase("upto Rs. 1.5Lac"))) {
//                                calculate_Loan();
//                            }
////                            else if ((loan_name.contains("Car Loan")) && (numberOfMonths >= 1) && (loanAmount >= 1 && loanAmount >= 10000000)) {
////                                calculate_Loan();
////                            }
//
//                            else {
//                                final_emi.setText(null);
//                                Toast.makeText(EMI.this, "Please enter a valid time period or amount according to your selection", Toast.LENGTH_LONG).show();
//                            }

                        else if ((loan_name.contains("Home Loan") && (numberOfMonths >= 1 && numberOfMonths <= 480)&&(loanAmount >= 1 && loanAmount <= 3000000))&&(loan_amount.equals("upto 3000000"))) {
                            calculate_Loan();
                        }else if ((loan_name.contains("Home Loan") && (numberOfMonths >= 1 && numberOfMonths <= 480)&&(loan_amount.equals("above 75 lac"))&&(loanAmount >= 1 && loanAmount >= 7500000))) {
                                calculate_Loan();

                        }else if ((loan_name.contains("Home Loan") && (numberOfMonths >= 1 && numberOfMonths <= 480)&&(loanAmount >= 1 && loanAmount >= 3000000)&&(loanAmount >= 1 && loanAmount <= 7500000)&&(!loan_amount.equals("upto 3000000")) )) {
                                calculate_Loan();
                        }

                        else if ((loan_name.contains("Oriental Mortgage Loan")) && (numberOfMonths >= 1 && numberOfMonths <= 84) && (loanAmount >= 1 && loanAmount <= 750000)) {
                            calculate_Loan();
                        } else if ((loan_name.contains("Personal Loan (Corp. Employees)")) && (numberOfMonths >= 1 && numberOfMonths <= 60) && (loanAmount >= 1 && loanAmount <= 500000)) {
                            calculate_Loan();
                        }
                        else if ((loan_name.contains("Personal Loan (Govt. Employees)")) && (numberOfMonths >= 1 && numberOfMonths <= 60) && (loanAmount >= 1 && loanAmount <= 500000)) {
                            calculate_Loan();
                        }
                        else if ((loan_name.contains("Personal Loan to pensioners")) && (numberOfMonths >= 1 && numberOfMonths <= 60) && (loanAmount >= 1 && loanAmount <= 1000000)) {
                            calculate_Loan();
                        }
                        else if ((loan_name.contains("Second hand car loan")) && (numberOfMonths >= 1 && numberOfMonths <= 60) && (loanAmount >= 1 && loanAmount <= 2500000)) {
                            calculate_Loan();
                        }
                        else if ((loan_name.contains("Education Loan")) && (numberOfMonths >= 1 && numberOfMonths <= 180)&&loan_amount.equalsIgnoreCase("For Category-A Institutes") || loan_amount.equalsIgnoreCase("For Category-B Institutes")) {
                            calculate_Loan();
                        }
//                        else if (loan_amount.equalsIgnoreCase("Upto 25.00 lacs")) {
//                            calculate_Loan();
//                        } else if (loan_amount.equalsIgnoreCase("Upto 10 lacs")) {
//                            calculate_Loan();
//                        } else if (loan_amount.equalsIgnoreCase("Second hand car loan")) {
//                            calculate_Loan();
//                        }
//                        else if (loan_amount.equalsIgnoreCase("Upto Rs. 1.00 Crores")) {
//                                calculate_Loan();
//                                }
                                else {
                            final_emi.setText(null);
                            Toast.makeText(EMI.this, "Please enter a valid time period or amount according to your selection", Toast.LENGTH_LONG).show();
                        }
                    } }}catch (Exception e){}
            }
        });

        refrsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                princi.setText(null);
                if(run||sign){
                    princi.setText(null);
                    period.setText(null);
                    final_emi.setText(null);
                }else{
                    princi.setText(null);
                    period.setText(null);
                    final_emi.setText(null);
                    inter.setText(null);
                }

                period.setText(null);
                final_emi.setText(null);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EMI.this, Home.class);
                startActivity(i);
                finish();
            }
        });


    }

    public void FDR_claculation() {
        double apple = ((depo_intr / 100.0) / 365);
        double i = (depo_amt * depo_month * apple);
        double pappu = Math.round(i * 100.0) / 100.0;
        String earned = Double.toString(pappu);
        double result = depo_amt + pappu;
        String final_result = Double.toString(result);
        final_emi.setText(final_result);
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(EMI.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        sweetAlertDialog.setCustomImage(R.drawable.logo);
        sweetAlertDialog.setTitleText("Interest earned");
        sweetAlertDialog.setContentText(earned + " " + "Rs.");
        sweetAlertDialog.show();
    }

    public void calculate_Loan() {
        double temp = 1200;
        double interestPerMonth = rateOfInterest / temp;
        //System.out.println(interestPerMonth);

        double onePlusInterestPerMonth = 1 + interestPerMonth;
        //System.out.println(onePlusInterestPerMonth);

        double powerOfOnePlusInterestPerMonth = Math.pow(onePlusInterestPerMonth, numberOfMonths);
        //System.out.println(powerOfOnePlusInterestPerMonth);

        double powerofOnePlusInterestPerMonthMinusOne = powerOfOnePlusInterestPerMonth - 1;
        //System.out.println(powerofOnePlusInterestPerMonthMinusOne);

        double divides = powerOfOnePlusInterestPerMonth / powerofOnePlusInterestPerMonthMinusOne;

        double principleMultiplyInterestPerMonth = loanAmount * interestPerMonth;
        //System.out.println(principleMultiplyInterestPerMonth);

        double totalEmi = principleMultiplyInterestPerMonth * divides;
       // System.out.println("EMI per month (Exact) : " + totalEmi);

        double finalValue = Math.round(totalEmi * 100.0) / 100.0;

       // System.out.println("EMI per month (Rounded) : " + finalValue);
        String a = Double.toString(finalValue);
        final_emi.setText(a);
    }
}
