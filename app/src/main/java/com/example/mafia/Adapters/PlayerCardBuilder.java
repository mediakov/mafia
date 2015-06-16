package com.example.mafia.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import com.example.mafia.Model.Game;
import com.example.mafia.R;

public class PlayerCardBuilder {

    public CardView buildPlayerCard(Context mContext) {
//        Парсим ресурс и получем View для карточки
        LayoutInflater ltInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CardView cardView = (CardView) ltInflater.inflate(R.layout.cardview_template, null, false);
        return cardView;
//        Назначаем слушателей кнопкам
//        Button cardEditButton = (Button) cardView.findViewById(R.id.editbutton);
//        Button cardDeleteButton = (Button) cardView.findViewById(R.id.deletebutton);
//        cardEditButton.setOnClickListener();
//        cardDeleteButton.setOnClickListener();

    }

    public CardView setPlayerInCard(CardView cardView, int position) {

        Game game = Game.getInstance();
        TextView playerNameTv = (TextView) cardView.findViewById(R.id.PlayerName);
        TextView playerNumberTv = (TextView) cardView.findViewById(R.id.PlayerNumber);
        cardView.setId((int) game.getPlayerNumberByPosition(position));
        playerNameTv.setText(game.getPlayerByPosition(position).getName());
        playerNumberTv.setText(game.getPlayerByPosition(position).getNumber().toString());
        return cardView;
    }


}
