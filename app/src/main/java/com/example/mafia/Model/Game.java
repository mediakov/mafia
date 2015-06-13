package com.example.mafia.Model;

import android.util.SparseArray;

/**
 * Main class of hierarchy. Object stores all game information: playerlist, roundlist, etc.
 * Class is Singleton
 */
public class Game {
    private SparseArray<Player> playerList = new SparseArray<>();
    private static Game instance;

    private Game() {}
    public static Game getInstance(){

        if (null == instance){
            instance = new Game();
        }
        return instance;

    }
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

    public long getPlayerNumberByPosition(Integer position) {
        return getPlayerByPosition(position).getNumber();

    }

    public int getAvailableNumber() {
        for (int i = 1; i <= 10; i++) {
            if (playerList.indexOfKey(i) < 0) {
                return i;
            }
        }
        return 11;
    }
}
