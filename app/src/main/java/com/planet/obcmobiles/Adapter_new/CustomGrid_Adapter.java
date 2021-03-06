package com.planet.obcmobiles.Adapter_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.planet.obcmobiles.R;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Admin on 6/14/2017.
 */

public class CustomGrid_Adapter extends BaseAdapter {

    private final String[] col;
    private final String[] web;
    private final int[] Imageid;
    private Context mContext;


    public CustomGrid_Adapter(Context c, String[] web, int[] Imageid, String[] col) {
        mContext = c;
        this.Imageid = Imageid;
        this.col = col;
        this.web = web;
    }

    @Override
    public int getCount() {
        return web.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            CircleImageView imageView = (CircleImageView) grid.findViewById(R.id.grid_image);
            textView.setText(web[position]);
            imageView.setImageResource(Imageid[position]);
        } else {
            grid = (View) convertView;
        }
        return grid;
    }
}