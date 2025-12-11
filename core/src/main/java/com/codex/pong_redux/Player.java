package com.codex.pong_redux;

public class Player {
    public final String id;
    public String name;
    public Player(String id) {
        this.id = id;
        this.name = "NONAME";
    }

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
