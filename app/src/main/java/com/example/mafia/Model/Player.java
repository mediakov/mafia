package com.example.mafia.Model;

/**
 * Player class. Nothing to say here.
 */
public class Player {

    private String name;
    private Integer number;

    public Player(String name, Integer number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return this.name;
    }

    public Integer getNumber() {
        return this.number;
    }
    public void changeNumber(Integer newNumber) {

        this.number = newNumber;
    }
    public void changeName(String newName) {

        this.name = newName;
    }
}
