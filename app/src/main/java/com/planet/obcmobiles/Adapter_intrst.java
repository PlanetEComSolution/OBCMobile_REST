package com.planet.obcmobiles;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by planet on 11/20/15.
 */
public class Adapter_intrst extends BaseAdapter {
    String s;
    String s_name;
    ArrayList<String> name;
    int a,l;
    Dialog dialog;
    boolean h=false;
    ArrayList<String> rate;
    ArrayList<Integer> minday= new ArrayList<>();
    ArrayList<Integer> maxday=new ArrayList<>();
    Context context;

    public Adapter_intrst(ArrayList<String> name, ArrayList<String> rate,ArrayList<Integer> minday,ArrayList<Integer> maxday, Context context) {
        this.name = name;
        this.rate = rate;
        this.minday = minday;
        this.maxday = maxday;
        this.context = context;
        notifyDataSetChanged();
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
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.intrst_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.duration_item);
            holder.num = (TextView) convertView.findViewById(R.id.rate_item);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.num.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        holder.name.setText(name.get(position));
       holder.num.setText(rate.get(position));

        holder.num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = rate.get(position);
                Log.i("rate","rate"+s);
                s_name=name.get(position);
                Log.i("name","name"+name);
                l=name.indexOf(s_name);
                Log.i("l","l"+l);
               a = rate.indexOf(s);


                //Log.d("ITEM_number", "" + l+"  name "+s_name);

                switch (l) {
                    case 0:
                        fdr();
                        break;
                    case 1:
                        fdr();
                        break;
                    case 2:
                        fdr();
                        break;


                    default:
                        dialog = new Dialog(context);
                        dialog.setContentView(R.layout.custom);
                        dialog.setTitle("Select Your Scheme");

                        TextView fdr_textView = (TextView) dialog.findViewById(R.id.fdr_id);
                        fdr_textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String s = rate.get(position);
                                Intent i = new Intent(context, EMI.class);
                                i.putIntegerArrayListExtra("MIN_DAY", minday);
                                i.putIntegerArrayListExtra("MAX_DAY", maxday);
                                i.putStringArrayListExtra("RAATE", rate);
                                i.putExtra("value", s);
                                i.putExtra("value_name", l);
                                context.startActivity(i);
                                dialog.dismiss();

                            }
                        });

                        TextView cdr_TextView = (TextView) dialog.findViewById(R.id.cdr_id);
                        cdr_TextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String s = rate.get(position);
                                int minn=minday.get(position);
                                int maxx=maxday.get(position);
                                Intent i = new Intent(context, Compound_intrest_Calc.class);
                                i.putExtra("value", s);
                                i.putExtra("MIN_DAY", minn);
                                i.putExtra("MAX_DAY", maxx);
                                context.startActivity(i);
                                dialog.dismiss();
                            }
                        });

                    dialog.show();
                        break;
                }
            }
        });

                return convertView;
            }


public void fdr(){
    final Dialog dialog1 = new Dialog(context);
    dialog1.setContentView(R.layout.custom);
    dialog1.setTitle("Select Your Scheme");
    TextView cdr_TextView = (TextView) dialog1.findViewById(R.id.cdr_id);
    cdr_TextView.setVisibility(View.GONE);
    TextView fdr_textView = (TextView) dialog1.findViewById(R.id.fdr_id);
    fdr_textView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, EMI.class);
            i.putIntegerArrayListExtra("MIN_DAY", minday);
            i.putIntegerArrayListExtra("MAX_DAY", maxday);
            i.putStringArrayListExtra("RAATE", rate);
            i.putExtra("value", s);
            i.putExtra("value_name",l);
          //  i.putExtra("value_name",s_name);
            context.startActivity(i);
            dialog1.dismiss();
        }

    });
    dialog1.show();
    }

    class Holder {
        TextView name, num;
    }

}