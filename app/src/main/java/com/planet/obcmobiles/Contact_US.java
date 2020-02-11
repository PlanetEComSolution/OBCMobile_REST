package com.planet.obcmobiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class Contact_US extends AppCompatActivity {
    ArrayList<String> number = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> img = new ArrayList<>();
    Adapter adapter;
    private ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        listView = (ListView) findViewById(R.id.contct_list);
        number.add("CALL Us:- ");
        number.add("18001801235");
        number.add("01202580001");
        number.add("08067205757");
        number.add("08067205767");
        number.add("MESSAGE Us:- ");
        number.add("09243101342");
        number.add("9915622622");
        number.add("9915622622");
        number.add("9915622622");
        number.add("9915622622");
        number.add("Mail Us:- ");
        number.add("customercare@obc.co.in");



        name.add("");
        name.add("Customer Care Number First");
        name.add("Customer Care Number Second");
        name.add("Missed Call to know the Balance");
        name.add("Missed call for Mini Statement");
        name.add("");
        name.add("Register Your E-mail id by typing SMS and send it to");
        name.add("Balance Enquiry: ACBAL < > 14 digit A/C no.");
        name.add("Statement on SMS: STM < > 14 digit A/C no.");
        name.add("Cheque Stop Facility: STOP < > 14 digit A/C no.");
        name.add("ATM Card Hotlisting: BLOCK < > 14 digit A/C no.");
        name.add("");
        name.add("For any Inquiry:-");



        img.add(R.color.white);
        img.add(R.drawable.call);
        img.add(R.drawable.call);
        img.add(R.drawable.call);
        img.add(R.drawable.call);
        img.add(R.color.white);
        img.add(R.drawable.msg);
        img.add(R.drawable.msg);
        img.add(R.drawable.msg);
        img.add(R.drawable.msg);
        img.add(R.drawable.msg);
        img.add(R.color.white);
        img.add(R.color.white);



        int a = number.size();

        for (int aa = 0; aa < number.size(); aa++) {
            adapter = new Adapter(this, number, name, img);
        }
        listView.setAdapter(adapter);

    }

}
