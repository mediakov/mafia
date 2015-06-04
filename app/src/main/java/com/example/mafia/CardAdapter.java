package com.example.mafia;
/**
 * AdapterForMainGridView
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends BaseAdapter {
    private Context mContext;
    final String LOG_TAG = "myLogs";
    public List<String> list = new ArrayList();

    public CardAdapter(Context c, List inboundList) {
        mContext = c;
        list = inboundList;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new CardView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        CardView cardView = new CardView(mContext);
        if (convertView == null) {
            // if it's not recycled, inflate the Card
            LayoutInflater ltInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cardView = (CardView) ltInflater.inflate(R.layout.cardview_template, cardView, false);

        } else {
            cardView = (CardView) convertView;
        }
        cardView.setId(position + 1);
        TextView tv = (TextView) cardView.getChildAt(0);
        tv.setText(list.get(position));
        Log.d(LOG_TAG, "ID of view: " + (cardView.getId()));
        return cardView;
    }
}
