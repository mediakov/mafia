package com.example.mafia.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mafia.Model.Game;
import com.example.mafia.R;

public class PlayerCardBuilder {

    public CardView buildPlayerCard(Context mContext) {
//        Парсим ресурс и получем View для карточки
        LayoutInflater ltInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final CardView cardView = (CardView) ltInflater.inflate(R.layout.cardview_template, null, false);
        Button.OnClickListener editButtonListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog.Builder builder = new MaterialDialog.Builder(v.getContext());
                builder.title(R.string.edit_player_dialog_title);
                builder.customView(R.layout.player_registration_dialog,true);
                builder.positiveText("Сохранить");
                builder.negativeText("Закрыть");
                builder.callback(new MaterialDialog.ButtonCallback() {
                                     @Override
                                     public void onPositive(MaterialDialog dialog) {
                                         dialog.dismiss();
                                     }

                                     @Override
                                     public void onNegative(MaterialDialog dialog) {
                                         dialog.dismiss();
                                     }
                                 }

                );
                MaterialDialog dialog = builder.build();
//                Устанавливаем значения полей диалога с карточки
                CardView card = (CardView) v.getParent().getParent().getParent();
                TextView oldPlayerNumber = (TextView) card.findViewById(R.id.PlayerNumber);
                TextView oldPlayerName = (TextView) card.findViewById(R.id.PlayerName);
                TextView playerNumber = (TextView) dialog.getCustomView().findViewById(R.id.PlayerNumber);
                TextView playerName = (TextView) dialog.getCustomView().findViewById(R.id.PlayerName);
                playerNumber.setText(oldPlayerNumber.getText());
                playerName.setText(oldPlayerName.getText());
                dialog.show();

            }
        };








//        Назначаем слушателей кнопкам
        Button cardEditButton = (Button) cardView.findViewById(R.id.editbutton);
//        Button cardDeleteButton = (Button) cardView.findViewById(R.id.deletebutton);
        cardEditButton.setOnClickListener(editButtonListener);
//        cardDeleteButton.setOnClickListener();
        return cardView;
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
