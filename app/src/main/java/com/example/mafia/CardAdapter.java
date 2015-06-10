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
import java.util.Hashtable;
import java.util.List;

public class CardAdapter extends BaseAdapter {
    private Context mContext;
    final String LOG_TAG = "myLogs";

    public CardAdapter(Context c ) {
        mContext = c;
//        mData = inboundList;
//        mKeys = mData.keySet().toArray(new Integer[mData.size()]);
    }

    public int getCount() {
        return Game.getPlayerCount();
    }

    public Object getItem(int position) {
        return Game.getPlayerByPosition(position);
    }

    public long getItemId(int position) {
        return Game.getPlayerNumberByPosition(position);
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
        TextView playerNameTv = (TextView) cardView.findViewById(R.id.PlayerName);
        TextView playerNumberTv = (TextView) cardView.findViewById(R.id.PlayerNumber);
        cardView.setId((int) Game.getPlayerNumberByPosition(position));
        playerNameTv.setText(Game.getPlayerByPosition(position).getName());
        playerNumberTv.setText(Game.getPlayerByPosition(position).getNumber().toString());
        Log.d(LOG_TAG, "ID of view: " + (cardView.getId()));
        return cardView;
    }
}
