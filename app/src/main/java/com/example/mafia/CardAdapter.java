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

public class CardAdapter extends BaseAdapter {
    private Context mContext;
    private Game game;
    final String LOG_TAG = "myLogs";

    public CardAdapter(Context c, Game game) {
        mContext = c;
        this.game = game;

    }

    public int getCount() {
        return game.getPlayerCount();
    }

    public Object getItem(int position) {
        return game.getPlayerByPosition(position);
    }

    public long getItemId(int position) {
        return game.getPlayerNumberByPosition(position);
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
        cardView.setId((int) game.getPlayerNumberByPosition(position));
        playerNameTv.setText(game.getPlayerByPosition(position).getName());
        playerNumberTv.setText(game.getPlayerByPosition(position).getNumber().toString());
        Log.d(LOG_TAG, "ID of view: " + (cardView.getId()));
        return cardView;
    }
}
