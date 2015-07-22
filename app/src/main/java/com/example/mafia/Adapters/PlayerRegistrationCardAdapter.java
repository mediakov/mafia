package com.example.mafia.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mafia.Model.Game;
import com.example.mafia.Model.Player;
import com.example.mafia.R;
import com.example.mafia.UI.PlayerRegistrationCard;

public class PlayerRegistrationCardAdapter extends RecyclerView.Adapter<PlayerRegistrationCardAdapter.PlayerRegistrationCardViewHolder> {

    public static class PlayerRegistrationCardViewHolder extends RecyclerView.ViewHolder {
        PlayerRegistrationCard card;
        TextView playerNumber;
        TextView playerName;
        Button editButton;
        Button deleteButton;

        public PlayerRegistrationCardViewHolder(View itemView) {
            super(itemView);
            this.card = (PlayerRegistrationCard) itemView.findViewById(R.id.Card);
            this.playerName = (TextView) itemView.findViewById(R.id.PlayerName);
            this.playerNumber = (TextView) itemView.findViewById(R.id.PlayerNumber);
            this.editButton = (Button) itemView.findViewById(R.id.editbutton);
            this.deleteButton = (Button) itemView.findViewById(R.id.deletebutton);
        }
    }

    SparseArray<Player> cardList;

    public PlayerRegistrationCardAdapter(SparseArray<Player> cardList) {
        this.cardList = cardList;
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    @Override
    public PlayerRegistrationCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_registration_card_template, parent, false);
        return new PlayerRegistrationCardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PlayerRegistrationCardViewHolder holder, int position) {

        holder.playerName.setText(cardList.get(cardList.keyAt(position)).getName());
        holder.playerNumber.setText(cardList.get(cardList.keyAt(position)).getNumber().toString());
        holder.card.player = cardList.get(cardList.keyAt(position));
        holder.card.setId(holder.card.player.getNumber());
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlayerRegistrationCard.editDialog == null) {
                    PlayerRegistrationCard.generateDialog(v.getContext());
                }
                PlayerRegistrationCard.dialogRequestedCard=holder.card;
                PlayerRegistrationCard.editDialog.show();
                TextView playerName = (TextView) PlayerRegistrationCard.editDialog.getCustomView().findViewById(R.id.PlayerName);
                TextView playerNumber = (TextView) PlayerRegistrationCard.editDialog.getCustomView().findViewById(R.id.PlayerNumber);
                playerName.setText(holder.card.player.getName());
                playerNumber.setText(holder.card.player.getNumber().toString());
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game game = Game.getInstance();
                game.deletePlayer(holder.card.player);
                RecyclerView rv = (RecyclerView) v.getRootView().findViewById(R.id.registrationCardGrid);
                rv.getAdapter().notifyDataSetChanged();

            }
        });

    }


}
