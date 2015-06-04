package com.example.mafia;

import android.support.design.widget.FloatingActionButton;
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

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> playerArray = new ArrayList();
    public int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GridView gridView = (GridView) findViewById(R.id.mainCardGrid);
        final CardAdapter cardAdapter = new CardAdapter(this, playerArray);
        gridView.setAdapter(cardAdapter);

        final MaterialDialog.ButtonCallback inputCallback = new MaterialDialog.ButtonCallback() {
            public void onPositive(MaterialDialog dialog) {
                LinearLayout playerData = new LinearLayout(dialog.getContext());
                EditText playerName = new EditText(dialog.getContext());
                playerData = (LinearLayout) dialog.getCustomView();
                playerName = (EditText) playerData.getChildAt(1);
                playerArray.add(playerName.getText().toString());
                cardAdapter.notifyDataSetChanged();
                gridView.smoothScrollToPosition(i);
            }

            public void onNegative(MaterialDialog dialog) {
                dialog.dismiss();
            }
        };
        final View.OnClickListener regFabListener = new View.OnClickListener() {
            public void onClick(final View v) {
                Log.d("MY_LOG", "Начата генерация диалога");
                final MaterialDialog.Builder builder = new MaterialDialog.Builder(v.getContext());
                builder.title(R.string.registration_player_dialog_title);
                builder.content(R.string.registration_player_dialog_content);
                Log.d("MY_LOG", "Установлены title и content");
                LinearLayout playerRegFieldsHolder = new LinearLayout(v.getContext());
/*                EditText playerNumber = new EditText(v.getContext());
                playerNumber.setInputType(2);
                EditText playerName = new EditText(v.getContext());
                playerRegFieldsHolder.addView(playerNumber);
                playerRegFieldsHolder.addView(playerName);*/
                builder.customView(R.layout.player_registration_dialog, true);
                Log.d("MY_LOG", "Добавлен layout c формой");
                builder.positiveText(android.R.string.ok);
                builder.negativeText(android.R.string.cancel);
                Log.d("MY_LOG", "Установлены кнопки");
                builder.callback(inputCallback);
                Log.d("MY_LOG", "Установлен callback");
                MaterialDialog playerRegistrationDialog = builder.build();
                Log.d("MY_LOG", "Построен диалог");
                playerRegistrationDialog.show();

            }
        };
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
