package com.planet.obcmobiles;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by planet on 11/20/15.
 */
public class Adapter_loan extends BaseAdapter {

    ArrayList<String> parti;
    ArrayList<String> period;
    ArrayList<String> amt;
    ArrayList<String> rate;
    Context context;

    public Adapter_loan(ArrayList<String> parti, ArrayList<String> period, ArrayList<String> amt, ArrayList<String> rate, Context context) {
        this.parti = parti;
        this.period = period;
        this.amt = amt;
        this.rate = rate;
        this.context = context;
    }


    @Override
    public int getCount() {
        return rate.size();
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
       /* Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.loan_intrst_item, null);
            holder.parti = (TextView) convertView.findViewById(R.id.partical_view);
            holder.amnt = (TextView) convertView.findViewById(R.id.amount_view);
            holder.period = (TextView) convertView.findViewById(R.id.period_view);
            holder.rate = (TextView) convertView.findViewById(R.id.loan_rate);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.parti.setText(parti.get(position));
        holder.amnt.setText(amt.get(position));
        holder.period.setText(period.get(position));
        holder.rate.setText(rate.get(position));

      /*  holder.rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=rate.get(position);
                Toast.makeText(context, "hello" + s, Toast.LENGTH_SHORT).show();

            }
        });*/
        return convertView;
    }
    class Holder {
        TextView parti,amnt,period,rate;
    }
}
