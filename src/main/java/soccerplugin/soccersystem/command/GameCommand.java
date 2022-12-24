package soccerplugin.soccersystem.command;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import soccerplugin.soccersystem.SoccerSystem;
import soccerplugin.soccersystem.bar.Bar;

import java.util.ArrayList;
import java.util.Collection;


public class GameCommand implements CommandExecutor {
    private Bar bar;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length < 1) {
                Player player = (Player) sender;
                player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 게임에 대한 전반적인 명령어집입니다.\n" + "/Game Start - 게임을 시작합니다.\n" + "/Game Stop - 게임을 종료합니다.\n" + "/Game Score [Red / Blue] [Plus / Minus] - 레드팀과 블루팀에 스코어를 관리합니다.\n" + "/Game [Yellow / Red] [닉네임] - 옐로우 카드와 레드카드를 지급합니다.\n" +
                        "이 플러그인은 BackdevHong(뙈지몬/홍인성)이 제작한 플러그인입니다. 2차 배포, 2차 판매를 금지합니다.");
                return true;
            } else {
                if (args[0].equalsIgnoreCase("start")) {
                    if (bar == null) {
                        Bukkit.broadcastMessage("§e§l[§c§l Soccer§e§l ]§f§l 게임이 시작하였습니다!");
                        bar = new Bar();
                        bar.createBar();
                        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                            bar.addPlayer(p);
                        }
                        return true;
                    } else {
                        Player player = (Player) sender;
                        player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 게임이 이미 시작되어 있습니다.");
                        return false;
                    }
                } else if (args[0].equalsIgnoreCase("stop")) {
                    if (bar == null) {
                        Player player = (Player) sender;
                        player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 게임이 시작되어 있지 않습니다.");
                        return false;
                    } else {
                        Bukkit.broadcastMessage("§e§l[§c§l Soccer§e§l ]§f§l 게임이 종료되었습니다!");
                        bar.removeBar();
                        bar = null;
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("score")) {
                    if (bar == null) {
                        Player player = (Player) sender;
                        player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 게임이 시작되어 있지 않습니다.");
                        return false;
                    } else {
                        if (args[1].equalsIgnoreCase("RED") || args[1].equalsIgnoreCase("Blue")) {
                            if (args[2].equalsIgnoreCase("PLUS") || args[2].equalsIgnoreCase("Minus")) {
                                Bukkit.broadcastMessage("§e§l[§c§l Soccer§e§l ]§f§l " + "" + args[1] + "팀! 1점 " + args[2]);
                                bar.addScore(args[1], args[2]);
                                return true;
                            }
                        } else {
                            Player player = (Player) sender;
                            player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 올바르지 않은 팀입니다.");
                            return false;
                        }
                    }
                }
            }
        } else if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("콘솔에서는 이 명령어를 실행할 수 없습니다.");
            return false;
        }
        return false;
    }
}
