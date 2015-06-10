package com.example.mafia;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Game game = new Game();
//        Готовим GridView к работе
        final GridView gridView = (GridView) findViewById(R.id.mainCardGrid);
        final CardAdapter cardAdapter = new CardAdapter(this, game);
        gridView.setAdapter(cardAdapter);
//        GridView готов

//        Обрабатываем диалог регистрации игроков
        final MaterialDialog.ButtonCallback inputCallback = new MaterialDialog.ButtonCallback() {
            public void onPositive(MaterialDialog dialog) {
                EditText playerName = (EditText) dialog.getCustomView().findViewById(R.id.PlayerName);
                EditText playerNumber = (EditText) dialog.getCustomView().findViewById(R.id.PlayerNumber);
                Integer playerNumberInt = Integer.parseInt(playerNumber.getText().toString());
                game.addPlayer(playerNumberInt, playerName.getText().toString());
                cardAdapter.notifyDataSetChanged();
                gridView.smoothScrollToPosition(playerNumberInt);
            }
            public void onNegative(MaterialDialog dialog) {
                dialog.dismiss();
            }
        };

//        Обрабатываем клик на FAB и генерируем диалоговое окно регистрации игрока
//        Вынести генерацию диалога отдельно
        final View.OnClickListener regFabListener = new View.OnClickListener() {
            public void onClick(final View v) {
                if (game.getAvailableNumber() <= 10) {
                    final MaterialDialog.Builder builder = new MaterialDialog.Builder(v.getContext());
                    builder.title(R.string.registration_player_dialog_title);
                    builder.customView(R.layout.player_registration_dialog, true);
                    builder.positiveText(android.R.string.ok);
                    builder.negativeText(android.R.string.cancel);
                    builder.callback(inputCallback);
                    MaterialDialog playerRegistrationDialog = builder.build();
                    TextView playerNumber = (TextView) playerRegistrationDialog.getCustomView().findViewById(R.id.PlayerNumber);
                    playerNumber.setText(String.valueOf(game.getAvailableNumber()));
                    playerRegistrationDialog.show();
                }
                else {
                    Snackbar.make(findViewById(R.id.mainCardGrid),R.string.no_available_place_in_game_alert,Snackbar.LENGTH_SHORT).show();
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
