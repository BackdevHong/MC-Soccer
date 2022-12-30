package soccerplugin.soccersystem.command;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import soccerplugin.soccersystem.SoccerSystem;
import soccerplugin.soccersystem.bar.Bar;
import soccerplugin.soccersystem.bar.ScoreSetting;

import java.util.ArrayList;
import java.util.Collection;


public class GameCommand implements CommandExecutor {
    private Bar bar;
    private ScoreSetting scoreSetting;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp()) {
                if (args.length < 1) {
                    player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 게임에 대한 전반적인 명령어집입니다.\n" + "/Game Start - 게임을 시작합니다.\n" + "/Game Stop - 게임을 종료합니다.\n" + "/Game Score [Red / Blue] [Plus / Minus] - 레드팀과 블루팀에 스코어를 관리합니다.\n" + "/Game [Yellow / Red] [닉네임] - 옐로우 카드와 레드카드를 지급합니다.\n" +
                            "이 플러그인은 BackdevHong(뙈지몬/홍인성)이 제작한 플러그인입니다. 2차 배포, 2차 판매를 금지합니다.");
                    return true;
                } else {
                    if (args[0].equalsIgnoreCase("start")) {
                        if (bar == null) {
                            Bukkit.broadcastMessage("§e§l[§c§l Soccer§e§l ]§f§l 게임이 시작하였습니다!");
                            bar = new Bar();
                            scoreSetting = new ScoreSetting();
                            bar.createBar();
                            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                                bar.addPlayer(p);
                                scoreSetting.addPLayers(p);
                            }
                            return true;
                        } else {
                            player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 게임이 이미 시작되어 있습니다.");
                            return false;
                        }
                    } else if (args[0].equalsIgnoreCase("stop")) {
                        if (bar == null) {
                            player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 게임이 시작되어 있지 않습니다.");
                            return false;
                        } else {
                            Bukkit.broadcastMessage("§e§l[§c§l Soccer§e§l ]§f§l 게임이 종료되었습니다!");
                            bar.removeBar();
                            scoreSetting.removeScore();
                            bar = null;
                            return true;
                        }
                    } else if (args[0].equalsIgnoreCase("score")) {
                        if (bar == null) {
                            player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 게임이 시작되어 있지 않습니다.");
                            return false;
                        } else {
                            if (args[1].equalsIgnoreCase("RED") || args[1].equalsIgnoreCase("레드")) {
                                if(args[2].equalsIgnoreCase("Plus")) {
                                    Bukkit.broadcastMessage("§e§l[§c§l Soccer§e§l ]§f§l " + "" + args[1] + "팀! 1점 획득!");
                                    bar.addScore(args[1], args[2]);
                                    return true;
                                } else if (args[2].equalsIgnoreCase("Minus")) {
                                    Bukkit.broadcastMessage("§e§l[§c§l Soccer§e§l ]§f§l " + "" + args[1] + "팀! 1점 차감!");
                                    bar.addScore(args[1], args[2]);
                                    return true;
                                }
                            } else {
                                player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 올바르지 않은 팀입니다.");
                                return false;
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("Yellow")) {
                        if (!args[1].isEmpty()) {
                            if (bar == null) {
                                player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 게임이 시작되어 있지 않습니다.");
                                return false;
                            } else {
                                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                                    if (p == Bukkit.getPlayer(args[1])) {
                                        if (scoreSetting.getScore(p) == 0) {
                                            scoreSetting.setScore(p);
                                            Bukkit.broadcastMessage("§e§l[§c§l Soccer§e§l ]§f§l " + args[1] + " " + "플레이어 옐로우카드! 한장만 더 받으면 퇴장합니다!");
                                        } else {
                                            scoreSetting.redCard(p);
                                            Bukkit.broadcastMessage("§e§l[§c§l Soccer§e§l ]§f§l " + args[1] + " " + "플레이어 경고누적으로 퇴장합니다!");
                                        }
                                    } else {
                                        player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 올바르지 않은 닉네임입니다.");
                                    }
                                }
                            }
                        } else {
                            player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 닉네임을 적어주세요!");
                        }
                    } else if (args[0].equalsIgnoreCase("Red")) {
                        if (!args[1].isEmpty()) {
                            if (bar == null) {
                                player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 게임이 시작되어 있지 않습니다.");
                                return false;
                            } else {
                                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                                    if (p == Bukkit.getPlayer(args[1])) {
                                        scoreSetting.redCard(p);
                                        Bukkit.broadcastMessage("§e§l[§c§l Soccer§e§l ]§f§l " + "" + args[1] + "플레이어 레드카드! 퇴장합니다!");
                                    } else {
                                        player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 올바르지 않은 닉네임입니다.");
                                    }
                                }
                            }
                        } else {
                            player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 닉네임을 적어주세요!");
                        }
                    }
                }
            } else {
                player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l OP가 아닌 사람은 이 명령어를 실행시킬 수 없습니다.");
            }
        } else if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("콘솔에서는 이 명령어를 실행할 수 없습니다.");
            return false;
        }
        return false;
    }
}
