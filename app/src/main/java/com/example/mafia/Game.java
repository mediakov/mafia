package com.example.mafia;

import android.util.SparseArray;

/**
 * Created by Юрий on 10.06.2015.
 */
public class Game {
    private SparseArray<Player> playerList = new SparseArray<>();

    public boolean addPlayer(Integer number, String name) {

        if (playerList.indexOfKey(number) < 0) {
            playerList.put(number, new Player(name, number));
            return true;
        } else {
            return false;
        }
    }

    public Integer getPlayerCount() {
        return playerList.size();
    }

    public Player getPlayerByPosition(Integer index) {
        return playerList.valueAt(index);
    }
    public long getPlayerNumberByPosition(Integer position){
        return getPlayerByPosition(position).getNumber();

    }
    public int getAvailableNumber() {
        for (int i=1;i<=10;i++){
            if (playerList.indexOfKey(i)<0) {
                return i;
            }
        }
                return 11;
    }
}
