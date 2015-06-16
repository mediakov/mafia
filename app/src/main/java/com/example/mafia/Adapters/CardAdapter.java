package com.example.mafia.Adapters;
/**
 * Adapter for playerlist with CardViews retrieving data from Game object
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.mafia.Model.Game;

public class CardAdapter extends BaseAdapter {
    private Context mContext;
    private Game game;
    private PlayerCardBuilder builder;

    public CardAdapter(Context c, PlayerCardBuilder builder) {
        mContext = c;
        this.builder = builder;
        this.game = Game.getInstance();

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
            // if it's not recycled, build a Card
            cardView = builder.buildPlayerCard(mContext);

        } else {
            cardView = (CardView) convertView;
        }
        cardView = builder.setPlayerInCard(cardView, position);
        return cardView;
    }
}
