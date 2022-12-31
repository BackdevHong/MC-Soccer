package soccerplugin.soccersystem.bar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreSetting {
    private ScoreboardManager manager;
    private Scoreboard scoreboard;

    private Objective objective;
    private Score score;
    private Score getPScore;
    public ScoreSetting() {
        this.manager = Bukkit.getScoreboardManager();
        this.scoreboard = manager.getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("YellowCard", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(format("&e옐로우카드"));
    }

    public void addPLayers(Player player) {
        player.setScoreboard(scoreboard);
    }

    public void setScore(Player player) {
        score = objective.getScore(player);
        score.setScore(1);
    }

    public void redCard(Player player) {
        score = objective.getScore(player);
        scoreboard.resetScores(player);
        player.setGameMode(GameMode.SPECTATOR);
    }
    public int getScore(Player player) {
        getPScore = objective.getScore(player);
        return getPScore.getScore();
    }
    public String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public void removeScore() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.setScoreboard(manager.getNewScoreboard());
        }
    }

}
