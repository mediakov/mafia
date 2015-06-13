package com.example.mafia;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mafia.Adapters.CardAdapter;
import com.example.mafia.Model.Game;

class PlayerRegistrationDialogBuilder {

    public MaterialDialog build(MaterialDialog.ButtonCallback inputCallback, Context v) {
        Game game = Game.getInstance();
        final MaterialDialog.Builder builder = new MaterialDialog.Builder(v);
        builder.title(R.string.registration_player_dialog_title);
        builder.customView(R.layout.player_registration_dialog, true);
        builder.positiveText(android.R.string.ok);
        builder.negativeText(android.R.string.cancel);
        builder.callback(inputCallback);
        MaterialDialog playerRegistrationDialog = builder.build();
        TextView playerNumber = (TextView) playerRegistrationDialog.getCustomView().findViewById(R.id.PlayerNumber);
        playerNumber.setText(String.valueOf(game.getAvailableNumber()));
        return playerRegistrationDialog;
    }

}


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Game game = Game.getInstance();
//        Готовим GridView к работе
        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.parentLayout);
        final GridView playerGrid = (GridView) findViewById(R.id.mainCardGrid);
        final CardAdapter cardAdapter = new CardAdapter(this, game);
        playerGrid.setAdapter(cardAdapter);
//        GridView готов

//        Обрабатываем диалог регистрации игроков
        final MaterialDialog.ButtonCallback inputCallback = new MaterialDialog.ButtonCallback() {
            public void onPositive(MaterialDialog dialog) {
                EditText playerName = (EditText) dialog.getCustomView().findViewById(R.id.PlayerName);
                EditText playerNumber = (EditText) dialog.getCustomView().findViewById(R.id.PlayerNumber);
                Integer playerNumberInt = Integer.parseInt(playerNumber.getText().toString());
                game.addPlayer(playerNumberInt, playerName.getText().toString());
                cardAdapter.notifyDataSetChanged();
                playerGrid.smoothScrollToPosition(playerNumberInt);
            }

            public void onNegative(MaterialDialog dialog) {
                dialog.dismiss();
            }
        };

//        Обрабатываем клик на FAB и генерируем диалоговое окно регистрации игрока
//        Вынести генерацию диалога отдельно
        View.OnClickListener regFabListener = new View.OnClickListener() {
            public void onClick(final View v) {
                if (game.getAvailableNumber() <= 10) {
                    PlayerRegistrationDialogBuilder builder = new PlayerRegistrationDialogBuilder();
                    builder.build(inputCallback, v.getContext()).show();
                } else {
                    Snackbar.make(findViewById(R.id.parentLayout), R.string.no_available_place_in_game_alert, Snackbar.LENGTH_SHORT).show();
                }
            }
        };
//        Устанавливаем обработчик для FAB
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.FloatingActionButtonID);
        floatingActionButton.setOnClickListener(regFabListener);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
