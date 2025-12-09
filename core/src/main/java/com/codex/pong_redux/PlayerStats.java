package com.codex.pong_redux;

public class PlayerStats {
    public String playerId;
    public int wins;
    public int losses;
    public int totalScore;

    public PlayerStats(String id) {
        this.playerId = id;
    }

    public void addWin(int score) {
        wins++;
        totalScore += score;
    }

    public void addLoss(int score) {
        losses++;
        totalScore += score;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getTotalScore() {
        return totalScore;
    }
}
