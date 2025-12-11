package com.codex.pong_redux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.badlogic.gdx.utils.GdxNativesLoader.load;

public class Leaderboard {
    private Map<String, PlayerStats> players = new HashMap<>();
    private Preferences preferences;

    public Leaderboard() {
        preferences = Gdx.app.getPreferences("pong_leaderboard");
        load();
    }

    public PlayerStats getPlayer(String playerId) {
        // if playerId doesn't exist, create it
        return players.computeIfAbsent(playerId, PlayerStats::new);
    }

    public void updateWin(String playerId, int score) {
        getPlayer(playerId).addWin(score);
        save();
    }

    public void updateLoss(String playerId, int score) {
        getPlayer(playerId).addLoss(score);
        save();
    }

    public Map<String, PlayerStats> getAllPlayers() {
        return players;
    }

    public List<PlayerStats> getTopPlayers(int limit) {
        return players.values().stream()
            .sorted((a, b) -> Double.compare(a.getWinLossRatio(), b.getWinLossRatio()))
            .limit(limit)
            .collect(Collectors.toList());
    }

    private void save() {
        for (PlayerStats stats : players.values()) {
            String key = stats.getPlayerId();
            preferences.putInteger(key + "_wins", stats.getWins());
            preferences.putInteger(key + "_losses", stats.getLosses());
            preferences.putInteger(key + "_score", stats.getTotalScore());
        }
        preferences.flush();
    }

    private void  load() {
        for (String key : preferences.get().keySet()) {
            if (key.endsWith("_wins")) {
                String playerId = key.replace("_wins", "");
                PlayerStats stats = new PlayerStats(playerId);
                stats.addWin(preferences.getInteger(playerId + "_wins"));
                stats.addLoss(preferences.getInteger(playerId + "_losses"));

                int score = preferences.getInteger(playerId + "_score");
                stats.setTotalScore(score);
                players.put(playerId, stats);
            }
        }
    }

}
