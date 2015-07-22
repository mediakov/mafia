package com.example.mafia.UI;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mafia.Adapters.PlayerRegistrationCardAdapter;
import com.example.mafia.Model.Game;
import com.example.mafia.Model.Player;
import com.example.mafia.R;


public class PlayerRegistrationCard extends CardView {

    public static SparseArray<PlayerRegistrationCard> cardList = new SparseArray<PlayerRegistrationCard>();
    public Player player;
    private final static Game game = Game.getInstance();
    public static MaterialDialog editDialog;
    public static PlayerRegistrationCard dialogRequestedCard;

    //   конструкторы


    public PlayerRegistrationCard(Context context) {
        super(context);
    }

    public PlayerRegistrationCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerRegistrationCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static void generateDialog(Context context) {

        final MaterialDialog.ButtonCallback editDialogInputCallback = new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                PlayerRegistrationCard playerRegistrationCard = PlayerRegistrationCard.dialogRequestedCard;
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


        MaterialDialog.Builder playerEditDialogBuilder = new MaterialDialog.Builder(context);
        playerEditDialogBuilder.title(R.string.edit_player_dialog_title);
        playerEditDialogBuilder.customView(R.layout.player_registration_dialog, true);
        playerEditDialogBuilder.positiveText("Сохранить");
        playerEditDialogBuilder.negativeText("Закрыть");
        playerEditDialogBuilder.callback(editDialogInputCallback);
        playerEditDialogBuilder.autoDismiss(false);
        editDialog = playerEditDialogBuilder.build();
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

    public static void showAddDialog(Context mContext, final PlayerRegistrationCardAdapter cardAdapter, final RecyclerView playerGrid) {
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
//                PlayerRegistrationCard.addPlayerRegistrationCard(dialog.getContext(), player);
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

