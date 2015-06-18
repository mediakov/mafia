package com.example.mafia.UI;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mafia.Adapters.CardAdapter;
import com.example.mafia.Model.Game;
import com.example.mafia.Model.Player;
import com.example.mafia.R;

public class PlayerRegistrationCard extends CardView {

    public static SparseArray<PlayerRegistrationCard> cardList = new SparseArray<PlayerRegistrationCard>();
    public Player player;

    private final static Game game = Game.getInstance();

    //   базовые методы
    public PlayerRegistrationCard(Context mContext) {
        super (mContext);
    }

    public PlayerRegistrationCard(Context mContext, AttributeSet attrs) {
        super(mContext,attrs);

    }

    public PlayerRegistrationCard(Context mContext, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(mContext,attrs,defStyleAttr );
    }



    public static PlayerRegistrationCard addPlayerRegistrationCard (Context mContext, Player player) {
//      создаем объект, присваиваем игрока, парсим layout
        PlayerRegistrationCard playerRegistrationCard = new PlayerRegistrationCard(mContext);
        playerRegistrationCard.player = player;
        LayoutInflater ltInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        playerRegistrationCard = (PlayerRegistrationCard) ltInflater.inflate(R.layout.player_registration_card_template, null, false);
//      заполняем поля внутренних View
        TextView playerNameTv = (TextView) playerRegistrationCard.findViewById(R.id.PlayerName);
        TextView playerNumberTv = (TextView) playerRegistrationCard.findViewById(R.id.PlayerNumber);
        playerNameTv.setText(player.getName());
        playerNumberTv.setText(player.getNumber().toString());
        playerRegistrationCard.setId((int) player.getNumber());

//      назначаем слушателей кнопкам
        Button.OnClickListener editButtonListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlayerRegistrationCard card = (PlayerRegistrationCard) v.getParent().getParent().getParent();
                card.showEditDialog();
            }
        };
        Button cardEditButton = (Button) playerRegistrationCard.findViewById(R.id.editbutton);
        cardEditButton.setOnClickListener(editButtonListener);
        cardList.put(player.getNumber(), playerRegistrationCard);
        return playerRegistrationCard;
    }

    public void deleteCard() {

        cardList.delete(this.player.getNumber());
        game.deletePlayer(this.player);

    }

    public boolean changePlayerNumber(Integer newPlayerNumber) {


        if (game.checkNumberIsAvailable(newPlayerNumber)) {

            cardList.delete(this.player.getNumber());
            this.player.changeNumber(newPlayerNumber);
            TextView playerNumberTv = (TextView) this.findViewById(R.id.PlayerNumber);
            playerNumberTv.setText(this.player.getNumber().toString());
            this.setId((int) this.player.getNumber());
            cardList.put(this.player.getNumber(), this);
            return true;

        }
        return false;
    }

    public boolean changePlayerName(String newPlayerName) {
        this.player.changeName(newPlayerName);
        TextView playerNameTv = (TextView) this.findViewById(R.id.PlayerName);
        playerNameTv.setText(this.player.getName());
        cardList.put(this.player.getNumber(), this);
        return true;
    }

    public void showEditDialog() {

        MaterialDialog.ButtonCallback editDialogInputCallback = new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onNegative(MaterialDialog dialog) {
                dialog.dismiss();
            }
        };
        MaterialDialog.Builder playerEditDialogBuilder = new MaterialDialog.Builder(getContext());
        playerEditDialogBuilder.title(R.string.edit_player_dialog_title);
        playerEditDialogBuilder.customView(R.layout.player_registration_dialog, true);
        playerEditDialogBuilder.positiveText("Сохранить");
        playerEditDialogBuilder.negativeText("Закрыть");
        playerEditDialogBuilder.callback(editDialogInputCallback);
        MaterialDialog playerEditDialog = playerEditDialogBuilder.build();
        PlayerRegistrationCard card = this;
        TextView playerName = (TextView) playerEditDialog.getCustomView().findViewById(R.id.PlayerName);
        TextView playerNumber = (TextView) playerEditDialog.getCustomView().findViewById(R.id.PlayerNumber);
        playerName.setText(card.player.getName());
        playerNumber.setText(card.player.getNumber().toString());
        playerEditDialog.show();
    }

    public static void showAddDialog(Context mContext, final CardAdapter cardAdapter, final GridView playerGrid) {
        MaterialDialog.ButtonCallback PlayerRegistrationCallback = new MaterialDialog.ButtonCallback() {
            public void onPositive(MaterialDialog dialog) {
                EditText playerName = (EditText) dialog.getCustomView().findViewById(R.id.PlayerName);
                EditText playerNumber = (EditText) dialog.getCustomView().findViewById(R.id.PlayerNumber);
                Integer playerNumberInt = Integer.parseInt(playerNumber.getText().toString());
                Player player = game.addPlayer(playerNumberInt, playerName.getText().toString());
                PlayerRegistrationCard.addPlayerRegistrationCard(dialog.getContext(), player);
                cardAdapter.notifyDataSetChanged();
                playerGrid.smoothScrollToPosition(playerNumberInt);
            }

            public void onNegative(MaterialDialog dialog) {
                dialog.dismiss();
            }

        };

        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext);
        builder.title(R.string.registration_player_dialog_title);
        builder.customView(R.layout.player_registration_dialog, true);
        builder.positiveText(android.R.string.ok);
        builder.negativeText(android.R.string.cancel);
        builder.callback(PlayerRegistrationCallback);
        MaterialDialog playerRegistrationDialog = builder.build();
        TextView playerNumber = (TextView) playerRegistrationDialog.getCustomView().findViewById(R.id.PlayerNumber);
        playerNumber.setText(String.valueOf(game.getAvailableNumber()));
        playerRegistrationDialog.show();
    }

}