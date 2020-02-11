package com.planet.obcmobiles;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by planet on 11/18/15.
 */
public class Adapter extends BaseAdapter {
    String z,zz;
    int j,k,l,m,n;
    Holder holder;
    private Context context;
    ArrayList<String> name;
    ArrayList<String> number;
    ArrayList<Integer> img;
    //private int[] img;
    // String name[];

    public Adapter(Context context, ArrayList<String> number, ArrayList<String> name, ArrayList<Integer> img) {
        this.context = context;
        this.name = name;
        this.number = number;
        this.img = img;

    }


    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_contact, null);
            holder.name = (TextView) convertView.findViewById(R.id.contct_name);
            holder.num = (TextView) convertView.findViewById(R.id.nmbr);
            holder.imageView = (ImageView) convertView.findViewById(R.id.cntct_image);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        int az = name.size();

        holder.name.setText(name.get(position));
        holder.num.setText(number.get(position));
        holder.imageView.setImageResource(img.get(position));

        holder.num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                z = number.get(position);
                zz=name.get(position);
                j=name.indexOf(zz);

                int hell = img.get(position);

                int u = 0;

                switch (u) {
                    case 0:
                        if (hell == 2130837578)
                            calling(z);


                     if (hell!=2130837578 && hell!=2130837663){
                            email(z);
                       }

                       if(hell==2130837663)
                           message(z);

                }

            }


        });

        return convertView;
    }

    public void calling(String a) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + a));
        if (callIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(callIntent);
        }
    }

    public void message(String a) {
        Intent callIntent = new Intent(Intent.ACTION_VIEW);
        callIntent.setData(Uri.parse("sms:" + a));
       if(j==6)
        callIntent.putExtra("sms_body", "SMS " );
       if(j==7)
            callIntent.putExtra("sms_body", "ACBAL " );
        if(j==8)
            callIntent.putExtra("sms_body", "STM " );
        if(j==9)
            callIntent.putExtra("sms_body", "STOP " );
        if(j==10)
            callIntent.putExtra("sms_body", "BLOCK " );
        if (callIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(callIntent);
        }
    }

    public void email(String a){
        String[] TO = {a};
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL,TO);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Customer service");
        //intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
        context.startActivity(Intent.createChooser(intent, "Send Email"));

    }

    class Holder {
        TextView name, num;
        ImageView imageView;
    }
}
