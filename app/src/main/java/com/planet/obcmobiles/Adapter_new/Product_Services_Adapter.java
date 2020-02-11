package com.planet.obcmobiles.Adapter_new;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.planet.obcmobiles.Model.Product_model;
import com.planet.obcmobiles.R;

import java.util.ArrayList;

/**
 * Created by Admin on 11/21/2017.
 */

public class Product_Services_Adapter extends RecyclerView.Adapter<Product_Services_Adapter.CustomViewHolder> {
    private ArrayList<Product_model> detail;

    Context context;
    View view;

    public Product_Services_Adapter(Context context, ArrayList<Product_model> tender_list) {
        this.detail = tender_list;
        this.context = context;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list_item, null);
        return new CustomViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, final int i) {

        try {
            customViewHolder.product.setText(detail.get(i).getProduct_services());


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @Override
    public int getItemCount() {
        return detail.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder  {
        TextView product;

        public CustomViewHolder(View view) {
            super(view);
            //itemView.setOnClickListener(this);
            this.product = (TextView) view.findViewById(R.id.product);

        }
    }
}
