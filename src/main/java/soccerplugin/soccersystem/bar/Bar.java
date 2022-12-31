package soccerplugin.soccersystem.bar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import soccerplugin.soccersystem.SoccerSystem;
import soccerplugin.soccersystem.color.ColorStringEnum;

public class Bar {
    private BossBar bar;
    private String TeamName1;
    private String TeamName2;
    private String TColor1;
    private String TColor2;
    private int Team1Score = 0;
    private int Team2Score = 0;
    public void addPlayer(Player player) {
        bar.addPlayer(player);
    }

    public BossBar getBar() {
        return bar;
    }

    public String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public void createBar(String TName1, String TName2, String TColor1, String TColor2) {
        this.TeamName1 = TName1;
        this.TeamName2 = TName2;
        this.TColor1 = TColor1;
        this.TColor2 = TColor2;
        bar = Bukkit.createBossBar(format( TColor1 + "&l" + TeamName1 + " " + this.Team1Score + " &f&l: " + TColor2 +"&l" + this.Team2Score + " " + TeamName2), BarColor.PINK, BarStyle.SOLID);
        bar.setVisible(true);
        bar.removeAll();
        bar.setProgress(1.0);
    }

    public void removeBar() {
        bar.setVisible(false);
        this.Team1Score = 0;
        this.Team2Score = 0;
    }

    public void addScore(String value, String Type, Player player) {
        if (value.equalsIgnoreCase(TeamName1)) {
            if (Type.equalsIgnoreCase("Plus")) {
                this.Team1Score++;
                Bukkit.broadcastMessage("§e§l[§c§l Soccer§e§l ]§f§l " + TeamName1 + "팀! 1점 획득!");
            } else {
                this.Team1Score--;
                Bukkit.broadcastMessage("§e§l[§c§l Soccer§e§l ]§f§l " + TeamName1 + "팀! 1점 차감!");
            }
        } else if (value.equalsIgnoreCase(TeamName2)) {
            if (Type.equalsIgnoreCase("Plus")) {
                this.Team2Score++;
                Bukkit.broadcastMessage("§e§l[§c§l Soccer§e§l ]§f§l " + TeamName2 + "팀! 1점 획득!");
            } else {
                this.Team2Score--;
                Bukkit.broadcastMessage("§e§l[§c§l Soccer§e§l ]§f§l " + TeamName2 + "팀! 1점 차감!");
            }
        } else {
            player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 올바르지 않은 팀이름입니다.");
        }
        bar.setTitle(format( TColor1 + "&l" + TeamName1 + " " + this.Team1Score + " &f&l: " + TColor2 +"&l" + this.Team2Score + " " + TeamName2));
    }
}