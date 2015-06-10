package com.example.mafia;

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

}
