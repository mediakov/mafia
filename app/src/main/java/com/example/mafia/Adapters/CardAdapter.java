package com.example.mafia.Adapters;
/**
 * Adapter for playerlist with CardViews retrieving data from Game object
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.mafia.Model.Game;
import com.example.mafia.Model.Player;
import com.example.mafia.UI.PlayerRegistrationCard;

public class CardAdapter extends BaseAdapter {
    private Context mContext;
    private SparseArray<PlayerRegistrationCard> playerCardList;

    public CardAdapter(Context c, SparseArray playerCardList) {
        mContext = c;
        this.playerCardList = playerCardList;
        Log.d("MyLOG", playerCardList.toString());
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
        Log.d("MyLOG", "getView запрошен ");
        PlayerRegistrationCard playerRegistrationCard;
        if (convertView == null) {
            // if it's not recycled, build a Card
          playerRegistrationCard = playerCardList.get(playerCardList.keyAt(position));

        } else {
            playerRegistrationCard = (PlayerRegistrationCard) convertView;
            playerRegistrationCard = playerCardList.get(playerCardList.keyAt(position));
//            Player player = playerCardList.get(playerCardList.keyAt(position)).player;
//            playerRegistrationCard.recycleCardNewPlayer(player);
            Log.d("MyLOG", "ConvertView is used");
//            playerRegistrationCard = playerRegistrationCard.recycleCardNewPlayer(playerCardList.get(playerCardList.keyAt(position)).player);
        }
        return playerRegistrationCard;
    }
}
