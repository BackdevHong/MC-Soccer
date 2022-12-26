package soccerplugin.soccersystem;

import org.bukkit.plugin.java.JavaPlugin;
import soccerplugin.soccersystem.command.GameCommand;

public final class SoccerSystem extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Soccer Plugin Loaded");
        getCommand("game").setExecutor(new GameCommand());
    }

    @Override
    public void onDisable() {
        getLogger().info("Soccer Plugin Disabled");
    }
}
