package soccerplugin.soccersystem.command;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import soccerplugin.soccersystem.bar.Bar;
import soccerplugin.soccersystem.bar.ScoreSetting;
import soccerplugin.soccersystem.color.ColorStringEnum;

import java.util.Arrays;


public class GameCommand implements CommandExecutor {
    private Bar bar;
    private ScoreSetting scoreSetting;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String Team1Color;
            String Team2Color;
            if (player.isOp()) {
                if (args.length < 1) {
                    player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 게임에 대한 전반적인 명령어집입니다.\n" +
                            "/Game Start [팀1] [팀1 색깔] [팀2] [팀2 색깔] - 게임을 시작합니다.\n" +
                            "팀 컬러 종류는 /Game Color 으로 확인할 수 있습니다." +
                            "/Game Stop - 게임을 종료합니다.\n" +
                            "/Game Score [팀이름] [Plus / Minus] - 해당 팀의 스코어를 관리합니다.\n" +
                            "/Game [Yellow / Red] [닉네임] - 옐로우 카드와 레드카드를 지급합니다.\n" +
                            "/Game Clear - 옐로우 카드를 받은 사람들의 리스트를 초기화 시킵니다." +
                            "이 플러그인은 BackdevHong(뙈지몬/홍인성)이 제작한 플러그인입니다. 2차 배포, 2차 판매를 금지합니다.");
                    return true;
                } else {
                    if (args[0].equalsIgnoreCase("start")) {
                        if (args.length < 2) {
                            player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 첫번째 팀 이름을 적어주세요!");
                            return false;
                        } else {
                            if (args.length < 3) {
                                player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 첫번째 팀 색깔을 적어주세요!");
                                return false;
                            } else {
                                try {
                                    Team1Color = String.valueOf(ColorStringEnum.valueOf(args[2].toUpperCase()));
                                } catch (Exception e) {
                                    Team1Color = null;
                                }
                                if (Team1Color == null) {
                                    player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 팀 컬러가 올바르지 않습니다.");
                                    return false;
                                } else {
                                    if (args.length < 4) {
                                        player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 두번째 팀 이름을 적어주세요!");
                                        return false;
                                    } else {
                                        if (args.length < 5) {
                                            player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 두번째 팀 색깔을 적어주세요!");
                                            return false;
                                        } else {
                                            try {
                                                Team2Color = String.valueOf(ColorStringEnum.valueOf(args[4].toUpperCase()));
                                            } catch (Exception e) {
                                                Team2Color = null;
                                            }
                                            if (Team2Color == null) {
                                                player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 팀 컬러가 올바르지 않습니다.");
                                                return false;
                                            } else {
                                                if (bar == null) {
                                                    Bukkit.broadcastMessage("§e§l[§c§l Soccer§e§l ]§f§l 게임이 시작하였습니다!");
                                                    bar = new Bar();
                                                    scoreSetting = new ScoreSetting();
                                                    bar.createBar(args[1], args[3], Team1Color, Team2Color);
                                                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                                                        bar.addPlayer(p);
                                                        scoreSetting.addPLayers(p);
                                                    }
                                                    return true;
                                                } else {
                                                    player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 게임이 이미 시작되었습니다.");
                                                    return false;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
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
                            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                                p.setGameMode(GameMode.SURVIVAL);
                            }
                            return true;
                        }
                    } else if (args[0].equalsIgnoreCase("score")) {
                        if (bar == null) {
                            player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 게임이 시작되어 있지 않습니다.");
                            return false;
                        } else {
                            if (args.length < 2) {
                                player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 팀 이름을 넣어주세요.");
                            } else {
                                if (args[2].equalsIgnoreCase("Plus") || args[2].equalsIgnoreCase("Minus")) {
                                    bar.addScore(args[1], args[2], player);
                                    return true;
                                }
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("Yellow")) {
                        if (!args[1].isEmpty()) {
                            if (bar == null) {
                                player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 게임이 시작되어 있지 않습니다.");
                                return false;
                            } else {
                                if (Bukkit.getPlayer(args[1]).isOnline()) {
                                    if (scoreSetting.getScore(Bukkit.getPlayer(args[1])) == 0) {
                                        scoreSetting.setScore(Bukkit.getPlayer(args[1]));
                                        Bukkit.broadcastMessage("§e§l[§c§l Soccer§e§l ]§f§l " + args[1] + " " + "플레이어 옐로우카드! 한장만 더 받으면 퇴장합니다!");
                                    } else {
                                        scoreSetting.redCard(Bukkit.getPlayer(args[1]));
                                        Bukkit.broadcastMessage("§e§l[§c§l Soccer§e§l ]§f§l " + args[1] + " " + "플레이어 경고누적으로 퇴장합니다!");
                                    }
                                } else {
                                    player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 올바르지 않은 닉네임입니다.");
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
                                if (Bukkit.getPlayer(args[1]).isOnline()) {
                                    scoreSetting.redCard(Bukkit.getPlayer(args[1]));
                                    Bukkit.broadcastMessage("§e§l[§c§l Soccer§e§l ]§f§l " + "" + args[1] + " 플레이어 레드카드! 퇴장합니다!");
                                } else {
                                    player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 올바르지 않은 닉네임입니다.");
                                }
                            }
                        } else {
                            player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 닉네임을 적어주세요!");
                        }
                    } else if (args[0].equalsIgnoreCase("color")) {
                        player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l Black, Blue, Green, BlueGreen, Red, Purple, Gold, Silver, DarkSilver, LightBlue, LightGreen, SkyBlue, LightRed, Pink, Yellow, White");
                    } else if (args[0].equalsIgnoreCase("clear")) {
                        player.sendMessage("§e§l[§c§l Soccer§e§l ]§f§l 옐로우 카드 리스트를 초기화하였습니다.");
                        scoreSetting = new ScoreSetting();
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            scoreSetting.addPLayers(p);
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
