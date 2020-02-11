package com.planet.obcmobiles;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 12/1/2015.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    String data1, data2;
    private Context _context;
    public static TextView txtListChild;
    private ArrayList<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public ExpandableListAdapter(Context context, ArrayList<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (childText.contains(".")) {
            String data[] = childText.split("\\.");
            data1 = data[1].toString();
            data2 = data[0].toString();
        }

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_itemz, null);

        }

        txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        txtListChild.setText(childText);


        if (txtListChild.getText().toString().equals(null)) {
            Toast.makeText(_context, "HELLOO", Toast.LENGTH_SHORT).show();
        } else if (groupPosition == 0) {

            txtListChild.setTextColor(Color.parseColor("#000000"));
            String next = "<font color='#60A4F8'>" + data1 + "</font>";
            String htmlString = "<u>" + next + "</u>";
            //txtListChild.setTextColor(Color.parseColor("#0099FF"));
            txtListChild.setText(Html.fromHtml(data2 + " " + "<br>" + htmlString));
        }
        if (groupPosition == 1) {
            if (childText.contains("*Available only on registered Mobile numbers")) {
                txtListChild.setTextSize(13);
                txtListChild.setTextColor(Color.parseColor("#60A4F8"));
            } else {
                txtListChild.setTextColor(Color.parseColor("#000000"));
                String next = "<font color='#60A4F8'>" + data1 + "</font>";
                String htmlString = "<u>" + next + "</u>";
                // txtListChild.setTextColor(Color.parseColor("#0099FF"));
                txtListChild.setText(Html.fromHtml(data2 + " " + "<br>" + htmlString));
            }
        }
        if ((groupPosition == 2)) {
            if (childText.contains("*Available only on registered Mobile numbers")) {
                txtListChild.setTextSize(13);
                txtListChild.setTextColor(Color.parseColor("#60A4F8"));
            } else {
                txtListChild.setTextColor(Color.parseColor("#000000"));
                String next = "<font color='#60A4F8'>" + data1 + "</font>";
                String htmlString = "<u>" + next + "</u>";
                // txtListChild.setTextColor(Color.parseColor("#0099FF"));
                txtListChild.setText(Html.fromHtml(data2 + " " + "<br>" + htmlString));
            }
        }
        if ((groupPosition == 3)) {
            if (childText.contains("@")) {
                String mail = (String) txtListChild.getText();
                String next = "<font color='#60A4F8'>" + mail + "</font>";
                String htmlString = "<u>" + next + "</u>";
                txtListChild.setText(Html.fromHtml(htmlString));
            }
        }

        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        List<String> list=new ArrayList<>();
      String text=  _listDataHeader.get(groupPosition);
         list = _listDataChild.get(text);
     //   System.out.println("TAKLA " + list.size() + "  GROUP POSITION " + groupPosition + " hEADER NAME" + _listDataHeader.get(groupPosition));
        return list.size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {


        return this._listDataHeader.size();

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final String headerTitle = (String) getGroup(groupPosition);
        if (isExpanded) {
           // Log.d("PECS", headerTitle);
        }
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }


        final TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.NORMAL);
        lblListHeader.setText(headerTitle);



        if (getChildrenCount(groupPosition) <= 0) {
            convertView.setVisibility(View.GONE);
            lblListHeader.setVisibility(View.GONE);
        } else {
            convertView.setVisibility(View.VISIBLE);
            lblListHeader.setVisibility(View.VISIBLE);
        }


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}