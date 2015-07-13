package com.example.mafia.UI;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mafia.Adapters.CardAdapter;
import com.example.mafia.Model.Game;
import com.example.mafia.Model.Player;
import com.example.mafia.R;

public class PlayerRegistrationCard extends CardView {

    public static SparseArray<PlayerRegistrationCard> cardList = new SparseArray<PlayerRegistrationCard>();
    public Player player;
    private final static Game game = Game.getInstance();

    //   конструкторы
    public PlayerRegistrationCard(Context mContext) {
        super(mContext);
    }

    public PlayerRegistrationCard(Context mContext, AttributeSet attrs) {
        super(mContext, attrs);

    }

    public PlayerRegistrationCard(Context mContext, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(mContext, attrs, defStyleAttr);
    }

    public static PlayerRegistrationCard addPlayerRegistrationCard(Context mContext, Player player) {

//      создаем объект, парсим layout, присваиваем игрока (порядок важен)
        final PlayerRegistrationCard playerRegistrationCard;
        LayoutInflater ltInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        playerRegistrationCard = (PlayerRegistrationCard) ltInflater.inflate(R.layout.player_registration_card_template, null, false);
        playerRegistrationCard.player = player;
//      заполняем поля внутренних View
        TextView playerNameTv = (TextView) playerRegistrationCard.findViewById(R.id.PlayerName);
        TextView playerNumberTv = (TextView) playerRegistrationCard.findViewById(R.id.PlayerNumber);
        playerNameTv.setText(playerRegistrationCard.player.getName());
        playerNumberTv.setText(playerRegistrationCard.player.getNumber().toString());
        playerRegistrationCard.setId((int) playerRegistrationCard.player.getNumber());
//      назначаем слушателей кнопкам
        Button.OnClickListener editButtonListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerRegistrationCard.showEditDialog();
            }
        };
        Button.OnClickListener deleteButtonListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                playerRegistrationCard.deleteCard();
            }
        };
        Button cardEditButton = (Button) playerRegistrationCard.findViewById(R.id.editbutton);
        cardEditButton.setOnClickListener(editButtonListener);
        Button cardDeleteButton = (Button) playerRegistrationCard.findViewById(R.id.deletebutton);
        cardDeleteButton.setOnClickListener(deleteButtonListener);
//        добавляем карточку в список карточек
        cardList.put(playerRegistrationCard.player.getNumber(), playerRegistrationCard);
        return playerRegistrationCard;
    }

    public PlayerRegistrationCard recycleCardNewPlayer(Player player) {
        this.player = player;
        TextView playerNameTv = (TextView) this.findViewById(R.id.PlayerName);
        TextView playerNumberTv = (TextView) this.findViewById(R.id.PlayerNumber);
        playerNameTv.setText(this.player.getName());
        playerNumberTv.setText(this.player.getNumber().toString());
        this.setId(this.player.getNumber());
        return this;
    }

    public void deleteCard() {
        cardList.delete(this.player.getNumber());
        game.deletePlayer(this.player);
        GridView gridView = (GridView) this.getParent();
        CardAdapter adapter = (CardAdapter) gridView.getAdapter();
        adapter.notifyDataSetChanged();
    }

    public boolean changePlayerNumber(Integer newPlayerNumber) {
        if (this.player.getNumber().intValue() == newPlayerNumber.intValue()) {
            return true;
        }
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
        if (this.player.getName() == newPlayerName) {
            return true;
        }
        this.player.changeName(newPlayerName);
        TextView playerNameTv = (TextView) this.findViewById(R.id.PlayerName);
        playerNameTv.setText(this.player.getName());
        cardList.put(this.player.getNumber(), this);
        return true;
    }

//    Диалоги

    public void showEditDialog() {

        final PlayerRegistrationCard playerRegistrationCard = this;
        final MaterialDialog.ButtonCallback editDialogInputCallback = new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                EditText playerNameTv = (EditText) dialog.getCustomView().findViewById(R.id.PlayerName);
                EditText playerNumberTv = (EditText) dialog.getCustomView().findViewById(R.id.PlayerNumber);
                String newPlayerName = playerNameTv.getText().toString();
                Integer newPlayerNumber = Integer.parseInt(playerNumberTv.getText().toString());
                playerRegistrationCard.changePlayerName(newPlayerName);

                if (playerRegistrationCard.changePlayerNumber(newPlayerNumber)) {
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    Snackbar.make(playerRegistrationCard.getRootView().findViewById(R.id.parentLayout),R.string.number_already_exist_alert, Snackbar.LENGTH_SHORT).show();

                }
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
        playerEditDialogBuilder.autoDismiss(false);
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
                if (player == null) {
                    Snackbar.make(playerGrid, R.string.number_already_exist_alert, Snackbar.LENGTH_SHORT).show();
                    return;
                }
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
        try {
            TextView playerNumber = (TextView) playerRegistrationDialog.getCustomView().findViewById(R.id.PlayerNumber);
            playerNumber.setText(String.valueOf(game.getAvailableNumber()));
        } catch (NullPointerException exception) {
            Log.d("My_log", exception.toString());
        }
        ;

        playerRegistrationDialog.show();
    }

}