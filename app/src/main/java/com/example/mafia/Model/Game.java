package com.example.mafia.Model;

import android.util.SparseArray;

/**
 * Main class of hierarchy. Object stores all game information: playerlist, roundlist, etc.
 * Class is Singleton
 */
public class Game {
    private static Game instance;

    public static SparseArray<Player> playerList = new SparseArray<>();

    private Game() {}
    public static Game getInstance(){

        if (null == instance){
            instance = new Game();
        }
        return instance;

    }
    public Player addPlayer(Integer number, String name) {

        if (checkNumberIsAvailable(number) & number<=10) {
            Player player = new Player(name,number);
            playerList.put(number, player);
            return player;
            }
        return null;
    }
    public void deletePlayer (Player player) {

        playerList.delete(player.getNumber());
    }

    public Integer getPlayerCount() {
        return playerList.size();
    }

    public Player getPlayerByPosition(Integer index) {
        return playerList.valueAt(index);
    }

    public Integer getPlayerNumberByPosition(Integer position) {
        return getPlayerByPosition(position).getNumber();


    }

    public Integer getAvailableNumber() {
        for (int i = 1; i <= 10; i++) {
            if (playerList.indexOfKey(i) < 0) {
                return i;
            }
        }
        return 11;
    }

    public boolean checkNumberIsAvailable (Integer number){

        if (playerList.indexOfKey(number) < 0 ) {return true;} else {return false;}

    }
}
