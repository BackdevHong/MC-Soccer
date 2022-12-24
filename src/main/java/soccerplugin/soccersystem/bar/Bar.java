package soccerplugin.soccersystem.bar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import soccerplugin.soccersystem.SoccerSystem;

public class Bar {
    private BossBar bar;
    private int redScore = 0;
    private int blueScore = 0;
    public void addPlayer(Player player) {
        bar.addPlayer(player);
    }

    public BossBar getBar() {
        return bar;
    }

    public String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public void createBar() {
        bar = Bukkit.createBossBar(format("&c&l" + this.redScore + " &f&l: " + "&b&l" + this.blueScore), BarColor.PINK, BarStyle.SOLID);
        bar.setVisible(true);
        bar.removeAll();
        bar.setProgress(1.0);
    }

    public void removeBar() {
        bar.setVisible(false);
        this.redScore = 0;
        this.blueScore = 0;
    }

    public void addScore(String value, String Type) {
        if (value.equalsIgnoreCase("RED")) {
            if (Type.equalsIgnoreCase("Plus")) {
                this.redScore++;
            } else {
                this.redScore--;
            }
        } else {
            if (Type.equalsIgnoreCase("Plus")) {
                this.blueScore++;
            } else {
                this.blueScore--;
            }
        }
        bar.setTitle(format("&c&l" + this.redScore + " &f&l: " + "&b&l" + this.blueScore));
    }
}
