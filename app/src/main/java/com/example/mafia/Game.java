package com.example.mafia;

import android.util.SparseArray;

/**
 * Created by Юрий on 10.06.2015.
 */
public class Game {
    private static SparseArray<Player> playerList = new SparseArray<>();

    public static boolean addPlayer(Integer number, String name) {

        if (playerList.indexOfKey(number) < 0) {
            playerList.put(number, new Player(name, number));
            return true;
        } else {
            return false;
        }
    }

    public static Integer getPlayerCount() {
        return playerList.size();
    }

    public static Player getPlayerByPosition(Integer index) {
        return playerList.valueAt(index);
    }
    public static long getPlayerNumberByPosition(Integer position){
        return getPlayerByPosition(position).getNumber();

    }
}
