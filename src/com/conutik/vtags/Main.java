package com.conutik.vtags;

import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin implements Listener {
    TabAPI TABInstance;
    TeamManager teamManager;
    public FileConfiguration config = getConfig();
    @Override
    public void onEnable() {
        config.addDefault("update-period", 20);
        config.options().copyDefaults(true);
        saveConfig();

        new BukkitRunnable() {
            @Override
            public void run() {
                TABInstance = TabAPI.getInstance();
                teamManager = TabAPI.getInstance().getTeamManager();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    for (Player ps : Bukkit.getOnlinePlayers()) {
                        if (Functions.sameWorld(p, ps)) {
                            if (Functions.blockBetweenPlayers(p, ps)) {
                                teamManager.hideNametag(TABInstance.getPlayer(p.getUniqueId()), TABInstance.getPlayer(ps.getUniqueId()));
                                teamManager.hideNametag(TABInstance.getPlayer(ps.getUniqueId()), TABInstance.getPlayer(p.getUniqueId()));
                            } else {
                                teamManager.showNametag(TABInstance.getPlayer(p.getUniqueId()), TABInstance.getPlayer(ps.getUniqueId()));
                                teamManager.showNametag(TABInstance.getPlayer(ps.getUniqueId()), TABInstance.getPlayer(p.getUniqueId()));
                            }
                        }
                    }

                }
            }
        }.runTaskTimer(this, 0, config.getInt("update-period"));

        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "VTags is now working");
    }
}
