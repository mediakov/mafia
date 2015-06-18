package com.example.mafia;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.example.mafia.Adapters.CardAdapter;
import com.example.mafia.Model.Game;
import com.example.mafia.UI.PlayerRegistrationCard;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Game game = Game.getInstance();
//        Готовим GridView к работе
        final GridView playerGrid = (GridView) findViewById(R.id.mainCardGrid);
        final CardAdapter cardAdapter = new CardAdapter(this, PlayerRegistrationCard.cardList );
        playerGrid.setAdapter(cardAdapter);
//        GridView готов


//        Обрабатываем клик на FAB и генерируем диалоговое окно регистрации игрока
        View.OnClickListener regFabListener = new View.OnClickListener() {
            public void onClick(final View v) {
                if (game.getAvailableNumber() <= 10) {
                    PlayerRegistrationCard.showAddDialog(v.getContext(), cardAdapter, playerGrid);

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
