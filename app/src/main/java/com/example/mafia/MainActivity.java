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
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Hashtable<Integer, String> playerArray = new Hashtable<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GridView gridView = (GridView) findViewById(R.id.mainCardGrid);
        final CardAdapter cardAdapter = new CardAdapter(this);
        gridView.setAdapter(cardAdapter);

        final MaterialDialog.ButtonCallback inputCallback = new MaterialDialog.ButtonCallback() {
            public void onPositive(MaterialDialog dialog) {
                EditText playerName = new EditText(dialog.getContext());
                EditText playerNumber = new EditText(dialog.getContext());
                playerName = (EditText) dialog.getCustomView().findViewById(R.id.PlayerName);
                playerNumber = (EditText) dialog.getCustomView().findViewById(R.id.PlayerNumber);
                Integer playerNumberInt = Integer.parseInt(playerNumber.getText().toString());
                Game.addPlayer(playerNumberInt, playerName.getText().toString());
                cardAdapter.notifyDataSetChanged();
                gridView.smoothScrollToPosition(playerNumberInt);
            }

            public void onNegative(MaterialDialog dialog) {
                dialog.dismiss();
            }
        };
        final View.OnClickListener regFabListener = new View.OnClickListener() {
            public void onClick(final View v) {
                final MaterialDialog.Builder builder = new MaterialDialog.Builder(v.getContext());
                TextView playerNumber = new TextView(builder.getContext());
                builder.title(R.string.registration_player_dialog_title);
                builder.customView(R.layout.player_registration_dialog, true);
                builder.positiveText(android.R.string.ok);
                builder.negativeText(android.R.string.cancel);
                builder.callback(inputCallback);
                MaterialDialog playerRegistrationDialog = builder.build();
                playerNumber = (TextView) playerRegistrationDialog.getCustomView().findViewById(R.id.PlayerNumber);
                playerNumber.setText("1");
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
