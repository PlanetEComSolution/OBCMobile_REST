package com.planet.obcmobiles;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;



public class Loan_Expandable_Adapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private List<String> _listDataChild;
    private   WebView web;
    public Loan_Expandable_Adapter(Context context, List<String> listDataHeader,
                                  List<String> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.faq_question_item, null);
        }

           web = (WebView) convertView.findViewById(R.id.webView_new);
           web.clearCache(true);
           web.getSettings().setJavaScriptEnabled(true);

          // web.loadUrl("https://www.nhdc.org.in/PostApply.aspx");

       web.loadDataWithBaseURL(null, childText, "text/html", "utf-8", null);
       web.setWebViewClient(new HelloWebViewClient());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
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
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.faq_ans_item, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        if (getChildrenCount(groupPosition) == 0)
            ((ImageView) convertView.findViewById(R.id.arrow)).setVisibility(View.INVISIBLE);

        if (isExpanded) {
            ((ImageView) convertView.findViewById(R.id.arrow)).setImageDrawable(_context.getResources().getDrawable(R.drawable.down_arrow));
        } else {
            ((ImageView) convertView.findViewById(R.id.arrow)).setImageDrawable(_context.getResources().getDrawable(R.drawable.right_arrow));
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


    private class HelloWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            if (url.endsWith(".pdf")) {

                if (url != null && url.contains("=")) {
                    String Urll =url.substring(url.lastIndexOf("&Signature=") + 1).replace("%2B", "%252B");
                    if (null != Urll && Urll.length() > 0 && Urll.contains("%252B")) {
                        int endIndex = url.lastIndexOf("Signature");
                        if (endIndex != -1) {
                            url = url.substring(0, endIndex);
                            url = url + Urll;
                        } }
                    url = url.replace("?AWSAccessKeyId=", "?AWSAccessKeyId%3D")
                            .replace("&Expires=", "%26Expires%3D").replace("&Signature=", "%26Signature%3D");
                }
                 Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/viewer?url="+url));

                _context.startActivity(browserIntent);
                }
            else
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                _context.startActivity(browserIntent);

            }

            return true;
        }
        }




}
