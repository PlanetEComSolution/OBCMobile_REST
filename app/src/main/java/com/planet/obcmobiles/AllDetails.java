package com.planet.obcmobiles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AllDetails extends Activity {
    Button b1, header_button;
    ListView l1;
    int lp = 0;
    String a, b, c, d, p, q;
    SweetAlertDialog sweetAlertDialog;
    private ProgressDialog pDialog;
    public static int count;
    SubAdapter2 adapter1;
    ArrayList<String> Atmname = new ArrayList<>();
    ArrayList<String> Atmaddress = new ArrayList<>();
    ArrayList<String> Atmdistance = new ArrayList<>();

    ArrayList<String> branchname = new ArrayList<>();
    ArrayList<String> branchaddress = new ArrayList<>();
    ArrayList<String> branchdistance = new ArrayList<>();
    ArrayList<String> atmlatList = new ArrayList<>();
    ArrayList<String> atmlongList = new ArrayList<>();
    ArrayList<String> branchlatList = new ArrayList<>();
    ArrayList<String> branchlongList = new ArrayList<>();

    ArrayList<String> myAtmbranch = new ArrayList<String>();
    ArrayList<String> mydistance = new ArrayList<String>();
    ArrayList<String> myAtmadddress = new ArrayList<String>();
    ArrayList<String> headerdarta = new ArrayList<String>();/// datat insert manualy


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_details);

        try{
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                Window window = AllDetails.this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(AllDetails.this.getResources().getColor(R.color.Green_primary));
            }
        }catch(Exception e){
e.printStackTrace();
        }
        b1 = (Button) findViewById(R.id.button);
        l1 = (ListView) findViewById(R.id.listView);
        header_button = (Button) findViewById(R.id.tital_button);
        Intent i = getIntent();
        count = i.getIntExtra("value1", count);

        myAtmbranch = i.getStringArrayListExtra("b");
        mydistance = i.getStringArrayListExtra("d");
        myAtmadddress = i.getStringArrayListExtra("ad");

        atmlatList = i.getStringArrayListExtra("ATM_LAT");
        atmlongList = i.getStringArrayListExtra("ATM_LONG");
        Atmname = i.getStringArrayListExtra("ATM_NAME");
        Atmaddress = i.getStringArrayListExtra("ATM_ADDRESS");
        Atmdistance = i.getStringArrayListExtra("ATM_DISTANCE");
        branchlatList = i.getStringArrayListExtra("BRANCH_LAT");
        branchlongList = i.getStringArrayListExtra("BRANCH_LONG");
        branchname = i.getStringArrayListExtra("BRANCH_NAME");
        branchaddress = i.getStringArrayListExtra("BRANCH_ADDRESS");
        branchdistance = i.getStringArrayListExtra("BRANCH_DISTANCE");

        setdatatoadapter();

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                try {
                    if (AllDetails.count == 0) {

                        int g = myAtmbranch.size();

                        if (myAtmbranch.size() >= 0 || myAtmbranch.size() <= 4) {
                            String k = atmlongList.get(position);
                            String bk = atmlatList.get(position);

                          //  System.out.println("data ON FUCK!" + position + "  " + g + " " + k + " " + bk);

                        }
                    }
                    if (AllDetails.count == 3 || AllDetails.count == 2) {
                        a = atmlatList.get(position);
                        b = atmlongList.get(position);
                        p=  Atmaddress.get(position);
                        c = branchlatList.get(position);
                        d = branchlongList.get(position);
                        q=branchaddress.get(position);


                        Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(AllDetails.this, AndroidGoogleMapsActivity.class);
                        i.putExtra("value2", count);
                        i.putExtra("ATM", a);
                        i.putExtra("ATM1", b);
                        i.putExtra("BRANCH", c);
                        i.putExtra("BRANCH1", d);
                        i.putExtra("ATM_ADDRESS_POSITION",p);
                        i.putExtra("BRANCH_ADDRESS_POSITION",q);

                        //
                        i.putStringArrayListExtra("b",myAtmbranch);
                        i.putStringArrayListExtra("d",mydistance);
                        i.putStringArrayListExtra("ad",myAtmadddress);
                        i.putStringArrayListExtra("ATM_NAME",Atmname);
                        i.putStringArrayListExtra("ATM_DISTANCE",Atmdistance);
                        i.putStringArrayListExtra("BRANCH_NAME",branchname);
                        i.putStringArrayListExtra("BRANCH_DISTANCE",branchdistance);
                        i.putStringArrayListExtra("ATM_LAT_LIST", (ArrayList<String>) atmlatList);
                        i.putStringArrayListExtra("ATM_LONG_LIST", (ArrayList<String>) atmlongList);
                        i.putStringArrayListExtra("ATM_ADDRESS", (ArrayList<String>) Atmaddress);
                        i.putStringArrayListExtra("BRANCH_LAT_LIST", (ArrayList<String>) branchlatList);
                        i.putStringArrayListExtra("BRANCH_LONG_LIST", (ArrayList<String>) branchlongList);
                        i.putStringArrayListExtra("BRANCH_ADDRESS", (ArrayList<String>) branchaddress);
                        //

                        i.putExtra("isFromDetailActivity", true);
             /*  i.putStringArrayListExtra("ATM_LONG_LIST", (ArrayList<String>) atmlongList);
                i.putStringArrayListExtra("BRANCH_LAT_LIST", (ArrayList<String>) branchlatList);
                i.putStringArrayListExtra("BRANCH_LONG_LIST", (ArrayList<String>) branchlongList);*/
                        startActivity(i);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (atmlatList.size() <= 0 && atmlongList.size() <= 0 || branchlatList.size() <=0 && branchlongList.size() <=0) {
                    SweetAlertDialog alertDialog = new SweetAlertDialog(AllDetails.this, SweetAlertDialog.ERROR_TYPE);
                    alertDialog.setTitleText("Can't Take you on Map, Check your Data");
                    alertDialog.show();
                }
                Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_LONG).show();
                Intent i = new Intent(AllDetails.this, AndroidGoogleMapsActivity.class);
                i.putExtra("value", count);

                //

                 i.putStringArrayListExtra("b",myAtmbranch);
                 i.putStringArrayListExtra("d",mydistance);
                 i.putStringArrayListExtra("ad",myAtmadddress);
                 i.putStringArrayListExtra("ATM_NAME",Atmname);
                 i.putStringArrayListExtra("ATM_DISTANCE",Atmdistance);
                 i.putStringArrayListExtra("BRANCH_NAME",branchname);
                 i.putStringArrayListExtra("BRANCH_DISTANCE",branchdistance);

                //

                i.putStringArrayListExtra("ATM_LAT_LIST", (ArrayList<String>) atmlatList);
                i.putStringArrayListExtra("ATM_LONG_LIST", (ArrayList<String>) atmlongList);
                i.putStringArrayListExtra("ATM_ADDRESS", (ArrayList<String>) Atmaddress);
                i.putStringArrayListExtra("BRANCH_LAT_LIST", (ArrayList<String>) branchlatList);
                i.putStringArrayListExtra("BRANCH_LONG_LIST", (ArrayList<String>) branchlongList);
                i.putStringArrayListExtra("BRANCH_ADDRESS", (ArrayList<String>) branchaddress);
                i.putExtra("isFromDetailActivity", true);


                //
               // i.putStringArrayListExtra("BRANCH_ADDRESS", (ArrayList<String>) branchaddress);

                //
                startActivity(i);
                finish();

            }
        });

        header_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AllDetails.this, Home.class);
                startActivity(i);
                finish();
            }
        });

    }


    public void setdatatoadapter() {
        headerdarta.add("OBC ATM");
        headerdarta.add("OBC ATM");
        headerdarta.add("OBC ATM");
        headerdarta.add("OBC ATM");
        headerdarta.add("OBC ATM");
        headerdarta.add("OBC Branch");
        headerdarta.add("OBC Branch");
        headerdarta.add("OBC Branch");
        headerdarta.add("OBC Branch");
        headerdarta.add("OBC Branch");


        if (AllDetails.count == 0)
        {
            adapter1 = new SubAdapter2(AllDetails.this, R.layout.list_item, myAtmbranch, mydistance, myAtmadddress, headerdarta, atmlatList, atmlongList, branchlatList, branchlongList);
            l1.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();

        }
        else
        {
            SubAdapter adapter = new SubAdapter(AllDetails.this, R.layout.list_item, Atmname, Atmdistance, branchname, branchaddress, branchdistance, Atmaddress, branchlongList,
                    branchlatList, atmlongList, atmlatList);
            if (Atmname == null || Atmdistance == null || branchname == null || branchaddress == null || branchdistance == null || Atmaddress == null || branchlongList == null || branchlatList == null || atmlongList == null || atmlatList == null) {
                b1.setVisibility(View.GONE);
                SweetAlertDialog alertDialog = new SweetAlertDialog(AllDetails.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                alertDialog.setCustomImage(R.drawable.logo);
                alertDialog.setCancelable(false);
                alertDialog.setContentText("Network Problem! Data not found");
                alertDialog.setTitleText("Oops!");
                alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(AllDetails.this, AndroidGoogleMapsActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
                alertDialog.show();

            } else {
                b1.setVisibility(View.VISIBLE);
                l1.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }


        }
    }


    class SubAdapter extends ArrayAdapter<String> {
        List<String> Atmname;
        List<String> Atmdistance;
        List<String> branchname;
        List<String> branchaddress;
        List<String> branchdistance;
        List<String> Atmaddress;
        List<String> branchlongList;
        List<String> branchlatList;
        List<String> atmlongList;
        List<String> atmlatList;


        Context context;

        public SubAdapter(Context context, int R, List<String> Atmname,
                          List<String> Atmdistance, List<String> branchname, List<String> branchaddress, List<String> branchdistance, List<String> Atmaddress, List<String> branchlongList,
                          List<String> branchlatList, List<String> atmlongList, List<String> atmlatList) {
            super(context, R, Atmname);


            this.context = context;
            this.Atmname = Atmname;
            this.Atmdistance = Atmdistance;
            this.branchname = branchname;
            this.branchaddress = branchaddress;
            this.branchdistance = branchdistance;
            this.Atmaddress = Atmaddress;
            this.branchlongList = branchlongList;
            this.branchlatList = branchlatList;
            this.atmlongList = atmlongList;
            this.atmlatList = atmlatList;

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item, parent, false);
                holder = new ViewHolder();
                holder.tatm = (TextView) convertView.findViewById(R.id.textView4);
                holder.tbranch = (TextView) convertView.findViewById(R.id.textView5);
                holder.atmtital = (TextView) convertView.findViewById(R.id.textView);
                holder.atmDis_tital = (TextView) convertView.findViewById(R.id.textView3);
                holder.atmDistance = (TextView) convertView.findViewById(R.id.textView6);
                holder.branchtital = (TextView) convertView.findViewById(R.id.textView2);
                holder.branchDis_tital = (TextView) convertView.findViewById(R.id.textView7);
                holder.branchDistance = (TextView) convertView.findViewById(R.id.textView8);
                holder.atm_addrs_tital = (TextView) convertView.findViewById(R.id.textView10);
                holder.atm_addrs = (TextView) convertView.findViewById(R.id.textView11);
                holder.atm_header = (TextView) convertView.findViewById(R.id.textView9);
                holder.branch_addrs = (TextView) convertView.findViewById(R.id.textView14);
                holder.branch_addrs_tital = (TextView) convertView.findViewById(R.id.textView13);
                holder.branch_header = (TextView) convertView.findViewById(R.id.textView12);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final String b = Atmname.get(position);
            final String rt = Atmdistance.get(position);
            final String p = branchname.get(position);
            final String q = branchdistance.get(position);
            final String a_addr = Atmaddress.get(position);
            final String b_addr = branchaddress.get(position);


            if (AllDetails.count == 2) {
                holder.tatm.setVisibility(View.GONE);
                holder.atmtital.setVisibility(View.GONE);
                holder.atmDis_tital.setVisibility(View.GONE);
                holder.atmDistance.setVisibility(View.GONE);
                holder.atm_header.setVisibility(View.GONE);
                holder.atm_addrs.setVisibility(View.GONE);
                holder.atm_addrs_tital.setVisibility(View.GONE);
                holder.tatm.setText(b);
                holder.atmDistance.setText(rt + " " + " KM.");
                holder.tbranch.setText(p);
                holder.branchDistance.setText(q + " " + " KM.");
                holder.atm_addrs.setText(a_addr);
                holder.branch_addrs.setText(b_addr);


            } else if (AllDetails.count == 3) {
                holder.tbranch.setVisibility(View.GONE);
                holder.branchtital.setVisibility(View.GONE);
                holder.branchDis_tital.setVisibility(View.GONE);
                holder.branchDistance.setVisibility(View.GONE);
                holder.branch_header.setVisibility(View.GONE);
                holder.branch_addrs.setVisibility(View.GONE);
                holder.branch_addrs_tital.setVisibility(View.GONE);
                holder.tatm.setText(b);
                holder.atmDistance.setText(rt + " " + " KM.");
                holder.tbranch.setText(p);
                holder.branchDistance.setText(q + " " + " KM.");
                holder.atm_addrs.setText(a_addr);
                holder.branch_addrs.setText(b_addr);

            }


            /*System.out.println(b);
            System.out.println(rt);
            System.out.println(rt);
            System.out.println(q);*/


            return convertView;
        }
    }


    public class SubAdapter2 extends ArrayAdapter<String> {

        List<String> myAtmbranch = new ArrayList<>();
        List<String> mydistance = new ArrayList<>();
        List<String> myAtmadddress = new ArrayList<>();
        List<String> headerdarta = new ArrayList<>();
        List<String> atmlatList = new ArrayList<>();
        List<String> atmlongList = new ArrayList<>();
        List<String> branchlatList = new ArrayList<>();
        List<String> branchlongList = new ArrayList<>();

        Context context;

        public SubAdapter2(Context context, int R, ArrayList<String> a, ArrayList<String> b, ArrayList<String> ad, ArrayList<String> headerdarta, List<String> atmlatList, List<String> atmlongList, List<String> branchlatList
                , List<String> branchlongList) {
            super(context, R, a);


            this.context = context;
            myAtmbranch = a;
            mydistance = b;
            myAtmadddress = ad;
            this.headerdarta = headerdarta;
            this.atmlatList = atmlatList;
            this.atmlongList = atmlongList;
            this.branchlatList = branchlatList;
            this.branchlongList = branchlongList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item, parent, false);
                holder = new ViewHolder();
                holder.tatm = (TextView) convertView.findViewById(R.id.textView4);
                holder.tbranch = (TextView) convertView.findViewById(R.id.textView5);
                holder.atmtital = (TextView) convertView.findViewById(R.id.textView);
                holder.atmDis_tital = (TextView) convertView.findViewById(R.id.textView3);
                holder.atmDistance = (TextView) convertView.findViewById(R.id.textView6);
                holder.branchtital = (TextView) convertView.findViewById(R.id.textView2);
                holder.branchDis_tital = (TextView) convertView.findViewById(R.id.textView7);
                holder.branchDistance = (TextView) convertView.findViewById(R.id.textView8);
                holder.atm_addrs_tital = (TextView) convertView.findViewById(R.id.textView10);
                holder.atm_addrs = (TextView) convertView.findViewById(R.id.textView11);
                holder.atm_header = (TextView) convertView.findViewById(R.id.textView9);
                holder.branch_addrs = (TextView) convertView.findViewById(R.id.textView14);
                holder.branch_addrs_tital = (TextView) convertView.findViewById(R.id.textView13);
                holder.branch_header = (TextView) convertView.findViewById(R.id.textView12);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            final String mb = myAtmbranch.get(position);
            final String md = mydistance.get(position);
            final String ma = myAtmadddress.get(position);
            final String hd = headerdarta.get(position);

            holder.tatm.setText(mb);
            holder.atmDistance.setText(ma + " KM.");
            holder.atm_addrs.setText(md);
            holder.atm_header.setText(hd);
            holder.tbranch.setVisibility(View.GONE);
            holder.branchtital.setVisibility(View.GONE);
            holder.branchDis_tital.setVisibility(View.GONE);
            holder.branchDistance.setVisibility(View.GONE);
            holder.branch_header.setVisibility(View.GONE);
            holder.branch_addrs.setVisibility(View.GONE);
            holder.branch_addrs_tital.setVisibility(View.GONE);

            return convertView;
        }

    }

    public class ViewHolder {
        TextView tatm, tbranch, atmtital, atmDis_tital, atmDistance, branchtital, branchDis_tital, branchDistance, atm_addrs_tital, atm_addrs, atm_header, branch_addrs_tital, branch_addrs, branch_header;

    }

}



