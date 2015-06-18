package com.example.mafia.Adapters;
/**
 * Adapter for playerlist with CardViews retrieving data from Game object
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.mafia.Model.Game;
import com.example.mafia.UI.PlayerRegistrationCard;

public class CardAdapter extends BaseAdapter {
    private Context mContext;
    private SparseArray playerCardList;

    public CardAdapter(Context c, SparseArray playerCardList) {
        mContext = c;
        this.playerCardList = playerCardList;
    }

    public int getCount() {
        return playerCardList.size();
    }

    public Object getItem(int position) {
        return playerCardList.valueAt(position);
        }

    public long getItemId(int position) {

        return playerCardList.keyAt(position);
    }

    // create a new CardView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        PlayerRegistrationCard playerRegistrationCard;
        if (convertView == null) {
            // if it's not recycled, build a Card
          playerRegistrationCard = (PlayerRegistrationCard) playerCardList.get(position);

        } else {
            playerRegistrationCard = (PlayerRegistrationCard) convertView;
//            playerRegistrationCard = (PlayerRegistrationCard) playerCardList.get(position);
        }
        return playerRegistrationCard;
    }
}
